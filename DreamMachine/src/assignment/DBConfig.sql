USE c_cs108_jevans2;

DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS photos;

CREATE TABLE users (
    user_id INT NOT NULL AUTO_INCREMENT,
    username CHAR(64),
    salt CHAR(32),
    digest CHAR(64),
    photo_id INT,
    PRIMARY KEY (user_id)
);

CREATE TABLE photos (
	photo_id INT NOT NULL AUTO_INCREMENT,
	ftype CHAR(3),
	data MEDIUMBLOB NOT NULL,
	PRIMARY KEY (photo_id)
);