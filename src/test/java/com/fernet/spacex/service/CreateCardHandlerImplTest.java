package com.fernet.spacex.service;

import com.fernet.spacex.model.CardType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateCardHandlerImplTest  {
    @InjectMocks
    private CreateCardHandlerImpl createCardHandler;
    @Mock
    private CreateIssueImpl createIssue;
    @Mock
    private CreateTaskImpl createTask;
    @Mock
    private CreateBugImpl createBug;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getCardParametersShouldRunOkForBug(){
        AbstractCardCreator cardCreator = createCardHandler.getCardParameters(CardType.bug);

        assertEquals(cardCreator, createBug);
    }
    @Test
    public void getCardParametersShouldRunOkForIssue(){
        AbstractCardCreator cardCreator = createCardHandler.getCardParameters(CardType.issue);

        assertEquals(cardCreator, createIssue);
    }
    @Test
    public void getCardParametersShouldRunOkForTask(){

        AbstractCardCreator cardCreator = createCardHandler.getCardParameters(CardType.task);

        assertEquals(cardCreator, createTask);
    }
}
