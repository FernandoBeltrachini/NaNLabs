package com.fernet.spacex.controller;

import com.fernet.spacex.controller.request.CreateCardRequest;
import com.fernet.spacex.service.CardService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Card controller.
 */
@RestController
public class CardController {

    @Autowired
    private CardService cardService;

    /**
     * Service used to create a card on trello.
     *
     * @param createCardRequest
     */
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public void createCard(@Valid @RequestBody CreateCardRequest createCardRequest) {
        cardService.createCard(createCardRequest);
    }
}
