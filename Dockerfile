FROM java:latest
RUN mkdir /SimpleNotes
WORKDIR /SimpleNotes
ADD build/libs/SimpleNotes-0.0.1-SNAPSHOT.jar /SimpleNotes
ENTRYPOINT ["java", "-jar", "SimpleNotes-0.0.1-SNAPSHOT.jar"]
