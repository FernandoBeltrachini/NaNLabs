package com.fernet.spacex.service;
import com.fernet.spacex.controller.request.CreateCardRequest;
import com.fernet.spacex.service.model.ParamType;
import com.fernet.spacex.service.rest.ListsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CreateIssueImplTest {
    @Mock
    private ListsService trelloListsService;

    @InjectMocks
    private CreateIssueImpl createIssue;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void runValidationsShouldRunOk() {
        CreateCardRequest createCardRequest = new CreateCardRequest();
        createCardRequest.setTittle("Sample Title");
        createCardRequest.setDescription("Sample Description");

        assertDoesNotThrow(() -> createIssue.runValidations(createCardRequest));
    }

    @Test
    void runValidationsNullTitleThrowsIllegalArgumentException() {
        CreateCardRequest createCardRequest = new CreateCardRequest();
        createCardRequest.setDescription("Sample Description");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> createIssue.runValidations(createCardRequest));

        assertEquals("Tittle cant be null", exception.getMessage());
    }

    @Test
    void runValidationsNullDescriptionThrowsIllegalArgumentException() {
        CreateCardRequest createCardRequest = new CreateCardRequest();
        createCardRequest.setTittle("Sample Title");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> createIssue.runValidations(createCardRequest));

        assertEquals("Description cant be null", exception.getMessage());
    }

    @Test
    void getCardParametersShouldRunOk() {
        when(trelloListsService.getListByNameOrCreateNewOne(CreateIssueImpl.TO_DO_LIST_NAME)).thenReturn("listId");

        CreateCardRequest createCardRequest = new CreateCardRequest();
        createCardRequest.setTittle("Sample Title");
        createCardRequest.setDescription("Sample Description");

        Map<ParamType, String> response = createIssue.getCardParameters(createCardRequest);

        assertNotNull(response);
        assertEquals("listId", response.get(ParamType.LIST));
        assertEquals("Sample Title", response.get(ParamType.NAME));
        assertEquals("Sample Description", response.get(ParamType.DESCRIPTION));
        verify(trelloListsService, times(1)).getListByNameOrCreateNewOne(anyString());
    }
}
