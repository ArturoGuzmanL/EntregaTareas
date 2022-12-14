-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 28-10-2022 a las 23:18:05
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
-- Base de datos: `comanda_desayunos`
--
CREATE DATABASE IF NOT EXISTS `comanda_desayunos` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `comanda_desayunos`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `carta`
--

DROP TABLE IF EXISTS `carta`;
CREATE TABLE IF NOT EXISTS `carta` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) NOT NULL,
  `tipo` varchar(255) NOT NULL,
  `precio` float NOT NULL,
  `disponibilidad` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `carta`
--

INSERT INTO `carta` (`id`, `nombre`, `tipo`, `precio`, `disponibilidad`) VALUES
(1, 'PITUFO MIXTO', 'PANADERIA', 1.5, 1),
(2, 'COCACOLA', 'BEBIDA', 2, 1),
(3, 'PITUFO BACON MAYO', 'PANADERIA', 2.5, 1),
(4, 'CACAHUETES', 'OTRO', 1, 1),
(5, 'FANTA NARANJA', 'BEBIDA', 2, 1),
(6, 'PEPSI', 'BEBIDA', 2, 1),
(7, 'ALTRAMUCES', 'OTRO', 0.5, 1),
(8, 'PATATAS', 'OTRO', 1.75, 1),
(9, 'GOLOSINAS', 'OTRO', 0.5, 1),
(10, 'PISTACHOS', 'OTRO', 2.5, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pedido`
--

DROP TABLE IF EXISTS `pedido`;
CREATE TABLE IF NOT EXISTS `pedido` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `identificador` int(11) NOT NULL,
  `fecha` date NOT NULL,
  `cliente` text NOT NULL,
  `estado` varchar(255) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `producto` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `producto` (`producto`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `pedido`
--

INSERT INTO `pedido` (`id`, `identificador`, `fecha`, `cliente`, `estado`, `cantidad`, `producto`) VALUES
(4, 2, '2022-10-28', 'BENITO', 'RECOGIDO', 3, 3),
(5, 2, '2022-10-28', 'BENITO', 'RECOGIDO', 3, 4),
(6, 2, '2022-10-28', 'BENITO', 'RECOGIDO', 1, 2),
(7, 3, '2022-10-28', 'JUANA', 'RECOGIDO', 2, 1),
(8, 4, '2022-10-28', 'MACABRO', 'RECOGIDO', 2, 5),
(9, 4, '2022-10-28', 'MACABRO', 'RECOGIDO', 2, 2),
(10, 5, '2022-10-28', 'JAJA', 'PENDIENTE', 5, 6),
(11, 5, '2022-10-28', 'JAJA', 'PENDIENTE', 2, 7),
(12, 5, '2022-10-28', 'JAJA', 'PENDIENTE', 5, 2),
(13, 6, '2022-10-28', 'PEPE', 'PENDIENTE', 1, 1),
(14, 6, '2022-10-28', 'PEPE', 'PENDIENTE', 1, 3),
(15, 7, '2022-10-28', 'JAJA', 'PENDIENTE', 3, 5),
(16, 7, '2022-10-28', 'JAJA', 'PENDIENTE', 2, 3),
(17, 8, '2022-10-28', 'MACABRO', 'PENDIENTE', 5, 5),
(18, 8, '2022-10-28', 'MACABRO', 'PENDIENTE', 7, 1),
(19, 9, '2022-10-28', 'BENITA', 'PENDIENTE', 3, 7);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `pedido`
--
ALTER TABLE `pedido`
  ADD CONSTRAINT `pedido_ibfk_1` FOREIGN KEY (`producto`) REFERENCES `carta` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
