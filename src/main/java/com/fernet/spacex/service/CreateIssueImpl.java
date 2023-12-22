package com.fernet.spacex.service;

import com.fernet.spacex.controller.request.CreateCardRequest;
import com.fernet.spacex.service.model.ParamType;
import com.fernet.spacex.service.rest.ListsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;

@Service
public class CreateIssueImpl extends AbstractCardCreator {
    public static final String TO_DO_LIST_NAME = "TO DO";

    @Autowired
    private ListsService trelloListsService;

    // An issue: This represents a business feature that needs implementation,
    // they will provide a short title and a description.
    // All issues gets added to the “To Do” list as unassigned
    @Override
    public void runValidations(CreateCardRequest createCardRequest) {
        if (isNull(createCardRequest.getTittle())) {
            throw new IllegalArgumentException("Tittle cant be null");
        }
        if (isNull(createCardRequest.getDescription())) {
            throw new IllegalArgumentException("Description cant be null");
        }
    }

    @Override
    public Map<ParamType, String> getCardParameters(CreateCardRequest createCardRequest) {
        Map<ParamType, String> response = new HashMap<>();


        response.put(ParamType.LIST, trelloListsService.getListByNameOrCreateNewOne(TO_DO_LIST_NAME));
        response.put(ParamType.NAME, createCardRequest.getTittle());
        response.put(ParamType.DESCRIPTION, createCardRequest.getDescription());

        return response;
    }


}
