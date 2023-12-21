package com.fernet.spacex.service.rest;

import com.fernet.spacex.service.model.ParamType;

import java.util.List;
import java.util.Map;

public interface TrelloService {

    void createCard(Map<ParamType, String> parameters);

    String getListByName(String listName);

    String getLabelIdByName(String labelName);

    List<String> getBoardMembers();
}
