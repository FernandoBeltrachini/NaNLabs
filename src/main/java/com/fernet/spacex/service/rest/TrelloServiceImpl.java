package com.fernet.spacex.service.rest;

import com.fernet.spacex.service.model.ParamType;
import com.fernet.spacex.service.rest.model.TrelloListItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.*;

/**
 * Class that holds the implementation on how we consume trello api.
 */
@Service
public class TrelloServiceImpl implements TrelloService {

    private RestTemplate restTemlate = new RestTemplate();

    @Value("${trello.url}")
    private String baseUrl;
    @Value("${key}")
    private String key;
    @Value("${token}")
    private String token;

    @Value("${board}")
    private String board;

    /**
     * Function to append authentication.
     */
    private String getAuthenticationParameters() {
        return "?key=".concat(key).concat("&token=").concat(token);
    }


    /**
     * Function that creates boards url given a resource type.
     */
    private String createBoardsUrls(String resource) {
        StringBuilder url = new StringBuilder(baseUrl)
                .append("1/boards/")
                .append(board)
                .append("/")
                .append(resource)
                .append(getAuthenticationParameters());
        return url.toString();
    }

    /**
     * Rest call to create a trello card.
     */
    @Override
    public void createCard(Map<ParamType, String> parameters) {
        StringBuilder url = new StringBuilder(baseUrl)
                .append("1/cards")
                .append(getAuthenticationParameters())
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

    /**
     * Get list by name on board.
     * If not found, returns the first list present.
     * <p>
     * throws IllegalArgumentException if no list on board.
     */
    @Override
    public String getListByName(String listName) {
        String url = createBoardsUrls("lists");
        TrelloListItem[] trelloListResponse = restTemlate.getForObject(url, TrelloListItem[].class);
        if (isNull(trelloListResponse) || trelloListResponse.length == 0) {
            throw new IllegalArgumentException("No list on board");
        }

        List<TrelloListItem> trelloBoardList = Arrays.asList(trelloListResponse);

        return trelloBoardList.stream()
                .filter(e -> e.getName().equals(listName))
                .findFirst()
                .map(TrelloListItem::getId)
                .orElse(trelloBoardList.stream()
                        .findFirst()
                        .get()
                        .getId());
    }

    /**
     * Method used to get the id of a label given his name.
     * Returns empty string if not found.
     *
     * @throws IllegalArgumentException if no labels found.
     */
    @Override
    public String getLabelIdByName(String labelName) {
        String url = createBoardsUrls("labels");
        TrelloListItem[] trelloListResponse = restTemlate.getForObject(url, TrelloListItem[].class);
        if (isNull(trelloListResponse) || trelloListResponse.length == 0) {
            // We could check that it does not exist we can create it, but for some reason im getting an error on trello API
            // This post throws invalid id
            // https://api.trello.com/1/labels?name=asd&color=green&idBoard=ukk8ZSed&key={{key}}&token={{token}}
            throw new IllegalArgumentException("Labels not found");
        }
        List<TrelloListItem> trelloBoardList = Arrays.asList(trelloListResponse);
        return trelloBoardList.stream()
                .filter(e -> e.getName().equals(labelName))
                .findFirst()
                .map(TrelloListItem::getId)
                .orElse("");

    }

    @Override
    public List<String> getBoardMembers() {
        String url = createBoardsUrls("members");
        TrelloListItem[] trelloListResponse = restTemlate.getForObject(url, TrelloListItem[].class);
        return isNull(trelloListResponse) ?
                new ArrayList<>()
                : Arrays.stream(trelloListResponse)
                .map(TrelloListItem::getId)
                .collect(Collectors.toList());
    }
}
