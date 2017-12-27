package com.hashout.rating.api.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.annotation.Nonnull;

public class UpdateRatingDto {

    @NotBlank
    private final String showName;

    @Nonnull
    private final ShowType showType;

    @Nonnull
    @Length(min = 1, max = 1, message = " should be between 1-5")
    private final String rating;

    @JsonCreator
    public UpdateRatingDto(@JsonProperty("showName") final String showName,
                           @JsonProperty("showType") final ShowType showType,
                           @JsonProperty("rating") final String rating) {
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


    public String getRating() {
        return rating;
    }
}
