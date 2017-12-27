FROM openjdk:8

ADD target/rating-service-1.0.0-SNAPSHOT.jar /var/lib/service.jar
ADD config.yml /etc/config.yml

EXPOSE 8080 8081 2003

WORKDIR /etc/

#some comment

ENTRYPOINT ["java", "-jar", "/var/lib/service.jar"]
CMD ["server", "config.yml"]

