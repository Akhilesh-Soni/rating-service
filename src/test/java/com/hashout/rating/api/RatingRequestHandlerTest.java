package com.hashout.rating.api;

import com.hashout.rating.api.dtos.*;
import com.hashout.rating.api.util.Data;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;

public class RatingRequestHandlerTest {
    private AllShowsDto allShowsDto;
    private Map<String, MovieRatingDto> movieRatingDtoMap;
    private Map<String, TvShowRatingDto> tvShowRatingDtoMap;
    private RatingRequestHandler objUnderTest;

    @Before
    public void setUp() throws Exception {

        ArrayList<MovieRatingDto> moviesRatingDtos = new ArrayList<>();
        ArrayList<TvShowRatingDto> tvShowRatingDtos = new ArrayList<>();

        movieRatingDtoMap = Data.intializeDataForMovies();
        tvShowRatingDtoMap = Data.intializeDataForTvShow();


        moviesRatingDtos.addAll(movieRatingDtoMap.values());
        tvShowRatingDtos.addAll(tvShowRatingDtoMap.values());

        allShowsDto = new AllShowsDto(moviesRatingDtos, tvShowRatingDtos);
        objUnderTest = new RatingRequestHandler();
    }

    @After
    public void tearDown() throws Exception {
        objUnderTest = null;
    }

    @Test
    public void getAllShowsRatingShallReturnValidResponse() throws Exception {
        AllShowsDto responseEntity = objUnderTest.getAllShowsRatings();

        MatcherAssert.assertThat(responseEntity.getMovies().size(), Is.is(allShowsDto.getMovies().size()));
        MatcherAssert.assertThat(responseEntity.getTvShows().size(), Is.is(allShowsDto.getTvShows().size()));
    }

    @Test
    public void getRatingOfShowShallReturnValidResponseForTvShow() throws Exception {
        TvShowRatingDto showRatingDto = tvShowRatingDtoMap.get("Bigg Boss");

        Response response = objUnderTest.findRatingsOfShow("Bigg Boss");
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

        Response response = objUnderTest.findRatingsOfShow("Tiger Zinda Hai");
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

    @Test(expected = NotFoundException.class)
    public void getRatingOfShowShallThrowNotFoundException() throws Exception {
        objUnderTest.findRatingsOfShow("Unknown Name");
    }

    @Test
    public void updateRatingShallUpdateTheRatingOfTypeTvShow() throws Exception {
        UpdateRatingDto updateRatingDto = new UpdateRatingDto("Bigg Boss", ShowType.TV_SHOW, "5");
        objUnderTest.handleUpdateRequest(updateRatingDto);
    }

    @Test
    public void updateRatingShallUpdateTheRatingOfMoview() throws Exception {
        UpdateRatingDto updateRatingDto = new UpdateRatingDto("Tiger Zinda Hai", ShowType.MOVIE, "5");
        objUnderTest.handleUpdateRequest(updateRatingDto);
    }

    @Test(expected = InvalidPropertiesFormatException.class)
    public void updateRatingShallReturnUnprocessableEntityReponse() throws Exception {
        UpdateRatingDto updateRatingDto = new UpdateRatingDto("Tiger Zinda Hai", ShowType.MOVIE, "13");
        objUnderTest.handleUpdateRequest(updateRatingDto);
    }

}