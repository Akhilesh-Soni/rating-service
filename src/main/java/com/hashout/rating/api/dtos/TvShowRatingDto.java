package com.hashout.rating.api.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * It contains details of tv show.
 **/
public class TvShowRatingDto extends ShowRatingDto {

    private final String channel;
    private final String host;

    public TvShowRatingDto(@JsonProperty("name") final String name,
                           @JsonProperty("language") final String language,
                           @JsonProperty("genre") final String genre,
                           @JsonProperty("type") final ShowType type,
                           @JsonProperty("averageRating") final float averageRating,
                           @JsonProperty("totalVotes") final int totalVotes,
                           @JsonProperty("channel") final String channel,
                           @JsonProperty("host") final String host) {
        super(name, language, genre, type, averageRating, totalVotes);
        this.channel = channel;
        this.host = host;
    }

    public String getChannel() {
        return channel;
    }

    public String getHost() {
        return host;
    }
}
