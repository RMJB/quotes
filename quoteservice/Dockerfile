FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp
COPY target/quoteservice-0.0.1-SNAPSHOT.jar quoteservice-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/quoteservice-0.0.1-SNAPSHOT.jar"]