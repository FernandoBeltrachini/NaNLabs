package com.fernet.spacex.service.rest.model;

import java.util.Random;

/**
 * Enum that holds different types of colors availables in trello.
 */
public enum TrelloLabelsColors {
    yellow,
    purple,
    blue,
    red,
    green,
    orange,
    black,
    sky,
    pink,
    lime;
    private static final TrelloLabelsColors[] VALUES = values();
    private static final int SIZE = VALUES.length;
    private static final Random RANDOM = new Random();

    public static TrelloLabelsColors getRandomColor() {
        return VALUES[RANDOM.nextInt(SIZE)];
    }
}
