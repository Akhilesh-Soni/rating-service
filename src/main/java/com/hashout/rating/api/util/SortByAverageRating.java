package com.hashout.rating.api.util;

import com.hashout.rating.api.dtos.ShowRatingDto;

public class SortByAverageRating implements java.util.Comparator<ShowRatingDto> {
    @Override
    public int compare(ShowRatingDto o1, ShowRatingDto o2) {

        if (o1.getAverageRating() >= o2.getAverageRating()) {
            return 1;
        } else {
            return -1;
        }
    }
}
