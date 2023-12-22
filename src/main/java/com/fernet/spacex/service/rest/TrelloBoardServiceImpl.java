package com.fernet.spacex.service.rest;

import com.fernet.spacex.service.BoardService;
import com.fernet.spacex.service.rest.model.TrelloLabelListItem;
import com.fernet.spacex.service.rest.model.TrelloListItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.fernet.spacex.service.rest.TrelloUtils.createBoardsUrls;
import static com.fernet.spacex.service.rest.TrelloUtils.getAuthenticationParameters;
import static com.fernet.spacex.service.rest.model.TrelloResource.labels;
import static java.util.Objects.isNull;

/**
 * Class that holds the implementation on how we consume trello board api.
 */
@Service
public class TrelloBoardServiceImpl implements BoardService {

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

    /**
     * Method used to get board id;
     */
    @Override
    public String getBoard() {
        StringBuilder url = new StringBuilder(baseUrl)
                .append("1/boards/")
                .append(board)
                .append(getAuthenticationParameters(key, token));
        TrelloListItem response = restTemlate.getForObject(url.toString(), TrelloListItem.class);
        return isNull(response) ? null : response.getId();
    }

    /**
     * Method used to get labels of a board.
     */
    @Override
    public List<TrelloLabelListItem> getBoardLabels() {
        String url = createBoardsUrls(baseUrl, key, token, labels, board);
        TrelloLabelListItem[] trelloListResponse = restTemlate.getForObject(url, TrelloLabelListItem[].class);

        return (trelloListResponse != null) ? Arrays.asList(trelloListResponse) : Collections.emptyList();
    }

}
