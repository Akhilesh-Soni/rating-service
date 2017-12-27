# RATING SERIVCE

How to start the api application
---

1. Run `mvn clean install` to build your application
1. Start application with `java -jar target/movie-rating-service-1.0.0-SNAPSHOT.jar server config.yml`
1. To check that your application is running enter url `http://localhost:8080`

How to create docker image and run it(Docker should be installed)
---

1. Inside rating-service folder open terminal.
2. Run `sudo docker build -t rating-service:1.0.0-SNAPSHOT .` to build a docker image.
3. Run `sudo docker run --name=rating-service  -p 8080:8080 -p 8081:8081 rating-service:1.0.0-SNAPSHOT` to run the docker image.


Health Check
---

To see your applications health enter url `http://localhost:8081/healthcheck`
