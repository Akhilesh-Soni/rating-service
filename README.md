# RATING SERVICE

An​ application which allows ​users​ to rate movies, Tv​ shows etc. Also​ allows​ to​ see overall rating​ from​ other users.
It exposes a REST interface which accepts HTTP requests from web client like Postman or Advance Rest Client.

How to start the rating service
---

1. Run `mvn clean install` to build your application
2. Start application with `java -jar target/movie-rating-service-1.0.0-SNAPSHOT.jar server config.yml`
3. To check that your application is running enter url `http://localhost:8080`

How to run the ui(Start rating service first)
---
1. Inside rating-service folder open terminal.
2. Run `cd ui` to switch to ui folder.
3. Run `npm install` to install required packages.
4. Run `bower install` to install dependencies.
5. Run `npm run start` to run ui.


How to create docker image and run it(Docker should be installed)
---

1. Inside rating-service folder open terminal.
2. Run `sudo docker build -t rating-service:1.0.0-SNAPSHOT .` to build a docker image.
3. Run `sudo docker run --name=rating-service  -p 8080:8080 -p 8081:8081 rating-service:1.0.0-SNAPSHOT` to run the docker image.


Health Check
---

To see your applications health enter url `http://localhost:8081/healthcheck`
