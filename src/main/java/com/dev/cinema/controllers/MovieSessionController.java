package com.dev.cinema.controllers;

import com.dev.cinema.mapper.MovieSessionDtoMapper;
import com.dev.cinema.model.MovieSession;
import com.dev.cinema.model.dto.MovieSessionRequestDto;
import com.dev.cinema.model.dto.MovieSessionResponseDto;
import com.dev.cinema.service.MovieSessionService;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/moviesessions")
public class MovieSessionController {
    private final MovieSessionService movieSessionService;
    private final MovieSessionDtoMapper movieSessionDtoMapper;

    public MovieSessionController(MovieSessionService movieSessionService,
                                  MovieSessionDtoMapper movieSessionDtoMapper) {
        this.movieSessionService = movieSessionService;
        this.movieSessionDtoMapper = movieSessionDtoMapper;
    }

    @PostMapping
    public void addMovieSession(@RequestBody MovieSessionRequestDto movieSessionRequestDto) {
        movieSessionService.add(movieSessionDtoMapper.dtoToMovieSession(movieSessionRequestDto));
    }

    @GetMapping
    public List<MovieSessionResponseDto> getAvailableSession(@RequestParam Long movieId,
                                                             @RequestParam
                                                             @DateTimeFormat(iso
                                                                     = DateTimeFormat.ISO.DATE)
                                                                     LocalDate sessionTime) {
        List<MovieSession> movieSessions =
                movieSessionService.findSession(movieId, sessionTime);

        return movieSessions.stream()
                .map(movieSessionDtoMapper::movieSessionToDto)
                .collect(Collectors.toList());
    }
}
