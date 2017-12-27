package com.hashout.rating.api.dtos;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShowRatingDto {

    private final String name;
    private final String language;
    private final String genre;
    private final ShowType type;
    private float averageRating;
    private int totalVotes;


    @JsonCreator
    public ShowRatingDto(@JsonProperty("name") final String name,
                         @JsonProperty("language") final String language,
                         @JsonProperty("genre") final String genre,
                         @JsonProperty("type") final ShowType type,
                         @JsonProperty("averageRating") final float averageRating,
                         @JsonProperty("totalVotes") final int totalVotes) {
        this.name = name;
        this.language = language;
        this.genre =  genre;
        this.type = type;
        this.averageRating = averageRating;
        this.totalVotes = totalVotes;
    }

    public String getName() {
        return name;
    }

    public String getLanguage() {
        return language;
    }

    public String getGenre() {
        return genre;
    }

    public ShowType getType() {
        return type;
    }

    public float getAverageRating() {
        return averageRating;
    }

    public int getTotalVotes() {
        return totalVotes;
    }

    public void updateTotalCount() {
        this.totalVotes++;
    }

    public void updateAverageRating(int rating) {
        this.averageRating = (this.averageRating + rating) / 2;
    }
}
