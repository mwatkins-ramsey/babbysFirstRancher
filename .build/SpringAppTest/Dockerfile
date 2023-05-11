FROM openjdk:20

WORKDIR /app

COPY . .

#RUN ./mvnw clean install -DskipTests 2>&1

RUN ls
CMD ["./mvnw", "test"]

EXPOSE 8081