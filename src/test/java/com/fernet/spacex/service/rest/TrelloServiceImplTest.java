package com.fernet.spacex.service.rest;

import com.fernet.spacex.service.model.ParamType;
import com.fernet.spacex.service.rest.model.TrelloListItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

public class TrelloServiceImplTest {

    public static final String TRELLO_ITEM_ID_1 = "ID_1";
    public static final String TRELLO_ITEM_ID_2 = "ID_2";
    public static final String TEST_NAME_1 = "TEST NAME 1";
    public static final String TEST_NAME_2 = "TEST NAME 2";
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private TrelloServiceImpl trelloService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(trelloService, "baseUrl", "https://api.trello.com/");
        ReflectionTestUtils.setField(trelloService, "key", "key");
        ReflectionTestUtils.setField(trelloService, "token", "token");
        ReflectionTestUtils.setField(trelloService, "board", "board");
    }

    @Test
    void createCardShouldRunOk() {
        Map<ParamType, String> parameters = new HashMap<>();
        parameters.put(ParamType.LIST, "listId");
        parameters.put(ParamType.NAME, "Card Name");
        parameters.put(ParamType.DESCRIPTION, "Card Description");

        trelloService.createCard(parameters);

        verify(restTemplate, times(1)).postForEntity(anyString(), isNull(), isNull());
    }

    @Test
    void getListByNameShouldRunOk() {
        TrelloListItem item1 = new TrelloListItem(TRELLO_ITEM_ID_1, TEST_NAME_1);
        TrelloListItem item2 = new TrelloListItem(TRELLO_ITEM_ID_2, TEST_NAME_2);

        TrelloListItem[] trelloResponse = {item1, item2};
        when(restTemplate.getForObject(anyString(), any())).thenReturn(trelloResponse);

        String response = trelloService.getListByName(TEST_NAME_1);

        verify(restTemplate, times(1)).getForObject(anyString(), any());
        assertEquals(response, TRELLO_ITEM_ID_1);
    }

    @Test
    void getListByNameShouldReturnFirstIfListNotFound() {
        TrelloListItem item1 = new TrelloListItem(TRELLO_ITEM_ID_1, TEST_NAME_1);
        TrelloListItem item2 = new TrelloListItem(TRELLO_ITEM_ID_2, TEST_NAME_2);

        TrelloListItem[] trelloResponse = {item1, item2};
        when(restTemplate.getForObject(anyString(), any())).thenReturn(trelloResponse);

        String response = trelloService.getListByName("OTHER NAME");

        verify(restTemplate, times(1)).getForObject(anyString(), any());
        assertEquals(response, TRELLO_ITEM_ID_1);
    }

    @Test
    void getLabelIdByNameShouldRunOk() {
        TrelloListItem item1 = new TrelloListItem(TRELLO_ITEM_ID_1, TEST_NAME_1);
        TrelloListItem item2 = new TrelloListItem(TRELLO_ITEM_ID_2, TEST_NAME_2);

        TrelloListItem[] trelloResponse = {item1, item2};
        when(restTemplate.getForObject(anyString(), any())).thenReturn(trelloResponse);

        String response = trelloService.getLabelIdByName(TEST_NAME_1);

        verify(restTemplate, times(1)).getForObject(anyString(), any());
        assertEquals(response, TRELLO_ITEM_ID_1);
    }

    @Test
    void getLabelIdByNameShouldReturnEmptyStringIfNotFound() {
        TrelloListItem item1 = new TrelloListItem();
        item1.setId(TRELLO_ITEM_ID_1);
        item1.setName(TEST_NAME_1);

        TrelloListItem item2 = new TrelloListItem();
        item2.setId(TRELLO_ITEM_ID_2);
        item2.setName(TEST_NAME_2);

        TrelloListItem[] trelloResponse = {item1, item2};
        when(restTemplate.getForObject(anyString(), any())).thenReturn(trelloResponse);

        String response = trelloService.getLabelIdByName("OTHER NAME");

        verify(restTemplate, times(1)).getForObject(anyString(), any());
        assertEquals(response, "");
    }
    @Test
    void getLabelIdByNameShouldFail() {
        TrelloListItem item1 = new TrelloListItem(TRELLO_ITEM_ID_1, TEST_NAME_1);
        TrelloListItem item2 = new TrelloListItem(TRELLO_ITEM_ID_2, TEST_NAME_2);

        TrelloListItem[] trelloResponse = {item1, item2};
        when(restTemplate.getForObject(anyString(), any())).thenReturn(trelloResponse);

        String response = trelloService.getLabelIdByName(TEST_NAME_1);

        verify(restTemplate, times(1)).getForObject(anyString(), any());
        assertEquals(response, TRELLO_ITEM_ID_1);
    }
    @Test
    void getBoardMembersShouldRunOk() {
        TrelloListItem item1 = new TrelloListItem();
        item1.setId(TRELLO_ITEM_ID_1);
        TrelloListItem item2 = new TrelloListItem();
        item2.setId(TRELLO_ITEM_ID_2);
        TrelloListItem[] trelloResponse = {item1, item2};
        when(restTemplate.getForObject(anyString(), any())).thenReturn(trelloResponse);

        List<String> response = trelloService.getBoardMembers();

        verify(restTemplate, times(1)).getForObject(anyString(), any());
        assertTrue(response.size() == 2);
        assertTrue(response.contains(TRELLO_ITEM_ID_1));
        assertTrue(response.contains(TRELLO_ITEM_ID_2));
    }

    @Test
    void getBoardMembersShouldReturnEmtpyListFromNullResponse() {
        when(restTemplate.getForObject(anyString(), any())).thenReturn(null);

        List<String> response = trelloService.getBoardMembers();

        verify(restTemplate, times(1)).getForObject(anyString(), any());
        assertNotNull(response);
    }

}
