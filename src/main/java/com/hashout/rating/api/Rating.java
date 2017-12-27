package com.hashout.rating.api;

import com.hashout.rating.api.dtos.*;
import com.hashout.rating.api.util.Data;

import javax.annotation.Nonnull;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Map;

/**
 * It is the rest resource exposed to external client.
 **/

@Path("/rating-service")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Rating {

    private final Map<String, TvShowRatingDto> tvShows;
    private final Map<String, MovieRatingDto> movies;

    public Rating() {
        tvShows = Data.intializeDataForTvShow();
        movies = Data.intializeDataForMovies();
    }

    @POST
    @Path("/update-rating")
    public Response updateRating(@Valid UpdateRatingDto updateRatingDto) {
        if (updateRatingDto.getShowType().equals(ShowType.MOVIE)) {
            if (movies.containsKey(updateRatingDto.getShowName())) {
                MovieRatingDto movieRatingDto = movies.get(updateRatingDto.getShowName());
                movieRatingDto.updateTotalCount();
                movieRatingDto.updateAverageRating(updateRatingDto.getRating());
            }
        } else {
            if (tvShows.containsKey(updateRatingDto.getShowName())) {
                TvShowRatingDto tvShowRatingDto = tvShows.get(updateRatingDto.getShowName());
                tvShowRatingDto.updateTotalCount();
                tvShowRatingDto.updateAverageRating(updateRatingDto.getRating());
            }
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Path("/show-name/{show_name}")
    public Response getAverageRating(@PathParam("show_name") @Nonnull final String showName) {

        if (movies.containsKey(showName)) {
            return Response.status(Response.Status.OK).entity(movies.get(showName)).build();
        } else if (tvShows.containsKey(showName)) {
            return Response.status(Response.Status.OK).entity(tvShows.get(showName)).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/all-show-ratings")
    public Response getAll() {

        ArrayList<MovieRatingDto> moviesRatingDtos = new ArrayList<>();
        moviesRatingDtos.addAll(movies.values());

        ArrayList<TvShowRatingDto> tvShowRatingDtos = new ArrayList<>();
        tvShowRatingDtos.addAll(tvShows.values());

        AllShowsDto allShowsDto = new AllShowsDto(moviesRatingDtos, tvShowRatingDtos);

        return Response.status(Response.Status.OK).entity(allShowsDto).build();
    }

}
