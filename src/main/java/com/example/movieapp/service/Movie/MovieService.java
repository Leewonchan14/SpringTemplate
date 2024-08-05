package com.example.movieapp.service.Movie;

import com.example.movieapp.web.dto.Movie.request.MovieCreateRequest;
import com.example.movieapp.domain.Movie;

public interface MovieService {
    Movie createMovie(MovieCreateRequest request);

    Movie getMovieDetail(String movieId);
}
