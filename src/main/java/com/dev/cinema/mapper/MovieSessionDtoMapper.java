package com.dev.cinema.mapper;

import com.dev.cinema.model.Movie;
import com.dev.cinema.model.MovieSession;
import com.dev.cinema.model.dto.MovieSessionRequestDto;
import com.dev.cinema.model.dto.MovieSessionResponseDto;
import com.dev.cinema.service.CinemaHallService;
import com.dev.cinema.service.MovieService;
import org.springframework.stereotype.Component;

@Component
public class MovieSessionDtoMapper {
    private final CinemaHallService cinemaHallService;
    private final MovieService movieService;

    public MovieSessionDtoMapper(CinemaHallService cinemaHallService, MovieService movieService) {
        this.cinemaHallService = cinemaHallService;
        this.movieService = movieService;
    }

    public MovieSession dtoToMovieSession(MovieSessionRequestDto movieSessionRequestDto) {
        MovieSession movieSession = new MovieSession();
        movieSession.setCinemaHall(cinemaHallService
                .getCinemaHallById(movieSessionRequestDto.getCinemaHallId()));
        movieSession.setSessionTime(movieSessionRequestDto.getSessionTime());

        return movieSession;
    }

    public MovieSessionResponseDto movieSessionToDto(MovieSession movieSession) {
        MovieSessionResponseDto movieSessionResponseDto = new MovieSessionResponseDto();
        Movie movie = movieSession.getMovie();
        movieSessionResponseDto.setCinemaHallId(movieSession.getCinemaHall().getId());
        movieSessionResponseDto.setMovieId(movie.getId());
        movieSessionResponseDto.setSessionTime(movieSession.getSessionTime());

        return movieSessionResponseDto;
    }
}
