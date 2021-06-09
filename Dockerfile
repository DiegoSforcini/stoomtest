FROM openjdk

WORKDIR /app

COPY target/stoomtest-0.0.1-SNAPSHOT.jar /app/stoomtest.jar

ENTRYPOINT ["java", "-jar", "stoomtest.jar"]