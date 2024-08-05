package com.example.movieapp.web.dto.Movie.response;

import com.example.movieapp.domain.Movie;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MovieCreateResponse {
    private String movieId;
    private String title;
    private String genre;

    public static MovieCreateResponse of(Movie movie) {
        return MovieCreateResponse.builder()
                .movieId(movie.getMovieId())
                .title(movie.getTitle())
                .genre(movie.getGenre().name())
                .build();
    }
}
