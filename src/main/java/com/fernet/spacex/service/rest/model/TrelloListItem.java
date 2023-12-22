package com.fernet.spacex.service.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class that represents items from trello lists.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrelloListItem {
    private String id;
    private String name;
    private String idBoard;
}
