package com.hashout.rating.api.util;

import com.hashout.rating.api.dtos.MovieRatingDto;
import com.hashout.rating.api.dtos.ShowType;
import com.hashout.rating.api.dtos.TvShowRatingDto;

import java.util.HashMap;
import java.util.Map;

public class Data {


    public static Map<String, TvShowRatingDto> intializeDataForTvShow() {
        Map<String, TvShowRatingDto> map = new HashMap<>();
        map.put("KBC", new TvShowRatingDto("KBC", "Hindi", "Reality Show",
                ShowType.TV_SHOW, 5, 1000, "Star Plus", "Amitabh Bachchan"));
        map.put("Bigg Boss", new TvShowRatingDto("Bigg Boss", "Hindi", "Reality Show",
                ShowType.TV_SHOW, 4, 100000, "Colors", "Salman Khan"));
        return map;
    }

    public static Map<String, MovieRatingDto> intializeDataForMovies() {
        Map<String, MovieRatingDto> map = new HashMap<>();
        map.put("Tiger Zinda Hai", new MovieRatingDto("Tiger Zinda Hai", "Hindi",
                "Action, Drama",
                ShowType.MOVIE, 3.0f, 3130230, "Salman Khan, Katrin Kaif",
                null));
        map.put("Start Wars", new MovieRatingDto("Star Wars", "English", "Action, Adventure",
                ShowType.MOVIE, 3.5f, 25356000, "Mark Hamil, Daisy Ridley",
                "Imax"));
        return map;
    }
}
