package com.hashout.rating.api.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class AllShowsDto {

    private final List<MovieRatingDto> movies;
    private final List<TvShowRatingDto> tvShows;


    @JsonCreator
    public AllShowsDto(@JsonProperty("movies") final List<MovieRatingDto> movies,
                       @JsonProperty("tvShows") final List<TvShowRatingDto> tvShows) {
        this.movies = movies;
        this.tvShows = tvShows;
    }

    public List<MovieRatingDto> getMovies() {
        return movies;
    }

    public List<TvShowRatingDto> getTvShows() {
        return tvShows;
    }
}
