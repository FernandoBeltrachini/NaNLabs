package com.fernet.spacex.controller.request;

import com.fernet.spacex.model.CardType;
import com.fernet.spacex.service.model.CardCategory;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Create card request.
 */
@Data
public class CreateCardRequest {
    @NotNull(message = "Card type is required")
    private CardType type;
    private String tittle;
    private String description;
    private CardCategory category;
}
