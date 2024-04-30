FROM azul/zulu-openjdk:17.0.11-jre
LABEL authors="zsdavid"
VOLUME /tmp
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]