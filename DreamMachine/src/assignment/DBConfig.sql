USE c_cs108_cheeawai1;

DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS photos;
DROP TABLE IF EXISTS quizzes;

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
	height INT,
	width INT,
	data MEDIUMBLOB NOT NULL,
	PRIMARY KEY (photo_id)
);

CREATE TABLE quizzes (
	quiz_id INT NOT NULL AUTO_INCREMENT,
	user_id INT,
	name VARCHAR(150),
	description TEXT,
	single_page BOOLEAN, 
	random_questions BOOLEAN,
	immediate_correct BOOLEAN,
	practice_mode BOOLEAN,
	created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (quiz_id)
);

CREATE TABLE achievements (
	ach_id INT NOT NULL AUTO_INCREMENT, 
	user_id INT,
	a1 BOOLEAN, 
	a2 BOOLEAN, 
	a3 BOOLEAN, 
	a4 BOOLEAN, 
	a5 BOOLEAN, 
	a6 BOOLEAN, 
	a7 BOOLEAN, 
	a8 BOOLEAN, 
	a9 BOOLEAN, 
	a10 BOOLEAN, 
	PRIMARY KEY (ach_id)
);