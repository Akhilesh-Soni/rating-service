package com.hashout.rating.api;

import com.hashout.rating.api.dtos.*;
import com.hashout.rating.api.util.Data;
import org.eclipse.jetty.http.HttpStatus;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;


public class RatingTest {

    private Rating objUnderTest;
    private AllShowsDto allShowsDto;
    private Map<String, MovieRatingDto> movieRatingDtoMap;
    private Map<String, TvShowRatingDto> tvShowRatingDtoMap;
    private RatingRequestHandler handler = Mockito.mock(RatingRequestHandler.class);

    @Before
    public void setUp() throws Exception {


        ArrayList<MovieRatingDto> moviesRatingDtos = new ArrayList<>();
        ArrayList<TvShowRatingDto> tvShowRatingDtos = new ArrayList<>();

        objUnderTest = new Rating(handler);
        movieRatingDtoMap = Data.intializeDataForMovies();
        tvShowRatingDtoMap = Data.intializeDataForTvShow();


        moviesRatingDtos.addAll(movieRatingDtoMap.values());
        tvShowRatingDtos.addAll(tvShowRatingDtoMap.values());

        allShowsDto = new AllShowsDto(moviesRatingDtos, tvShowRatingDtos);
    }

    @After
    public void tearDown() throws Exception {
        objUnderTest = null;
    }

    @Test
    public void getAllShowsRatingShallReturnValidResponse() throws Exception {
        Mockito.when(handler.getAllShowsRatings()).thenReturn(allShowsDto);
        Response response = objUnderTest.getAllShowsRating();
        AllShowsDto responseEntity = (AllShowsDto) response.getEntity();

        MatcherAssert.assertThat(response.getStatus(), Is.is(Response.Status.OK.getStatusCode()));
        MatcherAssert.assertThat(responseEntity.getMovies().size(), Is.is(allShowsDto.getMovies().size()));
        MatcherAssert.assertThat(responseEntity.getTvShows().size(), Is.is(allShowsDto.getTvShows().size()));
    }

    @Test
    public void getRatingOfShowShallReturnValidResponseForTvShow() throws Exception {
        TvShowRatingDto showRatingDto = tvShowRatingDtoMap.get("Bigg Boss");
        Response expectedResponse = Response.status(Response.Status.OK).entity(showRatingDto).build();
        Mockito.when(handler.findRatingsOfShow("Bigg Boss")).thenReturn(expectedResponse);


        Response response = objUnderTest.getRatingOfShow("Bigg Boss");
        TvShowRatingDto responseEntity = (TvShowRatingDto) response.getEntity();

        MatcherAssert.assertThat(response.getStatus(), Is.is(Response.Status.OK.getStatusCode()));
        MatcherAssert.assertThat(responseEntity.getName(), Is.is(showRatingDto.getName()));
        MatcherAssert.assertThat(responseEntity.getLanguage(), Is.is(showRatingDto.getLanguage()));
        MatcherAssert.assertThat(responseEntity.getAverageRating(), Is.is(showRatingDto.getAverageRating()));
        MatcherAssert.assertThat(responseEntity.getGenre(), Is.is(showRatingDto.getGenre()));
        MatcherAssert.assertThat(responseEntity.getChannel(), Is.is(showRatingDto.getChannel()));
        MatcherAssert.assertThat(responseEntity.getHost(), Is.is(showRatingDto.getHost()));
        MatcherAssert.assertThat(responseEntity.getTotalVotes(), Is.is(showRatingDto.getTotalVotes()));
        MatcherAssert.assertThat(responseEntity.getType(), Is.is(showRatingDto.getType()));

    }

    @Test
    public void getRatingOfShowShallReturnValidResponseForMovies() throws Exception {
        MovieRatingDto showRatingDto = movieRatingDtoMap.get("Tiger Zinda Hai");

        Response expectedResponse = Response.status(Response.Status.OK).entity(showRatingDto).build();
        Mockito.when(handler.findRatingsOfShow("Tiger Zinda Hai")).thenReturn(expectedResponse);


        Response response = objUnderTest.getRatingOfShow("Tiger Zinda Hai");
        MovieRatingDto responseEntity = (MovieRatingDto) response.getEntity();

        MatcherAssert.assertThat(response.getStatus(), Is.is(Response.Status.OK.getStatusCode()));
        MatcherAssert.assertThat(responseEntity.getName(), Is.is(showRatingDto.getName()));
        MatcherAssert.assertThat(responseEntity.getLanguage(), Is.is(showRatingDto.getLanguage()));
        MatcherAssert.assertThat(responseEntity.getAverageRating(), Is.is(showRatingDto.getAverageRating()));
        MatcherAssert.assertThat(responseEntity.getGenre(), Is.is(showRatingDto.getGenre()));
        MatcherAssert.assertThat(responseEntity.getCast(), Is.is(showRatingDto.getCast()));
        MatcherAssert.assertThat(responseEntity.getScreen(), Is.is(showRatingDto.getScreen()));
        MatcherAssert.assertThat(responseEntity.getTotalVotes(), Is.is(showRatingDto.getTotalVotes()));
        MatcherAssert.assertThat(responseEntity.getType(), Is.is(showRatingDto.getType()));

    }

    @Test
    public void getRatingOfShowShallReturnNotFoundResponseIfShowDoesnNotExist() throws Exception {
        Mockito.doThrow(NotFoundException.class).when(handler).findRatingsOfShow(Mockito.anyString());
        Response response = objUnderTest.getRatingOfShow("Unknown Name");
        MatcherAssert.assertThat(response.getStatus(), Is.is(Response.Status.NOT_FOUND.getStatusCode()));
    }

    @Test
    public void updateRatingShallUpdateTheRatingOfTypeTvShow() throws Exception {
        UpdateRatingDto updateRatingDto = new UpdateRatingDto("Bigg Boss", ShowType.TV_SHOW, "5");
        Mockito.doNothing().when(handler).handleUpdateRequest(updateRatingDto);
        Response response = objUnderTest.updateRating(updateRatingDto);
        MatcherAssert.assertThat(response.getStatus(), Is.is(Response.Status.NO_CONTENT.getStatusCode()));
    }

    @Test
    public void updateRatingShallUpdateTheRatingOfMoview() throws Exception {
        UpdateRatingDto updateRatingDto = new UpdateRatingDto("Tiger Zinda Hai", ShowType.MOVIE, "5");
        Mockito.doNothing().when(handler).handleUpdateRequest(updateRatingDto);
        Response response = objUnderTest.updateRating(updateRatingDto);
        MatcherAssert.assertThat(response.getStatus(), Is.is(Response.Status.NO_CONTENT.getStatusCode()));
    }

    @Test
    public void updateRatingShallReturnUnprocessableEntityReponse() throws Exception {
        UpdateRatingDto updateRatingDto = new UpdateRatingDto("Tiger Zinda Hai", ShowType.MOVIE, "13");
        Mockito.doThrow(InvalidPropertiesFormatException.class).when(handler).handleUpdateRequest(updateRatingDto);
        Response response = objUnderTest.updateRating(updateRatingDto);
        MatcherAssert.assertThat(response.getStatus(), Is.is(HttpStatus.UNPROCESSABLE_ENTITY_422));
    }
}