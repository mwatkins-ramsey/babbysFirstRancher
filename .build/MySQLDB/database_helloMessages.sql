CREATE DATABASE hello;
use hello;

CREATE TABLE messages(
    id bigint(11) not null AUTO_INCREMENT,
    message VARCHAR(256),
    PRIMARY KEY (id)
);

INSERT INTO messages(message) VALUES ("Hello World!"), ("Hello Tennessee!"), ("Hello Ramsey!"), ("Hello Michael!");