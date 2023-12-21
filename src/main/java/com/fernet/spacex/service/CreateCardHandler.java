package com.fernet.spacex.service;

import com.fernet.spacex.model.CardType;

public interface CreateCardHandler {
    GetCardParameters getCardParameters(CardType cardType);
}
