package com.fernet.spacex.service;

import com.fernet.spacex.controller.request.CreateCardRequest;
import com.fernet.spacex.service.model.ParamType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

import static java.util.Objects.*;

@Service
public class CardServiceImpl implements CardService {
    @Autowired
    private CardsService trelloCardsService;
    @Autowired
    private CreateCardHandler createCardHandler;

    /**
     * Service that creates a card on trello.
     */
    @Override
    public void createCard(CreateCardRequest createCardRequest) {
        if (isNull(createCardRequest) || isNull(createCardRequest.getType())) {
            throw new IllegalArgumentException("Null card type.");
        }
        Map<ParamType, String> parameters = createCardHandler.getCardParameters(createCardRequest.getType()).getParameters(createCardRequest);
        trelloCardsService.createCard(parameters);
    }
}
