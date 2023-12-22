package com.fernet.spacex.service.rest;

import com.fernet.spacex.service.rest.model.TrelloResource;

public class TrelloUtils {
    /**
     * Function that creates boards url given a resource type.
     */
    public static String createBoardsUrls(String baseUrl, String key, String token, TrelloResource resource, String boardId) {
        StringBuilder url = new StringBuilder(baseUrl)
                .append("1/boards/")
                .append(boardId)
                .append("/")
                .append(resource)
                .append(getAuthenticationParameters(key, token));
        return url.toString();
    }

    /**
     * Function to append authentication.
     */
    public static String getAuthenticationParameters(String key, String token) {
        return "?key=".concat(key).concat("&token=").concat(token);
    }

    /**
     * Method to create a trello resource url.
     */
    public static StringBuilder createResourcePathUrl(String baseUrl, String key, String token, TrelloResource resource, String boardId, String name) {
        return new StringBuilder(baseUrl)
                .append("1/")
                .append(resource)
                .append(getAuthenticationParameters(key, token))
                .append("&idBoard=")
                .append(boardId)
                .append("&name=")
                .append(name);
    }


}
