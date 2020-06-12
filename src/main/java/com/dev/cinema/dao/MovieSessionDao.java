package com.dev.cinema.dao;

import com.dev.cinema.model.MovieSession;
import java.time.LocalDate;
import java.util.List;

public interface MovieSessionDao {
    List<MovieSession> findSession(Long movieId, LocalDate date);

    MovieSession getMovieSessionById(Long id);

    MovieSession add(MovieSession session);
}
