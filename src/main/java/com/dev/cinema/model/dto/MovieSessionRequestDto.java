package com.dev.cinema.model.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class MovieSessionRequestDto {
    @NotNull(message = "Show time can't be null! ")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}",
            message = "Session time doesn't match with the pattern!")
    private LocalDateTime sessionTime;

    @NotNull(message = "CinemaHall id cannot be empty!")
    private Long cinemaHallId;

    public LocalDateTime getSessionTime() {
        return sessionTime;
    }

    public void setSessionTime(LocalDateTime sessionTime) {
        this.sessionTime = sessionTime;
    }

    public Long getCinemaHallId() {
        return cinemaHallId;
    }

    public void setCinemaHallId(Long cinemaHallId) {
        this.cinemaHallId = cinemaHallId;
    }
}
