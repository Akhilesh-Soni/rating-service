package com.hashout.rating.api.util;

import com.hashout.rating.api.dtos.ShowRatingDto;

import java.util.Comparator;

/**
 * This class will use to sort the show by their name in lexicographic order.
 **/
public class SortByName implements Comparator<ShowRatingDto> {
    @Override
    public int compare(ShowRatingDto o1, ShowRatingDto o2) {
        return o1.getName().compareToIgnoreCase(o2.getName());
    }
}
