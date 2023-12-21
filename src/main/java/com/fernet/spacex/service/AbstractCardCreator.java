package com.fernet.spacex.service;

import com.fernet.spacex.controller.request.CreateCardRequest;
import com.fernet.spacex.service.model.ParamType;

import java.util.Map;

public abstract class AbstractCardCreator implements GetCardParameters {
    public Map<ParamType, String> getParameters(CreateCardRequest createCardRequest){
        runValidations(createCardRequest);
        return getCardParameters(createCardRequest);
    }
}
