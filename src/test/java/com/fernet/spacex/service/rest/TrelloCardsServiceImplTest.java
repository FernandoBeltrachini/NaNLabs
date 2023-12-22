package com.fernet.spacex.service.rest;

import com.fernet.spacex.service.model.ParamType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TrelloCardsServiceImplTest {

    public static final String TRELLO_ITEM_ID_1 = "ID_1";
    public static final String TRELLO_ITEM_ID_2 = "ID_2";
    public static final String TEST_NAME_1 = "TEST NAME 1";
    public static final String TEST_NAME_2 = "TEST NAME 2";
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private TrelloCardsServiceImpl trelloCardsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(trelloCardsService, "baseUrl", "https://api.trello.com/");
        ReflectionTestUtils.setField(trelloCardsService, "key", "key");
        ReflectionTestUtils.setField(trelloCardsService, "token", "token");
    }

    @Test
    void createCardShouldRunOk() {
        Map<ParamType, String> parameters = new HashMap<>();
        parameters.put(ParamType.LIST, "listId");
        parameters.put(ParamType.NAME, "Card Name");
        parameters.put(ParamType.DESCRIPTION, "Card Description");
        parameters.put(ParamType.MEMBERS, "MemberId");
        parameters.put(ParamType.LABEL, "LABEL");

        trelloCardsService.createCard(parameters);

        verify(restTemplate, times(1))
                .postForEntity("https://api.trello.com/1/cards?key=key&token=token&idList=listId&name=Card Name" +
                        "&idLabels=LABEL&desc=Card Description&idMembers=MemberId", null, null);
    }

}
