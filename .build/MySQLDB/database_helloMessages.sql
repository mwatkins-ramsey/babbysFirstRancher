CREATE DATABASE IF NOT EXISTS hello;
use hello;

CREATE TABLE if not exists messages(
    id bigint(11) not null AUTO_INCREMENT,
    message VARCHAR(256),
    PRIMARY KEY (id)
);

INSERT INTO messages(message) VALUES ("Hello World!"), ("Hello Tennessee!"), ("Hello Ramsey!"), ("Hello Tester!");