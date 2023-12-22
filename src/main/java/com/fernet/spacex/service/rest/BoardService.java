package com.fernet.spacex.service.rest;

import com.fernet.spacex.service.rest.model.TrelloLabelListItem;

import java.util.List;

public interface BoardService {
    String getBoard();

    List<TrelloLabelListItem> getBoardLabels();

}
