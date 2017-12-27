package com.hashout.rating;

import com.hashout.rating.api.RatingRequestHandler;
import com.hashout.rating.api.RatingResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * It is the main class of service, where all bootstrapping of service take place.
 **/
public class RatingService extends Application<RatingConfiguration> {

    public static void main(final String[] args) throws Exception {
        new RatingService().run(args);
    }


    @Override
    public void initialize(final Bootstrap<RatingConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final RatingConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application
        environment.jersey().register(new RatingResource(new RatingRequestHandler()));
    }

}
