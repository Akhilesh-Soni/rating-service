package com.hashout.rating.api.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Nonnull;

public class UpdateRatingDto {

    @Nonnull
    private final String showName;

    @Nonnull
    private final ShowType showType;

    @Nonnull
    private final int rating;

    @JsonCreator
    public UpdateRatingDto(@JsonProperty("showName") final String showName,
                           @JsonProperty("showType") final ShowType showType,
                           @JsonProperty("rating") final int rating) {
        this.showName = showName;
        this.showType = showType;
        this.rating = rating;
    }

    public String getShowName() {
        return showName;
    }


    public ShowType getShowType() {
        return showType;
    }


    public int getRating() {
        return rating;
    }
}
