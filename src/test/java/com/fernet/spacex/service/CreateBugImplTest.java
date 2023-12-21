package com.fernet.spacex.service;


import com.fernet.spacex.controller.request.CreateCardRequest;
import com.fernet.spacex.service.model.ParamType;
import com.fernet.spacex.service.rest.TrelloService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class CreateBugImplTest {
    @InjectMocks
    private CreateBugImpl createBug;
    @Mock
    private TrelloService trelloService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    void runValidationsNullDescriptionShouldThrowIllegalArgumentException() {

        CreateCardRequest createCardRequest = new CreateCardRequest();

        assertThrows(IllegalArgumentException.class, () -> createBug.runValidations(createCardRequest));
    }

    @Test
    void getCardParametersShouldRunOk() {

        when(trelloService.getLabelIdByName(anyString())).thenReturn("labelId");
        when(trelloService.getListByName(anyString())).thenReturn("listId");
        when(trelloService.getBoardMembers()).thenReturn(List.of("member1", "member2"));

        CreateCardRequest createCardRequest = new CreateCardRequest();
        createCardRequest.setDescription("Sample description");

        Map<ParamType, String> response = createBug.getCardParameters(createCardRequest);

        assertNotNull(response);
        assertEquals("labelId", response.get(ParamType.LABEL));
        assertEquals("listId", response.get(ParamType.LIST));
        assertEquals("Sample description", response.get(ParamType.DESCRIPTION));
        assertTrue(response.get(ParamType.NAME).matches("^bug-[a-z]{4}-\\d+$"));
        assertNotNull(response.get(ParamType.MEMBERS));
    }

    @Test
    void getCardParametersShouldRunOkWithNoMembers() {

        when(trelloService.getLabelIdByName(anyString())).thenReturn("labelId");
        when(trelloService.getListByName(anyString())).thenReturn("listId");
        when(trelloService.getBoardMembers()).thenReturn(new ArrayList<>());

        CreateCardRequest createCardRequest = new CreateCardRequest();
        createCardRequest.setDescription("Sample description");

        Map<ParamType, String> response = createBug.getCardParameters(createCardRequest);

        assertNotNull(response);
        assertEquals("labelId", response.get(ParamType.LABEL));
        assertEquals("listId", response.get(ParamType.LIST));
        assertEquals("Sample description", response.get(ParamType.DESCRIPTION));
        assertTrue(response.get(ParamType.NAME).matches("^bug-[a-z]{4}-\\d+$"));
        assertEquals("", response.get(ParamType.MEMBERS));
    }
}
