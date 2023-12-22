package com.fernet.spacex.service.rest;

import com.fernet.spacex.service.rest.model.TrelloListItem;
import com.fernet.spacex.service.rest.model.TrelloResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.fernet.spacex.service.rest.TrelloUtils.createBoardsUrls;
import static com.fernet.spacex.service.rest.TrelloUtils.createResourcePathUrl;
import static com.fernet.spacex.service.rest.model.TrelloResource.lists;
import static java.util.Objects.isNull;

/**
 * Class that holds the implementation of trello api.
 */
@Service
public class TrelloListsServiceImpl implements ListsService {
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

    @Autowired
    private BoardService trelloBoardService;

    /**
     * Method used to create a list at trello.
     */
    @Override
    public String createList(String name, String boardId) {
        StringBuilder url = createResourcePathUrl(baseUrl, key, token, TrelloResource.lists, boardId, name);
        ResponseEntity<TrelloListItem> response = restTemlate.postForEntity(url.toString(), null, TrelloListItem.class);
        return isNull(response.getBody()) ? null : response.getBody().getId();
    }

    /**
     * Get list by name on board.
     * If not found, returns the first list present.
     * <p>
     * throws IllegalArgumentException if no list on board.
     */
    @Override
    public String getListByNameOrCreateNewOne(String listName) {
        String url = createBoardsUrls(baseUrl, key, token, lists, board);
        TrelloListItem[] trelloListResponse = restTemlate.getForObject(url, TrelloListItem[].class);
        if (isNull(trelloListResponse) || trelloListResponse.length == 0) {
            return createList(listName, trelloBoardService.getBoard());
        }

        List<TrelloListItem> trelloBoardList = Arrays.asList(trelloListResponse);
        Optional<String> id = trelloBoardList.stream()
                .filter(e -> e.getName().equals(listName))
                .findFirst()
                .map(TrelloListItem::getId);

        return id.orElseGet(() -> createList(listName, trelloBoardList.get(0).getIdBoard()));

    }

}
