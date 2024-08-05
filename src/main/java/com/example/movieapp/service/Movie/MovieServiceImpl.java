package com.example.movieapp.service.Movie;

import com.example.movieapp.web.dto.Movie.request.MovieCreateRequest;
import com.example.movieapp.domain.Movie;
import com.example.movieapp.repository.MovieRepository;
import com.example.movieapp.api.exception.CustomException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.movieapp.api.code.ErrorCode.MOVIE_NOT_FOUND;

@Service
@AllArgsConstructor
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;

    @Override
    public Movie createMovie(MovieCreateRequest request) {
        Movie newMovie = request.toMovie();
        return movieRepository.save(newMovie);
    }

    @Override
    public Movie getMovieDetail(String movieId) {
        return movieRepository.findById(movieId)
                .orElseThrow(() -> new CustomException(MOVIE_NOT_FOUND));
    }
}
