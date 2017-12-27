package com.hashout.rating.api;

import com.hashout.rating.api.dtos.*;
import com.hashout.rating.api.util.Data;
import com.hashout.rating.api.util.SortByAverageRating;
import com.hashout.rating.api.util.SortByName;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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

    private void validateRating(final String stringValue) throws InvalidPropertiesFormatException {
        int rating = Integer.parseInt(stringValue);
        if (rating < 1 || rating > 5) {
            throw new InvalidPropertiesFormatException("Rating must be between 1-5");
        }
    }

    public Response findRatingsOfShow(final String showName) {
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
        final ArrayList<MovieRatingDto> moviesRatingDtos = new ArrayList<>();
        moviesRatingDtos.addAll(movies.values());

        final ArrayList<TvShowRatingDto> tvShowRatingDtos = new ArrayList<>();
        tvShowRatingDtos.addAll(tvShows.values());

        return new AllShowsDto(moviesRatingDtos, tvShowRatingDtos);

    }

    public AllShowsDto getAllShowsRatingsFilteredByLanguage(final String language) {
        Predicate<MovieRatingDto> movieRatingDtoPredicate = movieRatingDto ->
                movieRatingDto.getLanguage().equalsIgnoreCase(language);
        Predicate<TvShowRatingDto> tvShowRatingDtoPredicate = tvShowRatingDto ->
                tvShowRatingDto.getLanguage().equalsIgnoreCase(language);

        List<MovieRatingDto> filteredMovies = movies.values().stream().filter(movieRatingDtoPredicate)
                .collect(Collectors.toList());
        List<TvShowRatingDto> filteredTvShows = tvShows.values().stream().filter(tvShowRatingDtoPredicate)
                .collect(Collectors.toList());

        return new AllShowsDto(filteredMovies, filteredTvShows);
    }

    public AllShowsDto sortTheShowsByName() {
        AllShowsDto allShowsDto = getAllShowsRatings();
        List<MovieRatingDto> movieRatingDtos = allShowsDto.getMovies();
        List<TvShowRatingDto> tvShowRatingDtos = allShowsDto.getTvShows();

        movieRatingDtos.sort(new SortByName());
        tvShowRatingDtos.sort(new SortByName());

        return new AllShowsDto(movieRatingDtos, tvShowRatingDtos);
    }

    public AllShowsDto sortTheShowsByAverageRating() {

        AllShowsDto allShowsDto = getAllShowsRatings();
        List<MovieRatingDto> movieRatingDtos = allShowsDto.getMovies();
        List<TvShowRatingDto> tvShowRatingDtos = allShowsDto.getTvShows();

        movieRatingDtos.sort(new SortByAverageRating());
        tvShowRatingDtos.sort(new SortByAverageRating());

        return new AllShowsDto(movieRatingDtos, tvShowRatingDtos);
    }
}
