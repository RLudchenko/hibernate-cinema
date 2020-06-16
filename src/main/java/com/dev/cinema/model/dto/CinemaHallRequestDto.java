package com.dev.cinema.model.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CinemaHallRequestDto {
    @NotNull(message = "Capacity cannot be empty!")
    private int capacity;

    @NotNull(message = "description cannot be empty!")
    @Size(min = 8, max = 200, message = "number of symbols must be between 8 and 200!")
    private String description;

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
