package com.example.movieapp.domain;

import com.example.movieapp.domain.enums.Genre;
import com.example.movieapp.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Movie extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String movieId;

    private String title;

    @Enumerated(EnumType.STRING)
    private Genre genre;
}
