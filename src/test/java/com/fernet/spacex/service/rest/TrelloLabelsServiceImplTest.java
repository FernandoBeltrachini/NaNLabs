package com.fernet.spacex.service.rest;

import com.fernet.spacex.service.BoardService;
import com.fernet.spacex.service.rest.model.TrelloLabelListItem;
import com.fernet.spacex.service.rest.model.TrelloLabelsColors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TrelloLabelsServiceImplTest {

    public static final String TRELLO_ITEM_ID_1 = "ID_1";
    public static final String TRELLO_ITEM_ID_2 = "ID_2";
    public static final String TEST_NAME_1 = "TEST NAME 1";
    public static final String TEST_NAME_2 = "TEST NAME 2";
    public static final String BOARD_ID = "BOARD_ID";
    public static final String OTHER_NAME = "OTHER NAME";
    public static final String ID_3 = "ID_3";
    public static final String LABEL_NEW_NAME = "new name";
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private TrelloLabelsServiceImpl trelloLabelsService;

    @Mock
    private BoardService trelloBoardService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(trelloLabelsService, "baseUrl", "https://api.trello.com/");
        ReflectionTestUtils.setField(trelloLabelsService, "key", "key");
        ReflectionTestUtils.setField(trelloLabelsService, "token", "token");
        ReflectionTestUtils.setField(trelloLabelsService, "board", "board");
    }

    private void addDefaultGetTrelloLabelsResponse() {
        TrelloLabelListItem item1 = new TrelloLabelListItem();
        item1.setId(TRELLO_ITEM_ID_1);
        item1.setName(TEST_NAME_1);

        TrelloLabelListItem item2 = new TrelloLabelListItem();
        item2.setId(TRELLO_ITEM_ID_2);
        item2.setName(TEST_NAME_2);

        TrelloLabelListItem[] trelloResponse = new TrelloLabelListItem[]{item1, item2};

        when(trelloBoardService.getBoardLabels()).thenReturn(List.of(trelloResponse));

    }

    @Test
    void getLabelIdByNameShouldRunOk() {
        addDefaultGetTrelloLabelsResponse();

        String response = trelloLabelsService.getLabelIdByNameOrCreateNewOne(TEST_NAME_1);

        verify(trelloBoardService, times(1)).getBoardLabels();
        assertEquals(response, TRELLO_ITEM_ID_1);
    }

    @Test
    void getLabelIdByNameShouldCreateNewLabelWhenNoLabelsOnBoard() {
        TrelloLabelListItem item1 = new TrelloLabelListItem();
        item1.setId(TRELLO_ITEM_ID_1);
        item1.setName(OTHER_NAME);

        when(restTemplate.getForObject(anyString(), any())).thenReturn(null);
        when(restTemplate.postForEntity(anyString(), any(), any())).thenReturn(ResponseEntity.ok(item1));
        when(trelloBoardService.getBoard()).thenReturn(BOARD_ID);

        String response = trelloLabelsService.getLabelIdByNameOrCreateNewOne(OTHER_NAME);

        verify(trelloBoardService, times(1)).getBoardLabels();
        verify(restTemplate, times(1)).postForEntity(anyString(), any(), any());
        verify(trelloBoardService, times(1)).getBoard();
        assertEquals(response, TRELLO_ITEM_ID_1);
    }

    @Test
    void getLabelIdByNameShouldCreateNewLabelDueNameNotFound() {
        addDefaultGetTrelloLabelsResponse();

        TrelloLabelListItem item1 = new TrelloLabelListItem();
        item1.setId(ID_3);
        item1.setName(OTHER_NAME);

        when(restTemplate.getForObject(anyString(), any())).thenReturn(null);
        when(restTemplate.postForEntity(anyString(), any(), any())).thenReturn(ResponseEntity.ok(item1));

        String response = trelloLabelsService.getLabelIdByNameOrCreateNewOne(OTHER_NAME);

        verify(trelloBoardService, times(1)).getBoardLabels();
        verify(restTemplate, times(1)).postForEntity(anyString(), any(), any());
        assertEquals(response, ID_3);
    }



    @Test
    void getBoardLabelsShouldRunOkWithNullResponseFromTrello() {
        TrelloLabelListItem item1 = new TrelloLabelListItem();
        item1.setId(ID_3);
        item1.setName(OTHER_NAME);
        when(restTemplate.postForEntity(anyString(), any(), any())).thenReturn(ResponseEntity.ok(item1));

        String response = trelloLabelsService.createLabel(LABEL_NEW_NAME, TrelloLabelsColors.purple, BOARD_ID);

        verify(restTemplate, times(1)).postForEntity(anyString(), any(), any());
        assertNotNull(response);
        assertEquals(response, ID_3);
    }


}
