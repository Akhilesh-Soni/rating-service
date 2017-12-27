package com.hashout.rating.api.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class MovieRatingDto extends ShowRatingDto {

    private final String cast;
    private final String screen;

    public MovieRatingDto(@JsonProperty("name") final String name,
                          @JsonProperty("language") final String language,
                          @JsonProperty("genre") final String genre,
                          @JsonProperty("type") final ShowType type,
                          @JsonProperty("averageRating") final float averageRating,
                          @JsonProperty("totalVotes") final int totalVotes,
                          @JsonProperty("cast") final String cast,
                          @JsonProperty("screen") final String screen) {
        super(name, language, genre, type, averageRating, totalVotes);
        this.cast = cast;
        this.screen = screen;
    }

    public String getCast() {
        return cast;
    }

    public String getScreen() {
        return screen;
    }
}
