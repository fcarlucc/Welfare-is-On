FROM eclipse-temurin:22-jdk

COPY app-0.0.1-SNAPSHOT.jar /app-0.0.1-SNAPSHOT.jar

RUN mkdir uploads

COPY uploads /uploads

CMD ["java", "-jar", "app-0.0.1-SNAPSHOT.jar"]
