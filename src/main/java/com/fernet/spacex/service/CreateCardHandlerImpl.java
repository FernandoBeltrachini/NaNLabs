package com.fernet.spacex.service;

import com.fernet.spacex.model.CardType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.*;

/**
 * Service that holds the implementations of different types of card creations.
 */
@Service
public class CreateCardHandlerImpl implements CreateCardHandler {
    private Map<CardType, AbstractCardCreator> cardTypeHandler;
    @Autowired
    private CreateIssueImpl createIssue;
    @Autowired
    private CreateTaskImpl createTask;
    @Autowired
    private CreateBugImpl createBug;

    /**
     * Metod used to get the implementation of the card that we want to create.
     *
     * @param cardType
     */
    @Override
    public AbstractCardCreator getCardParameters(CardType cardType) {
        if (isNull(cardTypeHandler)) {
            cardTypeHandler = new HashMap<>();
            cardTypeHandler.put(CardType.issue, createIssue);
            cardTypeHandler.put(CardType.task, createTask);
            cardTypeHandler.put(CardType.bug, createBug);
        }
        return cardTypeHandler.get(cardType);
    }
}
