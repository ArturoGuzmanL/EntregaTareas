-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 25-11-2022 a las 08:51:52
-- Versión del servidor: 10.4.24-MariaDB
-- Versión de PHP: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;


CREATE DATABASE IF NOT EXISTS `gestion_empresa` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `gestion_empresa`;



DROP TABLE IF EXISTS `alumno`;
CREATE TABLE IF NOT EXISTS `alumno` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) NOT NULL,
  `apellidos` text NOT NULL,
  `contraseña` varchar(255) NOT NULL,
  `dni` varchar(9) NOT NULL,
  `fecha_nacimiento` date NOT NULL,
  `email` varchar(255) NOT NULL,
  `telefono` int(9) NOT NULL,
  `empresa_practicas` int(11) NOT NULL,
  `profesor_asignado` int(11) NOT NULL,
  `total_horas_dual` int(11) DEFAULT NULL,
  `total_horas_fct` int(11) NOT NULL,
  `observaciones` text DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `empresa_practicas` (`empresa_practicas`),
  KEY `profesor_asignado` (`profesor_asignado`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



TRUNCATE TABLE `alumno`;



DROP TABLE IF EXISTS `empresa`;
CREATE TABLE IF NOT EXISTS `empresa` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) NOT NULL,
  `telefono` int(9) NOT NULL,
  `email` varchar(255) NOT NULL,
  `responsable_empresa` varchar(255) NOT NULL,
  `observaciones` text DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



TRUNCATE TABLE `empresa`;



DROP TABLE IF EXISTS `practica`;
CREATE TABLE IF NOT EXISTS `practica` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `alumno_asociado` int(11) NOT NULL,
  `fecha` date NOT NULL,
  `tipo_practica` varchar(255) NOT NULL,
  `total_horas_dia` int(11) NOT NULL,
  `actividad` varchar(255) NOT NULL,
  `observaciones` text DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `alumno_asociado` (`alumno_asociado`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;




TRUNCATE TABLE `practica`;


DROP TABLE IF EXISTS `profesor`;
CREATE TABLE IF NOT EXISTS `profesor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) NOT NULL,
  `apellido` varchar(255) NOT NULL,
  `contraseña` varchar(255) NOT NULL,
  `correo` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;




TRUNCATE TABLE `profesor`;


CREATE TABLE `gestion_empresa`.`sudo` (
  `id` INT NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `contraseña` BLOB NOT NULL,
  PRIMARY KEY (`id`));

INSERT INTO gestion_empresa.sudo (id, email, contraseña)
VALUES (1, 'SUDO_ADMIN', aes_encrypt('admin1234', SHA2('CLAVE',512)));

ALTER TABLE `alumno`
  ADD CONSTRAINT `alumno_ibfk_1` FOREIGN KEY (`profesor_asignado`) REFERENCES `profesor` (`id`),
  ADD CONSTRAINT `alumno_ibfk_2` FOREIGN KEY (`empresa_practicas`) REFERENCES `empresa` (`id`);


ALTER TABLE `practica`
  ADD CONSTRAINT `practica_ibfk_1` FOREIGN KEY (`alumno_asociado`) REFERENCES `alumno` (`id`);


  ALTER TABLE `gestion_empresa`.`profesor` 
CHANGE COLUMN `contraseña` `contraseña` BLOB NULL ;

ALTER TABLE `gestion_empresa`.`alumno` 
CHANGE COLUMN `contraseña` `contraseña` BLOB NULL ;

COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
