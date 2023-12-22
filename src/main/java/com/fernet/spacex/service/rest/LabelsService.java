package com.fernet.spacex.service.rest;

import com.fernet.spacex.service.rest.model.TrelloLabelListItem;
import com.fernet.spacex.service.rest.model.TrelloLabelsColors;

import java.util.List;

public interface LabelsService {

    String createLabel(String name, TrelloLabelsColors color, String boardId);



    String getLabelIdByNameOrCreateNewOne(String labelName);

}
