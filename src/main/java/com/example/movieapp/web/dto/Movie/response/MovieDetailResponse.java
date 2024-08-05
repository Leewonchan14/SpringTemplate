package com.example.movieapp.web.dto.Movie.response;

import com.example.movieapp.domain.Movie;
import com.example.movieapp.domain.enums.Genre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieDetailResponse {
    private String movieId;
    private String title;
    private Genre genre;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static MovieDetailResponse of(Movie movie) {
        return MovieDetailResponse.builder()
                .movieId(movie.getMovieId())
                .title(movie.getTitle())
                .genre(movie.getGenre())
                .createdAt(movie.getCreatedAt())
                .updatedAt(movie.getUpdatedAt())
                .build();
    }
}
