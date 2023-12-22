package com.fernet.spacex.service.rest;

import com.fernet.spacex.service.MembersService;
import com.fernet.spacex.service.rest.model.TrelloListItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.fernet.spacex.service.rest.TrelloUtils.createBoardsUrls;
import static com.fernet.spacex.service.rest.model.TrelloResource.members;
import static java.util.Objects.isNull;

/**
 * Class that holds the implementation for the members api on trello.
 */
@Service
public class TrelloMembersServiceImpl implements MembersService {

    @Autowired
    private RestTemplate restTemlate;

    @Value("${trello.url}")
    private String baseUrl;
    @Value("${trello.key}")
    private String key;
    @Value("${trello.token}")
    private String token;
    @Value("${trello.board}")
    private String board;

    @Override
    public List<String> getBoardMembers() {
        String url = createBoardsUrls(baseUrl, key, token, members, board);
        TrelloListItem[] trelloListResponse = restTemlate.getForObject(url, TrelloListItem[].class);
        return isNull(trelloListResponse) ?
                new ArrayList<>()
                : Arrays.stream(trelloListResponse)
                .map(TrelloListItem::getId)
                .collect(Collectors.toList());
    }
}
