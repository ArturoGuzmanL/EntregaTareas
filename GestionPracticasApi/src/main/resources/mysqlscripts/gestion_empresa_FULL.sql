-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 28-11-2022 a las 09:48:43
-- Versión del servidor: 10.4.24-MariaDB
-- Versión de PHP: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `gestion_empresa`
--
CREATE DATABASE IF NOT EXISTS `gestion_empresa` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `gestion_empresa`;

DELIMITER $$
--
-- Procedimientos
--
DROP PROCEDURE IF EXISTS `crear_pass`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `crear_pass` (`_id` INT, `_rol` SMALLINT, `_password` VARCHAR(45))   BEGIN
	DECLARE _passEncriptada BLOB DEFAULT '';
	SET _passEncriptada = aes_encrypt(_password, SHA2('CLAVE',512));
	
    IF _rol = 3 THEN
		UPDATE gestion_empresa.alumno SET gestion_empresa.alumno.contraseña = _passEncriptada
        WHERE gestion_empresa.alumno.id = _id;
	ELSE
		IF _rol = 2 THEN
			UPDATE gestion_empresa.profesor SET gestion_empresa.profesor.contraseña = _passEncriptada
			WHERE gestion_empresa.profesor.id = _id;
        END IF;
    END IF;
END$$

--
-- Funciones
--
DROP FUNCTION IF EXISTS `login`$$
CREATE FUNCTION `login` (
    `_email` VARCHAR(255),
    `_password` VARCHAR(255),
    `_rol` SMALLINT
) RETURNS tinyint(1)
BEGIN
	DECLARE _passEncriptada BLOB DEFAULT '';
	DECLARE _resultado tinyint(1) DEFAULT 0;

    SET _passEncriptada = aes_encrypt(_password, SHA2('CLAVE',512));

	IF _rol = 3 THEN
		SET _resultado = IF((SELECT gestion_empresa.alumno.contraseña FROM gestion_empresa.alumno
        WHERE gestion_empresa.alumno.email COLLATE utf8mb4_general_ci = _email) = _passEncriptada, 1, 0);
    ELSE
		IF _rol = 2 THEN
			SET _resultado = IF((SELECT gestion_empresa.profesor.contraseña FROM gestion_empresa.profesor
			WHERE gestion_empresa.profesor.correo COLLATE utf8mb4_general_ci = _email) = _passEncriptada, 1, 0);
        ELSE
			IF _rol = 1 THEN
				SET _resultado = IF((SELECT gestion_empresa.sudo.contraseña FROM gestion_empresa.sudo
				WHERE gestion_empresa.sudo.email COLLATE utf8mb4_general_ci = _email) = _passEncriptada, 1, 0);
            END IF;
        END IF;
    END IF;
RETURN _resultado;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `alumno`
--

DROP TABLE IF EXISTS `alumno`;
CREATE TABLE IF NOT EXISTS `alumno` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) NOT NULL,
  `apellidos` text NOT NULL,
  `contraseña` blob DEFAULT NULL,
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

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empresa`
--

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

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `practica`
--

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

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `profesor`
--

DROP TABLE IF EXISTS `profesor`;
CREATE TABLE IF NOT EXISTS `profesor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) NOT NULL,
  `apellido` varchar(255) NOT NULL,
  `contraseña` blob DEFAULT NULL,
  `correo` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `sudo`
--

DROP TABLE IF EXISTS `sudo`;
CREATE TABLE IF NOT EXISTS `sudo` (
  `id` int(11) NOT NULL,
  `email` varchar(45) NOT NULL,
  `contraseña` blob NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `sudo`
--

INSERT INTO `sudo` (`id`, `email`, `contraseña`) VALUES
(1, 'SUDO_ADMIN', 0x4fe38fe8d30db4e108e24a9e9963f603);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `alumno`
--
ALTER TABLE `alumno`
  ADD CONSTRAINT `alumno_ibfk_1` FOREIGN KEY (`profesor_asignado`) REFERENCES `profesor` (`id`),
  ADD CONSTRAINT `alumno_ibfk_2` FOREIGN KEY (`empresa_practicas`) REFERENCES `empresa` (`id`);

--
-- Filtros para la tabla `practica`
--
ALTER TABLE `practica`
  ADD CONSTRAINT `practica_ibfk_1` FOREIGN KEY (`alumno_asociado`) REFERENCES `alumno` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
