FROM gradle:6.8.3-jdk8
RUN mkdir notes-app
ADD . notes-app/
WORKDIR notes-app/
RUN gradle build

FROM java:latest
COPY --from=0 /notes-app/build/libs/SimpleNotes-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java", "-jar", "SimpleNotes-0.0.1-SNAPSHOT.jar"]
