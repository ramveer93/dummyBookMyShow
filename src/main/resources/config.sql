CREATE DATABASE bookmyshow;
CREATE USER 'bookmyshow'@'localhost' IDENTIFIED BY 'bookmyshow';
GRANT ALL PRIVILEGES ON bookmyshow.* TO 'bookmyshow'@'localhost';
commit;

ALTER DATABASE bookmyshow CHARACTER SET utf8 COLLATE utf8_general_ci;
