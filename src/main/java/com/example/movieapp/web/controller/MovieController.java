package com.example.movieapp.web.controller;

import com.example.movieapp.api.payload.ApiResponse;
import com.example.movieapp.web.dto.Movie.request.MovieCreateRequest;
import com.example.movieapp.web.dto.Movie.response.MovieCreateResponse;
import com.example.movieapp.web.dto.Movie.response.MovieDetailResponse;
import com.example.movieapp.service.Movie.MovieService;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.bind.annotation.*;

import static com.example.movieapp.api.code.SuccessCode.MOVIE_OK;

@RestController
@RequestMapping("/api/v1/movies")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @PostMapping
    public ApiResponse<MovieCreateResponse> createMovie(
            @RequestBody MovieCreateRequest request
    ) {
        return ApiResponse.onSuccess(
                MOVIE_OK,
                MovieCreateResponse.of(movieService.createMovie(request))
        );
    }

    @GetMapping("/{movieId}")
    public ApiResponse<MovieDetailResponse> getMovieDetail(
            @PathVariable @Length(min = 0, max = 5) String movieId
    ) {
        return ApiResponse.onSuccess(
                MOVIE_OK,
                MovieDetailResponse.of(movieService.getMovieDetail(movieId))
        );
    }

    @GetMapping
    public ApiResponse<String> getString() {
        return ApiResponse.onSuccess(
                MOVIE_OK,
                "Hello, World!"
        );
    }
}
