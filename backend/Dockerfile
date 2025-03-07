FROM amazoncorretto:23-alpine-jdk

LABEL maintainer="Umbralink Dev Team"

COPY /target/umbralink-0.0.1-SNAPSHOT.jar umbralink.jar

ENTRYPOINT [ "java", "-jar", "umbralink.jar" ]
