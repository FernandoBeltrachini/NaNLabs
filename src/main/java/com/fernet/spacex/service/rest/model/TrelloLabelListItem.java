package com.fernet.spacex.service.rest.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TrelloLabelListItem extends TrelloListItem{
    private TrelloLabelsColors color;
}
