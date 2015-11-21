USE c_cs108_cheeawai1;

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

CREATE TABLE quizzes (
	pid MEDIUMINT NOT NULL AUTO_INCREMENT,
	uid MEDIUMINT,
	name VARCHAR(1000),
	description VARCHAR(1000),
	cdate TIMESTAMP DEFAULT CURRENT_TIME,
	singlePage BOOLEAN, 
	randQuestion BOOLEAN,
	immediateCorrect BOOLEAN,
	practiceMode BOOLEAN,
	PRIMARY KEY (pid)
	)

CREATE TABLE questions (
	pid MEDIUMINT NOT NULL AUTO_INCREMENT,
	qid MEDIUMINT,
	qtype VARCHAR(20), //question types dont have long names
	question VARCHAR(10000), //establish 10000 characters as the limit for the length of a question to be 
	image VARCHAR(10000), //The image url?
	answer VARCHAR(10000), //I suppose a 10,000 char limit? 
	PRIMARY KEY (pid)
);

CREATE TABLE friends (
	pid MEDIUMINT NOT NULL AUTO_INCREMENT,
	friendA MEDIUMINT,
	friendB MEDIUMINT,
 	friendship VARCHAR(20), //is it ok to represent type like this?
	PRIMARY KEY (pid)
);

CREATE TABLE achievements (
	pid MEDIUMINT NOT NULL AUTO_INCREMENT, 
	uid MEDIUMINT, 
	achievement1 BOOLEAN, 
	achievement2 BOOLEAN, 
	achievement3 BOOLEAN, 
	achievement4 BOOLEAN, 
	achievement5 BOOLEAN, 
	achievement6 BOOLEAN, 
	achievement7 BOOLEAN, 
	achievement8 BOOLEAN, 
	achievement9 BOOLEAN, 
	achievement10 BOOLEAN, 
);

CREATE TABLE scores (
	pid MEDIUMINT NOT NULL AUTO_INCREMENT,
	quizid MEDIUMINT, 
	score MEDIUMINT, 
	utime 
)