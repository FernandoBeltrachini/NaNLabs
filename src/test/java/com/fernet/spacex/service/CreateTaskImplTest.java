package com.fernet.spacex.service;

import com.fernet.spacex.controller.request.CreateCardRequest;
import com.fernet.spacex.service.model.ParamType;
import com.fernet.spacex.service.rest.LabelsService;
import com.fernet.spacex.service.rest.ListsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;

import static com.fernet.spacex.service.model.CardCategory.Bug;
import static com.fernet.spacex.service.model.CardCategory.Maintenance;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CreateTaskImplTest {
    @Mock
    private ListsService trelloListsService;
    @Mock
    private LabelsService trelloLabelsService;

    @InjectMocks
    private CreateTaskImpl createTask;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void runValidationsShouldRunOk() {

        CreateCardRequest createCardRequest = new CreateCardRequest();
        createCardRequest.setTittle("Sample Title");
        createCardRequest.setCategory(Maintenance);

        createTask.runValidations(createCardRequest);
    }

    @Test
    void runValidationsFailsDueNullTittle() {
        CreateCardRequest createCardRequest = new CreateCardRequest();
        createCardRequest.setCategory(Maintenance);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> createTask.runValidations(createCardRequest));

        assertEquals("Tittle cant be null", exception.getMessage());
    }

    @Test
    void runValidationsFailsDueNullCategory() {
        CreateCardRequest createCardRequest = new CreateCardRequest();
        createCardRequest.setTittle("Sample Title");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> createTask.runValidations(createCardRequest));

        assertEquals("Category cant be null", exception.getMessage());
    }

    @Test
    void runValidationsFailsDueInvalidCategory() {
        CreateCardRequest createCardRequest = new CreateCardRequest();
        createCardRequest.setTittle("Sample Title");
        createCardRequest.setCategory(Bug);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> createTask.runValidations(createCardRequest));

        assertEquals("Wrong category", exception.getMessage());
    }

    @Test
    void getCardParametersShouldRunOk() {
        when(trelloListsService.getListByNameOrCreateNewOne(CreateTaskImpl.BACKLOG_LIST_NAME)).thenReturn("listId");
        when(trelloLabelsService.getLabelIdByNameOrCreateNewOne(anyString())).thenReturn("labelId");

        CreateCardRequest createCardRequest = new CreateCardRequest();
        createCardRequest.setTittle("Sample Title");
        createCardRequest.setCategory(Maintenance);

        Map<ParamType, String> response = createTask.getCardParameters(createCardRequest);

        assertNotNull(response);
        assertEquals("listId", response.get(ParamType.LIST));
        assertEquals("Sample Title", response.get(ParamType.NAME));
        assertEquals("labelId", response.get(ParamType.LABEL));

        verify(trelloListsService, times(1)).getListByNameOrCreateNewOne(CreateTaskImpl.BACKLOG_LIST_NAME);
        verify(trelloLabelsService, times(1)).getLabelIdByNameOrCreateNewOne(anyString());
    }
}
