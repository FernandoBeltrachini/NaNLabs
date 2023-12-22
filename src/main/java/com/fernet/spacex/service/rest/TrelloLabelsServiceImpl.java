package com.fernet.spacex.service.rest;

import com.fernet.spacex.service.rest.model.TrelloLabelListItem;
import com.fernet.spacex.service.rest.model.TrelloLabelsColors;
import com.fernet.spacex.service.rest.model.TrelloListItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.fernet.spacex.service.rest.TrelloUtils.createBoardsUrls;
import static com.fernet.spacex.service.rest.TrelloUtils.createResourcePathUrl;
import static com.fernet.spacex.service.rest.model.TrelloResource.labels;
import static java.util.Objects.isNull;

/**
 * Class that holds the implementation of the labels api from trello.
 */
@Service
public class TrelloLabelsServiceImpl implements LabelsService {
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
     * Method used to create a label in trello.
     *
     * @param name    the name of the label.
     * @param color   the color of the label.
     * @param boardId the board where we want to create the label.
     */
    @Override
    public String createLabel(String name, TrelloLabelsColors color, String boardId) {
        StringBuilder url = createResourcePathUrl(baseUrl, key, token, labels, boardId, name)
                .append("&color=")
                .append(color);

        ResponseEntity<TrelloListItem> response = restTemlate.postForEntity(url.toString(), null, TrelloListItem.class);

        return isNull(response) || isNull(response.getBody()) ? null : response.getBody().getId();
    }



    /**
     * Method used to get the id of a label given his name.
     * Returns empty string if not found.
     *
     * @throws IllegalArgumentException if no labels found.
     */
    @Override
    public String getLabelIdByNameOrCreateNewOne(String labelName) {
        List<TrelloLabelListItem> trelloListResponse = trelloBoardService.getBoardLabels();
        if (trelloListResponse.isEmpty()) {
            return createLabel(labelName, TrelloLabelsColors.getRandomColor(), trelloBoardService.getBoard());
        }

        Optional<String> listId = trelloListResponse.stream()
                .filter(e -> e.getName().equals(labelName))
                .findFirst()
                .map(TrelloListItem::getId);

        // If item in list not found we are creating a new one under that name.
        return listId.orElseGet(() -> createLabel(labelName, TrelloLabelsColors.getRandomColor(), trelloListResponse.get(0).getIdBoard()));
    }

}
