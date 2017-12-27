package com.hashout.rating.api.util;

import com.hashout.rating.api.dtos.ShowRatingDto;

/**
 * This class will use to sort the show by their average rating in ascending order.
 **/
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
