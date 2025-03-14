FROM amazoncorretto:23-alpine-jdk

LABEL maintainer="Umbralink Dev Team"

COPY /target/umbralink-0.0.1-SNAPSHOT.jar umbralink.jar

ENV CLOUDINARY_API_KEY=
ENV CLOUDINARY_CLOUD_NAME=
ENV CLOUDINARY_SECRET_KEY=
ENV DB_PASSWORD=
ENV SECRET_KEY=
ENV DB_USERNAME=
ENV DB_URL=

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "umbralink.jar" ]
