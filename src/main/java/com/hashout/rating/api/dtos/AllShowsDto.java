package com.hashout.rating.api.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AllShowsDto {

    private final List<MovieRatingDto> movies;
    private final List<TvShowRatingDto> tvShows;


    @JsonCreator
    public AllShowsDto(@JsonProperty("movies") final List<MovieRatingDto> movies,
                       @JsonProperty("tvShows") final List<TvShowRatingDto> tvShows) {
        this.movies = movies == null ? Collections.emptyList() : new ArrayList<>(movies);
        this.tvShows = tvShows == null ? Collections.emptyList() : new ArrayList<>(tvShows);
        ;
    }

    public List<MovieRatingDto> getMovies() {
        return new ArrayList<>(movies);
    }

    public List<TvShowRatingDto> getTvShows() {
        return new ArrayList<>(tvShows);
    }
}
