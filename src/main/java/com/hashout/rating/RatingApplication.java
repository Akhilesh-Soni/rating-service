package com.hashout.rating;

import com.hashout.rating.api.Rating;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class RatingApplication extends Application<RatingConfiguration> {

    public static void main(final String[] args) throws Exception {
        new RatingApplication().run(args);
    }


    @Override
    public void initialize(final Bootstrap<RatingConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final RatingConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application
        environment.jersey().register(new Rating());
    }

}
