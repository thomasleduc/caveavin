CREATE DATABASE  IF NOT EXISTS `caveavin`;
USE `caveavin`;

DROP TABLE IF EXISTS `bottle`;
CREATE TABLE `bottle` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `creation_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `drunk_date` timestamp NULL DEFAULT NULL,
  `stored` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_stored_in_idx` (`stored`),
  CONSTRAINT `fk_stored_in` FOREIGN KEY (`stored`) REFERENCES `cellar` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `causer`;
CREATE TABLE `causer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `creation_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `cellar`;
CREATE TABLE `cellar` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `owner` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_idx` (`owner`),
  CONSTRAINT `fk_user_cellar` FOREIGN KEY (`owner`) REFERENCES `causer` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

