package com.fernet.spacex.service;

import com.fernet.spacex.controller.request.CreateCardRequest;
import com.fernet.spacex.service.model.ParamType;
import com.fernet.spacex.service.rest.LabelsService;
import com.fernet.spacex.service.rest.ListsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.fernet.spacex.service.model.CardCategory.Bug;
import static java.util.Objects.isNull;

@Service
public class CreateTaskImpl extends AbstractCardCreator {
    public static final String BACKLOG_LIST_NAME = "Backlog";
    @Autowired
    private ListsService trelloListsService;
    @Autowired
    private LabelsService trelloLabelsService;

    // A task: This represents some manual work that needs to be done.
    // It will count with just a title and a category (Maintenance, Research, or Test)
    // each corresponding to a label in trello.
    @Override
    public void runValidations(CreateCardRequest createCardRequest) {
        if (isNull(createCardRequest.getTittle())) {
            throw new IllegalArgumentException("Tittle cant be null");
        }
        if (isNull(createCardRequest.getCategory())) {
            throw new IllegalArgumentException("Category cant be null");
        }
        if (Bug.equals(createCardRequest.getCategory())) {
            throw new IllegalArgumentException("Wrong category");
        }
    }

    @Override
    public Map<ParamType, String> getCardParameters(CreateCardRequest createCardRequest) {

        Map<ParamType, String> response = new HashMap<>();
        String labelId = trelloLabelsService.getLabelIdByNameOrCreateNewOne(createCardRequest.getCategory().toString());

        response.put(ParamType.LIST, trelloListsService.getListByNameOrCreateNewOne(BACKLOG_LIST_NAME));
        response.put(ParamType.NAME, createCardRequest.getTittle());
        response.put(ParamType.LABEL, labelId);

        return response;
    }
}
