FROM openjdk:17
WORKDIR /app
COPY ./target/interviewgft-0.0.1-SNAPSHOT.jar /app
EXPOSE 8080
CMD ["java", "-jar", "interviewgft-0.0.1-SNAPSHOT.jar"]