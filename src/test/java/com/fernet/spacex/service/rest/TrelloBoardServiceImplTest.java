package com.fernet.spacex.service.rest;

import com.fernet.spacex.service.rest.model.TrelloLabelListItem;
import com.fernet.spacex.service.rest.model.TrelloListItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class TrelloBoardServiceImplTest {

    public static final String ID = "ID";
    public static final String TRELLO_ITEM_ID =  "TRELLO_ITEM_ID" ;
    public static final String TEST_NAME = "TEST_NAME";
    @InjectMocks
    private TrelloBoardServiceImpl trelloBoardService;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(trelloBoardService, "baseUrl", "https://api.trello.com/");
        ReflectionTestUtils.setField(trelloBoardService, "key", "key");
        ReflectionTestUtils.setField(trelloBoardService, "token", "token");
    }
    @Test
    public void getBoardShouldRunOk(){
        TrelloListItem trelloResponse = new TrelloListItem();
        trelloResponse.setId(ID);

        when(restTemplate.getForObject(anyString(), any())).thenReturn(trelloResponse);

        String response = trelloBoardService.getBoard();

        verify(restTemplate, times(1)).getForObject(anyString(), any());
        assertEquals(response, ID);
    }

    @Test
    public void getBoardShouldRunOkWithNullResponse(){
        when(restTemplate.getForObject(anyString(), any())).thenReturn(null);

        String response = trelloBoardService.getBoard();

        verify(restTemplate, times(1)).getForObject(anyString(), any());
        assertNull(response);
    }

    @Test
    void getBoardLabelsShouldRunOk() {
        TrelloLabelListItem item = new TrelloLabelListItem();
        item.setId(TRELLO_ITEM_ID);
        item.setName(TEST_NAME);

        TrelloLabelListItem[] trelloResponse = new TrelloLabelListItem[]{item};

        when(restTemplate.getForObject(anyString(), any())).thenReturn(trelloResponse);

        List<TrelloLabelListItem> response = trelloBoardService.getBoardLabels();

        verify(restTemplate, times(1)).getForObject(anyString(), any());
        assertNotNull(response);
        assertTrue(response.size() == 1);
    }

}