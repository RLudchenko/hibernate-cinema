package com.dev.cinema.service.impl;

import com.dev.cinema.dao.MovieSessionDao;
import com.dev.cinema.model.MovieSession;
import com.dev.cinema.service.MovieSessionService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieSessionServiceImpl implements MovieSessionService {
    @Autowired
    private MovieSessionDao movieSessionDao;

    @Override
    public List<MovieSession> getSession(Long movieId, LocalDate date) {
        return movieSessionDao.findSession(movieId, date);
    }

    @Override
    public MovieSession getMovieSessionById(Long id) {
        return movieSessionDao.getMovieSessionById(id);
    }

    @Override
    public MovieSession add(MovieSession session) {
        return movieSessionDao.add(session);
    }
}
