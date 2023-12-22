package com.fernet.spacex.service.rest;

import com.fernet.spacex.service.rest.model.TrelloLabelListItem;
import com.fernet.spacex.service.rest.model.TrelloListItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TrelloListsServiceImplTest {

    public static final String TRELLO_ITEM_ID_1 = "ID_1";
    public static final String TRELLO_ITEM_ID_2 = "ID_2";
    public static final String TRELLO_ITEM_ID_3 = "ID_3";
    public static final String TEST_NAME_1 = "TEST NAME 1";
    public static final String TEST_NAME_2 = "TEST NAME 2";
    public static final String BOARD_ID = "BOARD_ID";
    public static final String NEW_LIST_NAME = "NEW LIST NAME";
    public static final String OTHER_NAME = "OTHER_NAME";
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private TrelloListsServiceImpl trelloListsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(trelloListsService, "baseUrl", "https://api.trello.com/");
        ReflectionTestUtils.setField(trelloListsService, "key", "key");
        ReflectionTestUtils.setField(trelloListsService, "token", "token");
        ReflectionTestUtils.setField(trelloListsService, "board", "board");
    }

    @Test
    void createListShouldRunOk() {
        TrelloLabelListItem item1 = new TrelloLabelListItem();
        item1.setId(TRELLO_ITEM_ID_1);
        item1.setName(NEW_LIST_NAME);

        when(restTemplate.postForEntity(anyString(), any(), any())).thenReturn(ResponseEntity.ok(item1));

        String response = trelloListsService.createList(NEW_LIST_NAME, BOARD_ID);

        verify(restTemplate, times(1)).postForEntity(anyString(), any(), any());
        assertNotNull(response);
        assertEquals(response, TRELLO_ITEM_ID_1);
    }

    @Test
    void getListByNameShouldRunOk() {
        TrelloListItem item1 = new TrelloListItem(TRELLO_ITEM_ID_1, TEST_NAME_1, BOARD_ID);
        TrelloListItem item2 = new TrelloListItem(TRELLO_ITEM_ID_2, TEST_NAME_2, BOARD_ID);

        TrelloListItem[] trelloResponse = {item1, item2};
        when(restTemplate.getForObject(anyString(), any())).thenReturn(trelloResponse);

        String response = trelloListsService.getListByNameOrCreateNewOne(TEST_NAME_1);

        verify(restTemplate, times(1)).getForObject(anyString(), any());
        assertEquals(response, TRELLO_ITEM_ID_1);
    }
    @Test
    void getListByNameShouldRunOkShouldCreateNewListIfNotFound() {
        TrelloListItem item1 = new TrelloListItem(TRELLO_ITEM_ID_1, TEST_NAME_1, BOARD_ID);
        TrelloListItem item2 = new TrelloListItem(TRELLO_ITEM_ID_2, TEST_NAME_2, BOARD_ID);
        TrelloLabelListItem item3 = new TrelloLabelListItem();
        item3.setId(TRELLO_ITEM_ID_3);
        item3.setName(NEW_LIST_NAME);
        TrelloListItem[] trelloResponse = {item1, item2};

        when(restTemplate.postForEntity(anyString(), any(), any())).thenReturn(ResponseEntity.ok(item3));
        when(restTemplate.getForObject(anyString(), any())).thenReturn(trelloResponse);

        String response = trelloListsService.getListByNameOrCreateNewOne(NEW_LIST_NAME);

        verify(restTemplate, times(1)).getForObject(anyString(), any());
        verify(restTemplate, times(1)).postForEntity(anyString(), any(), any());
        assertEquals(response, TRELLO_ITEM_ID_3);
    }
}
