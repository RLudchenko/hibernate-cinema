package com.dev.cinema.model.dto;

import java.time.LocalDateTime;

public class MovieSessionRequestDto {
    private LocalDateTime sessionTime;
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
