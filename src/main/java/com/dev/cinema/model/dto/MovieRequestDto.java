package com.dev.cinema.model.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class MovieRequestDto {
    @NotNull(message = "title cannot be empty!")
    @Size(min = 1, max = 25, message = "Number of symbols must be between 1 and 25!")
    private String title;

    @NotNull(message = "Description cannot be empty!")
    @Size(min = 8, max = 300, message = "Number of symbols must be between 8 and 300!")
    private String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
