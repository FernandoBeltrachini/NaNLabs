package com.fernet.spacex.service.rest;

public interface ListsService {
    String getListByNameOrCreateNewOne(String listName);

    String createList(String name, String boardId);

}
