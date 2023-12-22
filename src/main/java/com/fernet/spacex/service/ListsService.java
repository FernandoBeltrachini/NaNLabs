package com.fernet.spacex.service;

public interface ListsService {
    String getListByNameOrCreateNewOne(String listName);

    String createList(String name, String boardId);

}
