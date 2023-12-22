package com.fernet.spacex.service;

import com.fernet.spacex.controller.request.CreateCardRequest;
import com.fernet.spacex.model.CardType;
import com.fernet.spacex.service.rest.CardsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.ResourceAccessException;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class CardServiceImplTest {
    @Mock
    private CreateCardHandler createCardHandler;

    @Mock
    private CardsService trelloCardsService;

    @Mock
    private CreateBugImpl createBug;

    @InjectMocks
    private CardServiceImpl cardService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createCardOk() {
        CreateCardRequest createCardRequest = new CreateCardRequest();
        createCardRequest.setType(CardType.bug);
        when(createCardHandler.getCardParameters(any())).thenReturn(createBug);
        when(createBug.getParameters(any())).thenReturn(new HashMap<>());

        cardService.createCard(createCardRequest);

        verify(trelloCardsService, times(1)).createCard(any());
    }

    @Test
    void createCardThrowsResourceAccessException() {
        CreateCardRequest createCardRequest = new CreateCardRequest();
        createCardRequest.setType(CardType.issue);
        when(createCardHandler.getCardParameters(any())).thenThrow(ResourceAccessException.class);

        assertThrows(ResourceAccessException.class, () -> cardService.createCard(createCardRequest));

    }

    @Test
    void createCardShouldFailDueNullCardType() {
        assertThrows(IllegalArgumentException.class, () -> cardService.createCard(new CreateCardRequest()));
    }

    @Test
    void createCardShouldFailDueNullRequest() {
        assertThrows(IllegalArgumentException.class, () -> cardService.createCard(null));
    }
}
