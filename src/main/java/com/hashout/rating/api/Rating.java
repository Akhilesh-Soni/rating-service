package com.hashout.rating.api;

import com.hashout.rating.api.dtos.AllShowsDto;
import com.hashout.rating.api.dtos.MovieRatingDto;
import com.hashout.rating.api.dtos.TvShowRatingDto;
import com.hashout.rating.api.dtos.UpdateRatingDto;
import org.eclipse.jetty.http.HttpStatus;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.InvalidPropertiesFormatException;

/**
 * It is the rest resource exposed to external client.
 **/

@Path("/rating-service")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Rating {

    private final RatingRequestHandler ratingRequestHandler;

    public Rating(final RatingRequestHandler ratingRequestHandler) {
        this.ratingRequestHandler = ratingRequestHandler;
    }

    /**
     * Entry point to update the rating of show.
     * It will validate the request body {@link UpdateRatingDto}
     * <br>
     * Example of JSON payload:
     * <p>
     * {<br>
     *      "showName":"Bigg Boss",<br>
     *      "showType":"TV_SHOW",<br>
     *      "rating":"5"<br>
     * }
     *
     * @return {@link Response} <br>
     * 204 if successful<br>
     * 422 if {@link UpdateRatingDto} validation failed
     **/


    @POST
    @Path("/update-rating")
    public Response updateRating(@Valid UpdateRatingDto updateRatingDto) {
        try {
            ratingRequestHandler.handleUpdateRequest(updateRatingDto);
        } catch (final InvalidPropertiesFormatException | NumberFormatException e) {
            return Response.status(HttpStatus.UNPROCESSABLE_ENTITY_422).entity(e.getLocalizedMessage()).build();
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }


    /**
     * Entry point to get the rating of show.
     * It will return the response body {@link TvShowRatingDto} or {@link MovieRatingDto}
     * <br>
     * Example of JSON payload:
     * <p>
     * {<br>
     *      "name": "Bigg Boss", <br>
     *      "language": "Hindi", <br>
     *      "genre": "Reality Show", <br>
     *      "type": "TV_SHOW", <br>
     *      "averageRating": 4, <br>
     *      "totalVotes": 100000, <br>
     *      "channel": "Colors", <br>
     *      "host": "Salman Khan" <br>
     * }
     *
     * @return {@link Response} <br>
     * 200 if successful.<br>
     * 404 if show-name not found.<br>
     **/
    @GET
    @Path("/show-name/{show_name}")
    public Response getRatingOfShow(@PathParam("show_name") @NotBlank final String showName) {
        try {
            return ratingRequestHandler.findRatingsOfShow(showName);
        } catch (final NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }


    /**
     * Entry point to get the rating of all shows.
     * It will return the response body {@link AllShowsDto}
     * <br>
     * Example of JSON payload:
     * <p>
     * {<br>
     * "movies": [<br>
     *              {<br>
     *                  "name": "Start Wars",<br>
     *                  "language": "English",<br>
     *                  "genre": "Action, Adventure",<br>
     *                  "type": "MOVIE",<br>
     *                  "averageRating": 3.5,<br>
     *                  "totalVotes": 25356000,<br>
     *                  "cast": "Mark Hamil, Daisy Ridley",<br>
     *                  "screen": "Imax"<br>
     *              }<br>
     *          ],<br>
     * "tvShows": [<br>
     *              {<br>
     *                  "name": "Bigg Boss",<br>
     *                  "language": "Hindi",<br>
     *                  "genre": "Reality Show",<br>
     *                  "type": "TV_SHOW",<br>
     *                  "averageRating": 4,<br>
     *                  "totalVotes": 100000,<br>
     *                  "channel": "Colors",<br>
     *                  "host": "Salman Khan"<br>
     *              }<br>
     *          ]<br>
     * }<br>
     *
     * @return {@link Response} <br>
     * 200 if successful.<br>
     **/
    @GET
    @Path("/all-show-ratings")
    public Response getAllShowsRating() {

        AllShowsDto allShowsDto = ratingRequestHandler.getAllShowsRatings();
        return Response.status(Response.Status.OK).entity(allShowsDto).build();
    }

    /**
     * Entry point to get the rating of all shows filtered by language.
     * It will return the response body {@link AllShowsDto}
     * <br>
     * Example of JSON payload:
     * <p>
     * {<br>
     * "movies": [<br>
     *              {<br>
     *                  "name": "Tiger Zinda Hai",<br>
     *                  "language": "Hindi",<br>
     *                  "genre": "Action, Drama",<br>
     *                  "type": "MOVIE",<br>
     *                  "averageRating": 3.5,<br>
     *                  "totalVotes": 330000,<br>
     *                  "cast": "Salman Khan, Katrin Kaif",<br>
     *                  "screen": null<br>
     *              }<br>
     *          ],<br>
     * "tvShows": [<br>
     *              {<br>
     *                  "name": "Bigg Boss",<br>
     *                  "language": "Hindi",<br>
     *                  "genre": "Reality Show",<br>
     *                  "type": "TV_SHOW",<br>
     *                  "averageRating": 4,<br>
     *                  "totalVotes": 100000,<br>
     *                  "channel": "Colors",<br>
     *                  "host": "Salman Khan"<br>
     *              }<br>
     *          ]<br>
     * }<br>
     *
     * @return {@link Response} <br>
     * 200 if successful.<br>
     **/
    @GET
    @Path("/language/{language}")
    public Response getShowsFilteredByLanguage(@PathParam("language") @NotBlank final String language) {
        AllShowsDto allShowsDto = ratingRequestHandler.getAllShowsRatingsFilteredByLanguage(language);
        return Response.status(Response.Status.OK).entity(allShowsDto).build();
    }


    /**
     * Entry point to get the rating of all shows sorted by name or average rating.
     * It will return the response body {@link AllShowsDto}
     * <br>
     * Example of JSON payload sorted by average rating:
     * <p>
     * {<br>
     * "movies": [<br>
     *              {<br>
     *                  "name": "Tiger Zinda Hai",<br>
     *                  "language": "Hindi",<br>
     *                  "genre": "Action, Drama",<br>
     *                  "type": "MOVIE",<br>
     *                  "averageRating": 2.75,<br>
     *                  "totalVotes": 330001,<br>
     *                  "cast": "Salman Khan, Katrin Kaif",<br>
     *                  "screen": null<br>
     *              },<br>
     *              {<br>
     *                  "name": "Start Wars",<br>
     *                  "language": "English",<br>
     *                  "genre": "Action, Adventure",<br>
     *                  "type": "MOVIE",<br>
     *                  "averageRating": 3.5,<br>
     *                  "totalVotes": 25356000,<br>
     *                  "cast": "Mark Hamil, Daisy Ridley",<br>
     *                  "screen": "Imax"<br>
     *              }<br>
     *          ],<br>
     * "tvShows": [<br>
     *              {
     *                  "name": "Bigg Boss",<br>
     *                  "language": "Hindi",<br>
     *                  "genre": "Reality Show",<br>
     *                  "type": "TV_SHOW",<br>
     *                  "averageRating": 4,<br>
     *                  "totalVotes": 100000,<br>
     *                  "channel": "Colors",<br>
     *                  "host": "Salman Khan"<br>
     *              },<br>
     *              {<br>
     *                  "name": "KBC",<br>
     *                  "language": "Hindi",<br>
     *                  genre": "Reality Show",<br>
     *                  "type": "TV_SHOW",<br>
     *                  "averageRating": 5,<br>
     *                  "totalVotes": 1000,<br>
     *                  "channel": "Star Plus",
     *                  "host": "Amitabh Bachchan"<br>
     *              }<br>
     *          ]<br>
     * }<br>
     *
     * @return {@link Response} <br>
     * 200 if successful.<br>
     **/

    @GET
    @Path("/sort-the-shows")
    public Response getShowsInSortedManner(@QueryParam("sort-by-name") @DefaultValue("false") final boolean sortByName,
                                           @QueryParam("sort-by-average-rating") @DefaultValue("false") final boolean sortByAverageRating){

        AllShowsDto allShowsDto = null;
        if(sortByName){
            allShowsDto = ratingRequestHandler.sortTheShowsByName();
        } else if (sortByAverageRating){
            allShowsDto = ratingRequestHandler.sortTheShowsByAverageRating();
        }
        return Response.status(Response.Status.OK).entity(allShowsDto).build();
    }
}
