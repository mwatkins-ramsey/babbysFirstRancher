FROM openjdk:20

WORKDIR /app

COPY . .

RUN output=`./mvnw clean install -DskipTests 2>&1` || echo $output

CMD ["java", "-jar", "target/babbysFirstRancher-0.0.1-SNAPSHOT.jar"]

EXPOSE 8080