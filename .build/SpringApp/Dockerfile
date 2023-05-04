FROM openjdk:20

WORKDIR /app

COPY . .

RUN echo Hello $MYSQL_DB

RUN output=`./mvnw clean install 2>&1` || echo $output

CMD ["java", "-jar", "target/babbysFirstRancher-0.0.1-SNAPSHOT.jar"]

EXPOSE 8080