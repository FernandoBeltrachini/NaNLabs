package com.fernet.spacex.service.rest.model;

import lombok.Data;

@Data
public class TrelloLabelListItem extends TrelloListItem{
    private TrelloLabelsColors color;
}
