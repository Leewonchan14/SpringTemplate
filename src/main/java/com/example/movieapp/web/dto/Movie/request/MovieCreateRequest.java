package com.example.movieapp.web.dto.Movie.request;


import com.example.movieapp.domain.enums.Genre;
import com.example.movieapp.domain.Movie;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class MovieCreateRequest {
    private String title;
    private Genre genre;

    public Movie toMovie() {
        return Movie.builder()
                .title(this.getTitle())
                .genre(this.getGenre())
                .build();
    }
}
