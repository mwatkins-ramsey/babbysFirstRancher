FROM mysql:latest

ENV MYSQL_ROOT_PASSWORD=root

COPY .build/MySQLDB/database_helloMessages.sql /docker-entrypoint-initdb.d/

EXPOSE 8081