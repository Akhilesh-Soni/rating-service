package com.hashout.rating.api.util;

import com.hashout.rating.api.dtos.ShowRatingDto;

import java.util.Comparator;

public class SortByName implements Comparator<ShowRatingDto> {
    @Override
    public int compare(ShowRatingDto o1, ShowRatingDto o2) {
        return o1.getName().compareToIgnoreCase(o2.getName());
    }
}
