package com.fernet.spacex.service.rest;

import com.fernet.spacex.service.rest.model.TrelloListItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class TrelloMembersServiceImplTest {

    public static final String TRELLO_ITEM_ID_1 = "ID_1";
    public static final String TRELLO_ITEM_ID_2 = "ID_2";

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private TrelloMembersServiceImpl trelloMembersService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(trelloMembersService, "baseUrl", "https://api.trello.com/");
        ReflectionTestUtils.setField(trelloMembersService, "key", "key");
        ReflectionTestUtils.setField(trelloMembersService, "token", "token");
        ReflectionTestUtils.setField(trelloMembersService, "board", "board");
    }

    @Test
    void getBoardMembersShouldRunOk() {
        TrelloListItem item1 = new TrelloListItem();
        item1.setId(TRELLO_ITEM_ID_1);
        TrelloListItem item2 = new TrelloListItem();
        item2.setId(TRELLO_ITEM_ID_2);
        TrelloListItem[] trelloResponse = {item1, item2};
        when(restTemplate.getForObject(anyString(), any())).thenReturn(trelloResponse);

        List<String> response = trelloMembersService.getBoardMembers();

        verify(restTemplate, times(1)).getForObject(anyString(), any());
        assertTrue(response.size() == 2);
        assertTrue(response.contains(TRELLO_ITEM_ID_1));
        assertTrue(response.contains(TRELLO_ITEM_ID_2));
    }

    @Test
    void getBoardMembersShouldReturnEmtpyListFromNullResponse() {
        when(restTemplate.getForObject(anyString(), any())).thenReturn(null);

        List<String> response = trelloMembersService.getBoardMembers();

        verify(restTemplate, times(1)).getForObject(anyString(), any());
        assertNotNull(response);
    }

}
