package com.fernet.spacex.service.rest;

import com.fernet.spacex.service.CardsService;
import com.fernet.spacex.service.model.ParamType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static com.fernet.spacex.service.rest.TrelloUtils.getAuthenticationParameters;

/**
 * Class that holds the implementation on how we consume trello card list api.
 */
@Service
public class TrelloCardsServiceImpl implements CardsService {
    @Autowired
    private RestTemplate restTemlate;
    @Value("${trello.url}")
    private String baseUrl;
    @Value("${trello.key}")
    private String key;
    @Value("${trello.token}")
    private String token;

    /**
     * Rest call to create a trello card.
     */
    @Override
    public void createCard(Map<ParamType, String> parameters) {
        StringBuilder url = new StringBuilder(baseUrl)
                .append("1/cards")
                .append(getAuthenticationParameters(key, token))
                .append("&idList=")
                .append(parameters.get(ParamType.LIST));

        if (parameters.containsKey(ParamType.NAME)) {
            url.append("&name=").append(parameters.get(ParamType.NAME));
        }
        if (parameters.containsKey(ParamType.LABEL)) {
            url.append("&idLabels=").append(parameters.get(ParamType.LABEL));
        }
        if (parameters.containsKey(ParamType.DESCRIPTION)) {
            url.append("&desc=").append(parameters.get(ParamType.DESCRIPTION));
        }
        if (parameters.containsKey(ParamType.MEMBERS)) {
            url.append("&idMembers=").append(parameters.get(ParamType.MEMBERS));
        }

        restTemlate.postForEntity(url.toString(), null, null);
    }
}
