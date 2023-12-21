package com.fernet.spacex.service;

import com.fernet.spacex.controller.request.CreateCardRequest;
import com.fernet.spacex.service.model.ParamType;

import java.util.Map;

public interface GetCardParameters {
    Map<ParamType, String> getCardParameters(CreateCardRequest createCardRequest);
    Map<ParamType, String> getParameters(CreateCardRequest createCardRequest);
    void runValidations(CreateCardRequest createCardRequest);
}
