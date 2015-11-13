USE c_cs108_jevans2;

DROP TABLE IF EXISTS users;

CREATE TABLE users (
    pid MEDIUMINT NOT NULL AUTO_INCREMENT,
    username CHAR(64),
    salt CHAR(32),
    digest CHAR(32),
    PRIMARY KEY (pid)
);
