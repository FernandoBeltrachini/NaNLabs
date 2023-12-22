package com.fernet.spacex.service;

import com.fernet.spacex.service.model.ParamType;

import java.util.Map;

public interface CardsService {

    void createCard(Map<ParamType, String> parameters);

}
