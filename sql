CREATE DATABASE jd2_homework3_4;
USE jd2_homework3_4;
CREATE TABLE films(
	id INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
	title VARCHAR(50) NULL,
	duration INT(11) NULL,
	price INT(11) NULL 
);
INSERT INTO films (title, duration, price) VALUES ('film_1', 60, 10);
INSERT INTO films (title, duration, price) VALUES ('film_2', 90, 11);
INSERT INTO films (title, duration, price) VALUES ('film_3', 120, 9);
INSERT INTO films (title, duration, price) VALUES ('film_4', 60, 17);
INSERT INTO films (title, duration, price) VALUES ('film_5', 90, 5);

SELECT MAX(price) FROM films;
DELETE FROM films WHERE price = (SELECT * FROM (SELECT MIN(price) FROM films) AS t1);
UPDATE films SET title = 'Hot film!' WHERE price = (SELECT * FROM (SELECT MAX(price) FROM films) AS t1);

DROP TABLE films;
DROP DATABASE jd2_homework3_4;