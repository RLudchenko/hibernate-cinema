package com.dev.cinema.mapper;

import com.dev.cinema.model.Movie;
import com.dev.cinema.model.dto.MovieRequestDto;
import com.dev.cinema.model.dto.MovieResponseDto;
import org.springframework.stereotype.Component;

@Component
public class MovieDtoMapper {
    public Movie dtoToMovie(MovieRequestDto movieRequestDto) {
        Movie movie = new Movie();
        movie.setTitle(movieRequestDto.getTitle());
        movie.setDescription(movieRequestDto.getDescription());

        return movie;
    }

    public MovieResponseDto movieToDto(Movie movie) {
        MovieResponseDto movieResponseDto = new MovieResponseDto();
        movieResponseDto.setMovieId(movie.getId());
        movieResponseDto.setTitle(movie.getTitle());
        movieResponseDto.setDescription(movie.getDescription());
        movieResponseDto.setMovieId(movie.getId());

        return movieResponseDto;
    }
}
