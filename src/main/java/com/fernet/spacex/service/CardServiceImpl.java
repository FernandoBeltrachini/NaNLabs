package com.fernet.spacex.service;

import com.fernet.spacex.controller.request.CreateCardRequest;
import com.fernet.spacex.service.model.ParamType;
import com.fernet.spacex.service.rest.TrelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.Map;

@Service
public class CardServiceImpl implements CardService {
    @Autowired
    private TrelloService trelloService;
    @Autowired
    private CreateCardHandler createCardHandler;

    /**
     * Service that creates a card on trello.
     */
    @Override
    public void createCard(CreateCardRequest createCardRequest) {
        Map<ParamType, String> parameters = createCardHandler.getCardParameters(createCardRequest.getType()).getParameters(createCardRequest);
        trelloService.createCard(parameters);
    }
}
