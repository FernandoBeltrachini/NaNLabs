package com.fernet.spacex.config;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Class that represents an error message response.
 * */
@Data
@AllArgsConstructor
public class ErrorMessage {
    private String description;
}
