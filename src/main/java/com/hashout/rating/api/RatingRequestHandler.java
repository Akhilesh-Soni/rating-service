package com.hashout.rating.api;

import com.hashout.rating.api.dtos.*;
import com.hashout.rating.api.util.Data;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;

public class RatingRequestHandler {

    private final Map<String, MovieRatingDto> movies;
    private final Map<String, TvShowRatingDto> tvShows;

    public RatingRequestHandler() {
        movies = Data.intializeDataForMovies();
        tvShows = Data.intializeDataForTvShow();
    }

    public void handleUpdateRequest(final UpdateRatingDto updateRatingDto) throws InvalidPropertiesFormatException {
        validateRating(updateRatingDto.getRating());
        int rating = Integer.parseInt(updateRatingDto.getRating());
        if (updateRatingDto.getShowType().equals(ShowType.MOVIE)) {
            if (movies.containsKey(updateRatingDto.getShowName())) {
                MovieRatingDto movieRatingDto = movies.get(updateRatingDto.getShowName());
                movieRatingDto.updateAverageRating(rating);
                movieRatingDto.updateTotalCount();
            }
        } else {
            if (tvShows.containsKey(updateRatingDto.getShowName())) {
                TvShowRatingDto tvShowRatingDto = tvShows.get(updateRatingDto.getShowName());
                tvShowRatingDto.updateTotalCount();
                tvShowRatingDto.updateAverageRating(rating);
            }
        }

    }

    private void validateRating(String stringValue) throws InvalidPropertiesFormatException {
        int rating = Integer.parseInt(stringValue);
        if (rating < 1 || rating > 5) {
            throw new InvalidPropertiesFormatException("Rating must be between 1-5");
        }
    }

    public Response findRatingsOfShow(String showName) {
        Response response = null;
        if (movies.containsKey(showName)) {
            response = Response.status(Response.Status.OK).entity(movies.get(showName)).build();
        } else if (tvShows.containsKey(showName)) {
            response = Response.status(Response.Status.OK).entity(tvShows.get(showName)).build();
        }
        if (response == null) {
            throw new NotFoundException("Show with given name " + showName + " not found.");
        }
        return response;
    }

    public AllShowsDto getAllShowsRatings() {
        ArrayList<MovieRatingDto> moviesRatingDtos = new ArrayList<>();
        moviesRatingDtos.addAll(movies.values());

        ArrayList<TvShowRatingDto> tvShowRatingDtos = new ArrayList<>();
        tvShowRatingDtos.addAll(tvShows.values());

        return new AllShowsDto(moviesRatingDtos, tvShowRatingDtos);

    }
}
