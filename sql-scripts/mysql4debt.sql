DROP DATABASE  IF EXISTS `spring_security_custom_user_demo`;

CREATE DATABASE  IF NOT EXISTS `spring_security_custom_user_demo`;
USE `spring_security_custom_user_demo`;



DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(45) COLLATE latin1_bin NOT NULL,
  `first_name` varchar(45) COLLATE latin1_bin NOT NULL,
  `last_name` varchar(45) COLLATE latin1_bin NOT NULL,
  `username` varchar(45) COLLATE latin1_bin NOT NULL,
  `password` char(80) COLLATE latin1_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1 COLLATE=latin1_bin;

INSERT INTO `user` (email, first_name, last_name, username, password)
VALUES 
('john@noname.com', 'John','Doe', 'john','$2a$10$ZHgjK7UfAjH6xFYruiorsepV6JV61Jkqe9lPwh0XXZ876K63wt0Oe'),
('rinat@noname.com', 'Rinat','Kamaletdinov', 'rinat','$2a$10$ZHgjK7UfAjH6xFYruiorsepV6JV61Jkqe9lPwh0XXZ876K63wt0Oe'),
('noname@noname.com', 'No','Name', 'noname','$2a$10$ZHgjK7UfAjH6xFYruiorsepV6JV61Jkqe9lPwh0XXZ876K63wt0Oe'); 



DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) COLLATE latin1_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1 COLLATE=latin1_bin;

INSERT INTO `role` (name)
VALUES 
('ROLE_EMPLOYEE'), ('ROLE_MANAGER'), ('ROLE_ADMIN');



DROP TABLE IF EXISTS `users_roles`;

CREATE TABLE `users_roles` (
  `user_id` int NOT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FK_ROLE_idx` (`role_id`),
  CONSTRAINT `FK_ROLE` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FK_USR_R` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_bin;

SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO `users_roles` (user_id, role_id)
VALUES 
(1, 1),
(2, 1),
(2, 2),
(3, 1),
(3, 2),
(3, 3);



DROP TABLE IF EXISTS `debt`;

CREATE TABLE `debt` (
  `id_of_debt` int NOT NULL AUTO_INCREMENT,
  `creditor_first_name` varchar(45) COLLATE latin1_bin NOT NULL,
  `creditor_last_name` varchar(45) COLLATE latin1_bin NOT NULL,
  `creditor_username` varchar(45) COLLATE latin1_bin DEFAULT NULL,
  `debtor_first_name` varchar(45) COLLATE latin1_bin NOT NULL,
  `debtor_last_name` varchar(45) COLLATE latin1_bin NOT NULL,
  `debtor_username` varchar(45) COLLATE latin1_bin DEFAULT NULL,
  `info` varchar(45) COLLATE latin1_bin DEFAULT NULL,
  `value` varchar(45) COLLATE latin1_bin NOT NULL,
  PRIMARY KEY (`id_of_debt`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1 COLLATE=latin1_bin;

INSERT INTO `debt` (id_of_debt, creditor_first_name, creditor_last_name, creditor_username, 
debtor_first_name, debtor_last_name, debtor_username, info, value)
VALUES 
(1, 'Rinat', 'Kamaletdinov', 'rinat', 'No', 'Name', 'noname', 'до зарплаты', 123),
(2, 'John', 'Doe', 'john', 'Rinat', 'Kamaletdinov', 'rinat', 'на белеберду', 456),
(3, 'John', 'Doe', 'john', 'No', 'Name', 'noname', 'на важное', 789),
(4, 'No', 'Name', 'noname', 'Sasha', 'Gambler', null, 'карточный долг', 321),
(5, 'Vasya', 'Petrov', null, 'John', 'Doe', 'john', 'на еду', 654),
(6, 'Rinat', 'Kamaletdinov', 'rinat', 'Petya', 'Ivanov', null, 'до зарплаты', 987);