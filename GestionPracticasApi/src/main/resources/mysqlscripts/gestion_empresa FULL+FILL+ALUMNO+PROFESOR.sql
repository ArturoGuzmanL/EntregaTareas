-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 30-11-2022 a las 20:47:11
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
CREATE DEFINER=`root`@`localhost` FUNCTION `login` (`_email` VARCHAR(255), `_password` VARCHAR(255), `_rol` SMALLINT) RETURNS TINYINT(1)  BEGIN
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
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `alumno`
--

INSERT INTO `alumno` (`id`, `nombre`, `apellidos`, `contraseña`, `dni`, `fecha_nacimiento`, `email`, `telefono`, `empresa_practicas`, `profesor_asignado`, `total_horas_dual`, `total_horas_fct`, `observaciones`) VALUES
(1, 'Antonio', 'Rodriguez', 0xd80ca175bd8cc262fa4dbfa5daef8c48, '56473495E', '1966-04-25', 'Antonioi@mail.com', 564734950, 2, 1, 120, 120, 'Aqui va una observacion ...'),
(2, 'Pepe', 'Alvarez', NULL, '66578394V', '1966-09-03', 'Pepei@mail.com', 665783940, 4, 1, 160, 160, 'Aqui va una observacion ...'),
(3, 'Francisco', 'Balbuena', NULL, '32234896M', '1976-01-11', 'Franciscoi@mail.com', 322348960, 10, 1, 50, 50, 'Aqui va una observacion ...'),
(4, 'Reyes', 'Caja', NULL, '99878566C', '1981-06-17', 'Reyesi@mail.com', 998785660, 5, 1, 130, 130, 'Aqui va una observacion ...'),
(5, 'Alvaro', 'Aguilar', NULL, '43589459J', '1984-11-13', 'Alvaroi@mail.com', 435894590, 9, 1, 500, 500, 'Aqui va una observacion ...'),
(6, 'Juan', 'Rutatan', NULL, '66576892H', '1996-08-01', 'Juani@mail.com', 665768920, 5, 1, 90, 90, 'Aqui va una observacion ...'),
(7, 'Roberto', 'Escohotado', NULL, '22355475H', '2009-06-22', 'Robertoi@mail.com', 223554750, 6, 1, 440, 440, 'Aqui va una observacion ...'),
(8, 'Juan', 'Rutatan', NULL, '66576892H', '1986-11-14', 'Juani1@mail.com', 665768920, 8, 1, 450, 450, 'Aqui va una observacion ...'),
(9, 'Pepe', 'Alvarez', NULL, '66578394V', '2006-06-29', 'Pepei1@mail.com', 665783940, 2, 1, 20, 20, 'Aqui va una observacion ...'),
(10, 'Alberto', 'Santos', NULL, '77594885{', '1976-07-22', 'Albertoi@mail.com', 775948850, 2, 1, 270, 270, 'Aqui va una observacion ...'),
(11, 'Alvaro', 'Aguilar', NULL, '43589459J', '2004-06-23', 'Alvaroi1@mail.com', 435894590, 8, 2, 500, 500, 'Aqui va una observacion ...'),
(12, 'Adrian', 'Joseju', NULL, '65774894Z', '2005-08-04', 'Adriani@mail.com', 657748940, 8, 2, 220, 220, 'Aqui va una observacion ...'),
(13, 'Maite', 'Nopor', NULL, '56117523S', '1961-11-05', 'Maitei@mail.com', 561175230, 4, 2, 470, 470, 'Aqui va una observacion ...'),
(14, 'Rafa', 'Beethoven', NULL, '64779670O', '1988-03-02', 'Rafai89@mail.com', 647796700, 4, 2, 380, 380, 'Aqui va una observacion ...'),
(15, 'Aaron', 'Jimenez', NULL, '47586726P', '1974-12-09', 'Aaroni@mail.com', 475867260, 2, 2, 400, 400, 'Aqui va una observacion ...'),
(16, 'Alejandro', 'Vinance', NULL, '45532177T', '2006-07-19', 'Alejandroi@mail.com', 455321770, 6, 2, 420, 420, 'Aqui va una observacion ...'),
(17, 'Reyes', 'Caja', NULL, '99878566C', '1983-04-20', 'Reyesi1@mail.com', 998785660, 2, 2, 490, 490, 'Aqui va una observacion ...'),
(18, 'Antonio', 'Rodriguez', NULL, '56473495E', '1983-05-11', 'Antonioi12@mail.com', 564734950, 3, 2, 200, 200, 'Aqui va una observacion ...'),
(19, 'Roberto', 'Escohotado', NULL, '22355475H', '1978-01-23', 'Robertoi1@mail.com', 223554750, 2, 2, 350, 350, 'Aqui va una observacion ...'),
(20, 'Maite', 'Nopor', NULL, '56117523S', '1969-01-15', 'Maitei2@mail.com', 561175230, 5, 2, 480, 480, 'Aqui va una observacion ...'),
(21, 'Antonio', 'Rodriguez', NULL, '56473495E', '1981-08-12', 'Antonioi334@mail.com', 564734950, 7, 3, 380, 380, 'Aqui va una observacion ...'),
(22, 'Alberto', 'Ramon', NULL, '78541255L', '1969-09-14', 'Albertoi1@mail.com', 785412550, 2, 3, 150, 150, 'Aqui va una observacion ...'),
(23, 'Adrian', 'Joseju', NULL, '65774894Z', '1991-03-01', 'Adriani1@mail.com', 657748940, 3, 3, 420, 420, 'Aqui va una observacion ...'),
(24, 'Alvaro', 'Aguilar', NULL, '43589459J', '1975-08-21', 'Alvaroi2@mail.com', 435894590, 1, 3, 260, 260, 'Aqui va una observacion ...'),
(25, 'Juan', 'Rutatan', NULL, '66576892H', '1985-09-02', 'Juani3@mail.com', 665768920, 9, 3, 50, 50, 'Aqui va una observacion ...'),
(26, 'Maite', 'Nopor', NULL, '56117523S', '1960-04-02', 'Maitei3@mail.com', 561175230, 5, 3, 400, 400, 'Aqui va una observacion ...'),
(27, 'Adolfo', 'Juanillo', NULL, '78562590X', '1962-10-06', 'Adolfoi@mail.com', 785625900, 4, 3, 60, 60, 'Aqui va una observacion ...'),
(28, 'Aaron', 'Jimenez', NULL, '47586726P', '1972-10-07', 'Aaroni1@mail.com', 475867260, 4, 3, 90, 90, 'Aqui va una observacion ...'),
(29, 'Francisco', 'Balbuena', NULL, '32234896M', '1995-06-22', 'Franciscoi1@mail.com', 322348960, 5, 3, 120, 120, 'Aqui va una observacion ...'),
(30, 'Alvaro', 'Aguilar', NULL, '43589459J', '1992-04-04', 'Alvaroi3@mail.com', 435894590, 3, 3, 440, 440, 'Aqui va una observacion ...'),
(31, 'Benito', 'Arguelles', NULL, '36485964G', '1963-07-28', 'Benitoi@mail.com', 364859640, 3, 4, 60, 60, 'Aqui va una observacion ...'),
(32, 'Adrian', 'Joseju', NULL, '65774894Z', '2003-01-05', 'Adriani3@mail.com', 657748940, 5, 4, 280, 280, 'Aqui va una observacion ...'),
(33, 'Reyes', 'Caja', NULL, '99878566C', '1975-10-24', 'Reyesi2@mail.com', 998785660, 4, 4, 310, 310, 'Aqui va una observacion ...'),
(34, 'Francisco', 'Balbuena', NULL, '32234896M', '1978-06-08', 'Franciscoi2@mail.com', 322348960, 5, 4, 140, 140, 'Aqui va una observacion ...'),
(35, 'Benito', 'Arguelles', NULL, '36485964G', '1968-07-09', 'Benitoi1@mail.com', 364859640, 9, 4, 210, 210, 'Aqui va una observacion ...'),
(36, 'Aaron', 'Jimenez', NULL, '47586726P', '1997-10-28', 'Aaroni2@mail.com', 475867260, 5, 4, 240, 240, 'Aqui va una observacion ...'),
(37, 'Alberto', 'Ramon', NULL, '78541255L', '2006-02-26', 'Albertoi2@mail.com', 785412550, 5, 4, 120, 120, 'Aqui va una observacion ...'),
(38, 'Alvaro', 'Aguilar', NULL, '43589459J', '1978-11-14', 'Alvaroi11@mail.com', 435894590, 7, 4, 200, 200, 'Aqui va una observacion ...'),
(39, 'Alberto', 'Santos', NULL, '77594885{', '1992-10-21', 'Albertoi3@mail.com', 775948850, 6, 4, 410, 410, 'Aqui va una observacion ...'),
(40, 'Adrian', 'Joseju', NULL, '65774894Z', '1993-10-09', 'Adriani4@mail.com', 657748940, 8, 4, 100, 100, 'Aqui va una observacion ...'),
(41, 'Jorge', 'Privado', NULL, '12345332M', '1998-06-01', 'Jorgei@mail.com', 123453320, 6, 5, 320, 320, 'Aqui va una observacion ...'),
(42, 'Roberto', 'Escohotado', NULL, '22355475H', '1993-10-22', 'Robertoi2@mail.com', 223554750, 4, 5, 170, 170, 'Aqui va una observacion ...'),
(43, 'Antonio', 'Rodriguez', NULL, '56473495E', '1977-10-06', 'Antonioi553@mail.com', 564734950, 9, 5, 70, 70, 'Aqui va una observacion ...'),
(44, 'Juan', 'Rutatan', NULL, '66576892H', '1969-05-16', 'Juani4@mail.com', 665768920, 8, 5, 190, 190, 'Aqui va una observacion ...'),
(45, 'Alvaro', 'Aguilar', NULL, '43589459J', '1960-05-15', 'Alvaroi34@mail.com', 435894590, 7, 5, 150, 150, 'Aqui va una observacion ...'),
(46, 'Jose', 'Lanzas', NULL, '32134560{', '1974-09-13', 'Josei1@mail.com', 321345600, 7, 5, 110, 110, 'Aqui va una observacion ...'),
(47, 'Miguel', 'Bernard', NULL, '65788909Q', '1966-07-19', 'Migueli@mail.com', 657889090, 10, 5, 270, 270, 'Aqui va una observacion ...'),
(48, 'Aaron', 'Jimenez', NULL, '47586726P', '1968-04-25', 'Aaroni3@mail.com', 475867260, 10, 5, 400, 400, 'Aqui va una observacion ...'),
(49, 'Pepe', 'Alvarez', NULL, '66578394V', '1961-12-14', 'Pepei2@mail.com', 665783940, 1, 5, 100, 100, 'Aqui va una observacion ...'),
(50, 'Jorge', 'Privado', NULL, '12345332M', '1994-11-21', 'Jorgei1@mail.com', 123453320, 2, 5, 420, 420, 'Aqui va una observacion ...'),
(51, 'Reyes', 'Caja', NULL, '99878566C', '2004-01-18', 'Reyesi3@mail.com', 998785660, 6, 6, 510, 510, 'Aqui va una observacion ...'),
(52, 'Jorge', 'Privado', NULL, '12345332M', '1970-07-17', 'Jorgei2@mail.com', 123453320, 9, 6, 100, 100, 'Aqui va una observacion ...'),
(53, 'Alejandro', 'Vinance', NULL, '45532177T', '1986-08-16', 'Alejandroi1@mail.com', 455321770, 6, 6, 280, 280, 'Aqui va una observacion ...'),
(54, 'Aaron', 'Jimenez', NULL, '47586726P', '1979-03-26', 'Aaroni4@mail.com', 475867260, 10, 6, 230, 230, 'Aqui va una observacion ...'),
(55, 'Miguel', 'Bernard', NULL, '65788909Q', '1979-06-28', 'Migueli1@mail.com', 657889090, 9, 6, 470, 470, 'Aqui va una observacion ...'),
(56, 'Benito', 'Arguelles', NULL, '36485964G', '1985-03-06', 'Benitoi2@mail.com', 364859640, 6, 6, 420, 420, 'Aqui va una observacion ...'),
(57, 'Antonio', 'Rodriguez', NULL, '56473495E', '1965-10-04', 'Antonioi115@mail.com', 564734950, 3, 6, 490, 490, 'Aqui va una observacion ...'),
(58, 'Jorge', 'Privado', NULL, '12345332M', '1981-05-03', 'Jorgei3@mail.com', 123453320, 6, 6, 410, 410, 'Aqui va una observacion ...'),
(59, 'Adolfo', 'Juanillo', NULL, '78562590X', '1989-05-25', 'Adolfoi1@mail.com', 785625900, 7, 6, 40, 40, 'Aqui va una observacion ...'),
(60, 'Adrian', 'Joseju', NULL, '65774894Z', '1977-01-05', 'Adriani5@mail.com', 657748940, 7, 6, 270, 270, 'Aqui va una observacion ...'),
(61, 'Adrian', 'Joseju', NULL, '65774894Z', '1986-05-05', 'Adriani6@mail.com', 657748940, 7, 7, 460, 460, 'Aqui va una observacion ...'),
(62, 'Alvaro', 'Aguilar', NULL, '43589459J', '1974-11-04', 'Alvaroi54@mail.com', 435894590, 8, 7, 450, 450, 'Aqui va una observacion ...'),
(63, 'Alvaro', 'Aguilar', NULL, '43589459J', '2002-04-07', 'Alvaroi544@mail.com', 435894590, 3, 7, 270, 270, 'Aqui va una observacion ...'),
(64, 'Adrian', 'Joseju', NULL, '65774894Z', '1971-09-12', 'Adriani7@mail.com', 657748940, 2, 7, 180, 180, 'Aqui va una observacion ...'),
(65, 'Aaron', 'Jimenez', NULL, '47586726P', '1962-02-05', 'Aaroni56@mail.com', 475867260, 5, 7, 350, 350, 'Aqui va una observacion ...'),
(66, 'Sam', 'Sout', NULL, '91223684|', '1963-04-16', 'Sami@mail.com', 912236840, 1, 7, 200, 200, 'Aqui va una observacion ...'),
(67, 'Jose', 'Lanzas', NULL, '32134560{', '1975-04-26', 'Josei2@mail.com', 321345600, 6, 7, 190, 190, 'Aqui va una observacion ...'),
(68, 'Antonio', 'Rodriguez', NULL, '56473495E', '2000-12-29', 'Antonioi003@mail.com', 564734950, 1, 7, 80, 80, 'Aqui va una observacion ...'),
(69, 'Aaron', 'Jimenez', NULL, '47586726P', '1978-10-05', 'Aaroni66@mail.com', 475867260, 4, 7, 280, 280, 'Aqui va una observacion ...'),
(70, 'Rafa', 'Beethoven', NULL, '64779670O', '1971-01-22', 'Rafai231@mail.com', 647796700, 4, 7, 150, 150, 'Aqui va una observacion ...'),
(71, 'Aaron', 'Jimenez', NULL, '47586726P', '1988-11-10', 'Aaroni34@mail.com', 475867260, 3, 8, 460, 460, 'Aqui va una observacion ...'),
(72, 'Miguel', 'Bernard', NULL, '65788909Q', '1989-04-24', 'Migueli3@mail.com', 657889090, 7, 8, 330, 330, 'Aqui va una observacion ...'),
(73, 'Alejandro', 'Vinance', NULL, '45532177T', '1960-06-27', 'Alejandroi2@mail.com', 455321770, 5, 8, 20, 20, 'Aqui va una observacion ...'),
(74, 'Alberto', 'Santos', NULL, '77594885{', '1975-06-23', 'Albertoi34@mail.com', 775948850, 10, 8, 160, 160, 'Aqui va una observacion ...'),
(75, 'Juan', 'Rutatan', NULL, '66576892H', '2008-11-21', 'Juani5@mail.com', 665768920, 9, 8, 510, 510, 'Aqui va una observacion ...'),
(76, 'Antonio', 'Rodriguez', NULL, '56473495E', '1964-07-02', 'Antonioi32@mail.com', 564734950, 3, 8, 420, 420, 'Aqui va una observacion ...'),
(77, 'Alvaro', 'Aguilar', NULL, '43589459J', '1981-09-04', 'Alvaroi66@mail.com', 435894590, 10, 8, 500, 500, 'Aqui va una observacion ...'),
(78, 'Benito', 'Arguelles', NULL, '36485964G', '1961-07-24', 'Benitoi3@mail.com', 364859640, 7, 8, 150, 150, 'Aqui va una observacion ...'),
(79, 'Alberto', 'Ramon', NULL, '78541255L', '1987-06-21', 'Albertoi4@mail.com', 785412550, 4, 8, 420, 420, 'Aqui va una observacion ...'),
(80, 'Maite', 'Nopor', NULL, '56117523S', '1982-05-05', 'Maitei4@mail.com', 561175230, 2, 8, 330, 330, 'Aqui va una observacion ...'),
(81, 'Antonio', 'Rodriguez', NULL, '56473495E', '2006-04-22', 'Antonioi001@mail.com', 564734950, 2, 9, 30, 30, 'Aqui va una observacion ...'),
(82, 'Francisco', 'Balbuena', NULL, '32234896M', '1973-05-13', 'Franciscoi4@mail.com', 322348960, 1, 9, 190, 190, 'Aqui va una observacion ...'),
(83, 'Francisco', 'Balbuena', NULL, '32234896M', '1979-02-24', 'Franciscoi5@mail.com', 322348960, 2, 9, 420, 420, 'Aqui va una observacion ...'),
(84, 'Alberto', 'Santos', NULL, '77594885{', '1971-02-18', 'Albertoi5@mail.com', 775948850, 3, 9, 300, 300, 'Aqui va una observacion ...'),
(85, 'Reyes', 'Caja', NULL, '99878566C', '1968-05-25', 'Reyesi5@mail.com', 998785660, 5, 9, 80, 80, 'Aqui va una observacion ...'),
(86, 'Jose', 'Lanzas', NULL, '32134560{', '1978-01-01', 'Josei4@mail.com', 321345600, 2, 9, 460, 460, 'Aqui va una observacion ...'),
(87, 'Sam', 'Sout', NULL, '91223684|', '1991-03-08', 'Sami2@mail.com', 912236840, 6, 9, 490, 490, 'Aqui va una observacion ...'),
(88, 'Adrian', 'Joseju', NULL, '65774894Z', '2001-04-14', 'Adriani88@mail.com', 657748940, 2, 9, 180, 180, 'Aqui va una observacion ...'),
(89, 'Reyes', 'Caja', NULL, '99878566C', '1967-01-12', 'Reyesi6@mail.com', 998785660, 1, 9, 260, 260, 'Aqui va una observacion ...'),
(90, 'Alberto', 'Santos', NULL, '77594885{', '1996-03-18', 'Albertoi6@mail.com', 775948850, 8, 9, 200, 200, 'Aqui va una observacion ...'),
(91, 'Jose', 'Lanzas', NULL, '32134560{', '1992-01-01', 'Josei5@mail.com', 321345600, 8, 10, 220, 220, 'Aqui va una observacion ...'),
(92, 'Juan', 'Rutatan', NULL, '66576892H', '1964-11-13', 'Juani6@mail.com', 665768920, 6, 10, 400, 400, 'Aqui va una observacion ...'),
(93, 'Adrian', 'Joseju', NULL, '65774894Z', '1993-12-21', 'Adriani990@mail.com', 657748940, 6, 10, 110, 110, 'Aqui va una observacion ...'),
(94, 'Aaron', 'Jimenez', NULL, '47586726P', '1968-01-16', 'Aaroni90@mail.com', 475867260, 10, 10, 230, 230, 'Aqui va una observacion ...'),
(95, 'Maite', 'Nopor', NULL, '56117523S', '1977-06-15', 'Maitei5@mail.com', 561175230, 6, 10, 230, 230, 'Aqui va una observacion ...'),
(96, 'Juan', 'Rutatan', NULL, '66576892H', '2000-06-15', 'Juani7@mail.com', 665768920, 2, 10, 500, 500, 'Aqui va una observacion ...'),
(97, 'Adolfo', 'Juanillo', NULL, '78562590X', '1988-01-21', 'Adolfoi33@mail.com', 785625900, 6, 10, 190, 190, 'Aqui va una observacion ...'),
(98, 'Alberto', 'Santos', NULL, '77594885{', '2003-06-19', 'Albertoi677@mail.com', 775948850, 6, 10, 190, 190, 'Aqui va una observacion ...'),
(99, 'Juan', 'Rutatan', NULL, '66576892H', '1963-10-05', 'Juani8@mail.com', 665768920, 4, 10, 370, 370, 'Aqui va una observacion ...'),
(100, 'Juan', 'Rutatan', NULL, '66576892H', '1975-01-15', 'Juani9@mail.com', 665768920, 9, 10, 390, 390, 'Aqui va una observacion ...');

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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `empresa`
--

INSERT INTO `empresa` (`id`, `nombre`, `telefono`, `email`, `responsable_empresa`, `observaciones`) VALUES
(1, 'SamS.A', 912236840, 'SamS.Ai122@mail.com', 'Sam Sout', 'Aqui va una observacion ...'),
(2, 'JuanS.A', 665768920, 'JuanS.Ai4543@mail.com', 'Juan Rutatan', 'Aqui va una observacion ...'),
(3, 'BenitoS.A', 364859640, 'BenitoS.Ai007@mail.com', 'Benito Arguelles', 'Aqui va una observacion ...'),
(4, 'BenitoS.A', 364859640, 'BenitoS.Ai54@mail.com', 'Benito Arguelles', 'Aqui va una observacion ...'),
(5, 'RafaS.A', 647796700, 'RafaS.Ai876@mail.com', 'Rafa Beethoven', 'Aqui va una observacion ...'),
(6, 'AntonioS.A', 564734950, 'AntonioS.Ai010@mail.com', 'Antonio Rodriguez', 'Aqui va una observacion ...'),
(7, 'MiguelS.A', 657889090, 'MiguelS.AiAsmi@mail.com', 'Miguel Bernard', 'Aqui va una observacion ...'),
(8, 'MaiteS.A', 561175230, 'MaiteS.AiFilip@mail.com', 'Maite Nopor', 'Aqui va una observacion ...'),
(9, 'AdolfoS.A', 785625900, 'AdolfoS.AiCoin@mail.com', 'Adolfo Juanillo', 'Aqui va una observacion ...'),
(10, 'JoseS.A', 321345600, 'JoseS.AiCaper@mail.com', 'Jose Lanzas', 'Aqui va una observacion ...');

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
) ENGINE=InnoDB AUTO_INCREMENT=301 DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `practica`
--

INSERT INTO `practica` (`id`, `alumno_asociado`, `fecha`, `tipo_practica`, `total_horas_dia`, `actividad`, `observaciones`) VALUES
(1, 1, '1968-07-09', 'FCT', 30, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(2, 1, '1993-04-09', 'DUAL', 30, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(3, 1, '1992-02-03', 'FCT', 30, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(4, 2, '1975-09-18', 'FCT', 40, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(5, 2, '1992-03-27', 'DUAL', 40, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(6, 2, '1998-06-10', 'FCT', 40, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(7, 3, '1970-03-12', 'FCT', 12, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(8, 3, '1971-04-25', 'DUAL', 12, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(9, 3, '1975-03-08', 'FCT', 12, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(10, 4, '1997-08-14', 'FCT', 32, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(11, 4, '1967-08-01', 'DUAL', 32, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(12, 4, '1978-10-18', 'FCT', 32, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(13, 5, '1966-01-27', 'FCT', 125, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(14, 5, '2000-04-09', 'DUAL', 125, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(15, 5, '1983-08-06', 'FCT', 125, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(16, 6, '1965-09-08', 'FCT', 22, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(17, 6, '1973-06-29', 'DUAL', 22, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(18, 6, '1974-03-20', 'FCT', 22, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(19, 7, '1966-05-25', 'FCT', 110, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(20, 7, '1977-10-25', 'DUAL', 110, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(21, 7, '1967-10-23', 'FCT', 110, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(22, 8, '1961-05-29', 'FCT', 112, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(23, 8, '1973-02-28', 'DUAL', 112, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(24, 8, '1997-10-17', 'FCT', 112, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(25, 9, '1989-01-12', 'FCT', 5, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(26, 9, '1992-10-21', 'DUAL', 5, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(27, 9, '1993-03-12', 'FCT', 5, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(28, 10, '1963-01-08', 'FCT', 67, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(29, 10, '2004-04-03', 'DUAL', 67, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(30, 10, '1970-04-29', 'FCT', 67, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(31, 11, '1996-11-29', 'FCT', 125, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(32, 11, '2008-01-24', 'DUAL', 125, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(33, 11, '1982-03-27', 'FCT', 125, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(34, 12, '1960-11-12', 'FCT', 55, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(35, 12, '1976-12-07', 'DUAL', 55, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(36, 12, '1991-12-16', 'FCT', 55, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(37, 13, '1973-10-15', 'FCT', 117, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(38, 13, '2005-08-24', 'DUAL', 117, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(39, 13, '2008-08-22', 'FCT', 117, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(40, 14, '1993-11-22', 'FCT', 95, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(41, 14, '1993-08-29', 'DUAL', 95, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(42, 14, '1970-06-15', 'FCT', 95, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(43, 15, '1966-01-04', 'FCT', 100, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(44, 15, '2009-02-10', 'DUAL', 100, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(45, 15, '1984-11-01', 'FCT', 100, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(46, 16, '1989-10-22', 'FCT', 105, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(47, 16, '1983-12-06', 'DUAL', 105, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(48, 16, '1960-05-04', 'FCT', 105, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(49, 17, '1962-06-20', 'FCT', 122, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(50, 17, '1974-03-24', 'DUAL', 122, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(51, 17, '1991-04-09', 'FCT', 122, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(52, 18, '1978-05-05', 'FCT', 50, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(53, 18, '1989-04-08', 'DUAL', 50, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(54, 18, '1972-04-05', 'FCT', 50, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(55, 19, '1960-11-08', 'FCT', 87, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(56, 19, '1968-12-05', 'DUAL', 87, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(57, 19, '1966-09-12', 'FCT', 87, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(58, 20, '1984-02-13', 'FCT', 120, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(59, 20, '1988-05-14', 'DUAL', 120, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(60, 20, '2002-09-18', 'FCT', 120, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(61, 21, '1978-09-22', 'FCT', 95, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(62, 21, '2005-03-21', 'DUAL', 95, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(63, 21, '1962-03-28', 'FCT', 95, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(64, 22, '1976-05-25', 'FCT', 37, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(65, 22, '1982-04-08', 'DUAL', 37, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(66, 22, '1993-01-05', 'FCT', 37, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(67, 23, '1967-08-11', 'FCT', 105, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(68, 23, '1966-08-14', 'DUAL', 105, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(69, 23, '1981-04-27', 'FCT', 105, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(70, 24, '1964-02-23', 'FCT', 65, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(71, 24, '2003-02-16', 'DUAL', 65, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(72, 24, '1971-01-05', 'FCT', 65, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(73, 25, '2003-09-27', 'FCT', 12, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(74, 25, '2005-01-11', 'DUAL', 12, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(75, 25, '1972-04-06', 'FCT', 12, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(76, 26, '2003-05-09', 'FCT', 100, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(77, 26, '1995-06-12', 'DUAL', 100, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(78, 26, '1994-05-15', 'FCT', 100, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(79, 27, '1977-04-25', 'FCT', 15, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(80, 27, '1960-08-09', 'DUAL', 15, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(81, 27, '1982-07-25', 'FCT', 15, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(82, 28, '1980-02-02', 'FCT', 22, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(83, 28, '1999-03-05', 'DUAL', 22, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(84, 28, '1977-07-11', 'FCT', 22, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(85, 29, '1986-03-27', 'FCT', 30, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(86, 29, '1961-07-23', 'DUAL', 30, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(87, 29, '2009-02-17', 'FCT', 30, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(88, 30, '2006-12-21', 'FCT', 110, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(89, 30, '1964-04-28', 'DUAL', 110, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(90, 30, '2003-10-13', 'FCT', 110, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(91, 31, '2003-01-07', 'FCT', 15, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(92, 31, '1976-05-28', 'DUAL', 15, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(93, 31, '2003-04-17', 'FCT', 15, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(94, 32, '1962-06-05', 'FCT', 70, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(95, 32, '1983-10-06', 'DUAL', 70, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(96, 32, '1966-03-06', 'FCT', 70, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(97, 33, '2008-12-18', 'FCT', 77, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(98, 33, '1982-04-21', 'DUAL', 77, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(99, 33, '1967-04-16', 'FCT', 77, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(100, 34, '1971-02-14', 'FCT', 35, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(101, 34, '1996-05-21', 'DUAL', 35, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(102, 34, '1960-01-29', 'FCT', 35, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(103, 35, '1971-08-10', 'FCT', 52, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(104, 35, '1991-12-13', 'DUAL', 52, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(105, 35, '1974-02-02', 'FCT', 52, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(106, 36, '1980-09-14', 'FCT', 60, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(107, 36, '2004-11-02', 'DUAL', 60, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(108, 36, '1969-07-27', 'FCT', 60, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(109, 37, '1987-05-07', 'FCT', 30, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(110, 37, '2002-09-17', 'DUAL', 30, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(111, 37, '1985-08-19', 'FCT', 30, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(112, 38, '1975-11-04', 'FCT', 50, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(113, 38, '2005-02-07', 'DUAL', 50, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(114, 38, '1974-05-18', 'FCT', 50, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(115, 39, '1981-03-22', 'FCT', 102, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(116, 39, '1985-07-20', 'DUAL', 102, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(117, 39, '1963-06-09', 'FCT', 102, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(118, 40, '1981-02-16', 'FCT', 25, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(119, 40, '1996-02-27', 'DUAL', 25, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(120, 40, '1976-08-27', 'FCT', 25, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(121, 41, '1967-01-10', 'FCT', 80, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(122, 41, '1980-12-17', 'DUAL', 80, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(123, 41, '1970-09-01', 'FCT', 80, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(124, 42, '1983-08-11', 'FCT', 42, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(125, 42, '1967-04-29', 'DUAL', 42, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(126, 42, '2009-09-22', 'FCT', 42, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(127, 43, '1990-05-27', 'FCT', 17, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(128, 43, '1980-02-08', 'DUAL', 17, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(129, 43, '1982-06-22', 'FCT', 17, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(130, 44, '1999-10-21', 'FCT', 47, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(131, 44, '1981-10-09', 'DUAL', 47, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(132, 44, '1998-04-29', 'FCT', 47, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(133, 45, '1991-06-26', 'FCT', 37, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(134, 45, '2000-01-10', 'DUAL', 37, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(135, 45, '1965-02-13', 'FCT', 37, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(136, 46, '2008-05-28', 'FCT', 27, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(137, 46, '2001-05-18', 'DUAL', 27, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(138, 46, '1997-06-08', 'FCT', 27, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(139, 47, '2006-06-05', 'FCT', 67, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(140, 47, '2001-09-17', 'DUAL', 67, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(141, 47, '1997-02-26', 'FCT', 67, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(142, 48, '2001-07-14', 'FCT', 100, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(143, 48, '2009-03-16', 'DUAL', 100, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(144, 48, '1998-05-16', 'FCT', 100, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(145, 49, '1984-05-13', 'FCT', 25, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(146, 49, '1960-09-13', 'DUAL', 25, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(147, 49, '1971-11-04', 'FCT', 25, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(148, 50, '1997-10-21', 'FCT', 105, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(149, 50, '1964-12-26', 'DUAL', 105, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(150, 50, '1987-02-15', 'FCT', 105, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(151, 51, '2006-11-01', 'FCT', 127, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(152, 51, '2008-07-07', 'DUAL', 127, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(153, 51, '2000-12-01', 'FCT', 127, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(154, 52, '2008-01-17', 'FCT', 25, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(155, 52, '1980-12-06', 'DUAL', 25, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(156, 52, '2001-07-29', 'FCT', 25, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(157, 53, '1969-01-23', 'FCT', 70, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(158, 53, '1994-08-04', 'DUAL', 70, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(159, 53, '1962-07-03', 'FCT', 70, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(160, 54, '1980-12-29', 'FCT', 57, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(161, 54, '1986-09-10', 'DUAL', 57, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(162, 54, '1973-05-26', 'FCT', 57, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(163, 55, '1974-10-19', 'FCT', 117, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(164, 55, '2002-01-29', 'DUAL', 117, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(165, 55, '2002-05-05', 'FCT', 117, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(166, 56, '1975-05-17', 'FCT', 105, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(167, 56, '1994-07-13', 'DUAL', 105, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(168, 56, '1992-05-22', 'FCT', 105, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(169, 57, '1968-12-14', 'FCT', 122, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(170, 57, '1962-07-26', 'DUAL', 122, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(171, 57, '1984-10-26', 'FCT', 122, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(172, 58, '1978-07-21', 'FCT', 102, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(173, 58, '2009-05-25', 'DUAL', 102, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(174, 58, '1986-01-22', 'FCT', 102, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(175, 59, '1992-07-03', 'FCT', 10, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(176, 59, '1960-10-26', 'DUAL', 10, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(177, 59, '1992-10-04', 'FCT', 10, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(178, 60, '1995-09-29', 'FCT', 67, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(179, 60, '2008-12-29', 'DUAL', 67, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(180, 60, '2004-10-02', 'FCT', 67, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(181, 61, '2001-01-12', 'FCT', 115, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(182, 61, '1993-04-29', 'DUAL', 115, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(183, 61, '2007-05-02', 'FCT', 115, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(184, 62, '2004-05-23', 'FCT', 112, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(185, 62, '1996-02-11', 'DUAL', 112, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(186, 62, '1983-09-21', 'FCT', 112, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(187, 63, '1965-11-16', 'FCT', 67, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(188, 63, '2000-09-11', 'DUAL', 67, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(189, 63, '1972-02-02', 'FCT', 67, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(190, 64, '2000-08-02', 'FCT', 45, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(191, 64, '1980-06-19', 'DUAL', 45, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(192, 64, '1996-05-22', 'FCT', 45, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(193, 65, '2009-03-03', 'FCT', 87, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(194, 65, '1984-12-27', 'DUAL', 87, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(195, 65, '1990-03-01', 'FCT', 87, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(196, 66, '1963-06-18', 'FCT', 50, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(197, 66, '1962-04-20', 'DUAL', 50, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(198, 66, '1969-12-03', 'FCT', 50, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(199, 67, '1961-10-05', 'FCT', 47, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(200, 67, '1993-07-18', 'DUAL', 47, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(201, 67, '1969-08-06', 'FCT', 47, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(202, 68, '1982-02-01', 'FCT', 20, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(203, 68, '2001-06-04', 'DUAL', 20, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(204, 68, '1976-05-18', 'FCT', 20, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(205, 69, '1972-06-22', 'FCT', 70, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(206, 69, '1979-04-01', 'DUAL', 70, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(207, 69, '1973-06-22', 'FCT', 70, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(208, 70, '1996-01-03', 'FCT', 37, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(209, 70, '1972-06-21', 'DUAL', 37, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(210, 70, '1981-01-08', 'FCT', 37, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(211, 71, '1963-02-23', 'FCT', 115, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(212, 71, '2007-03-16', 'DUAL', 115, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(213, 71, '1991-01-15', 'FCT', 115, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(214, 72, '1998-09-15', 'FCT', 82, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(215, 72, '1998-11-22', 'DUAL', 82, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(216, 72, '2004-01-17', 'FCT', 82, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(217, 73, '1978-08-18', 'FCT', 5, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(218, 73, '1997-10-25', 'DUAL', 5, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(219, 73, '2009-12-04', 'FCT', 5, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(220, 74, '2006-11-05', 'FCT', 40, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(221, 74, '1975-06-21', 'DUAL', 40, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(222, 74, '1983-01-10', 'FCT', 40, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(223, 75, '2001-07-03', 'FCT', 127, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(224, 75, '1995-10-15', 'DUAL', 127, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(225, 75, '2001-07-11', 'FCT', 127, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(226, 76, '1964-08-17', 'FCT', 105, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(227, 76, '1988-07-18', 'DUAL', 105, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(228, 76, '2006-07-22', 'FCT', 105, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(229, 77, '1998-04-22', 'FCT', 125, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(230, 77, '1990-04-02', 'DUAL', 125, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(231, 77, '2007-04-20', 'FCT', 125, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(232, 78, '1961-08-08', 'FCT', 37, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(233, 78, '1989-08-02', 'DUAL', 37, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(234, 78, '1968-05-02', 'FCT', 37, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(235, 79, '1991-01-10', 'FCT', 105, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(236, 79, '1994-04-04', 'DUAL', 105, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(237, 79, '1967-05-27', 'FCT', 105, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(238, 80, '2009-07-06', 'FCT', 82, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(239, 80, '1967-09-23', 'DUAL', 82, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(240, 80, '1964-07-27', 'FCT', 82, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(241, 81, '2000-05-20', 'FCT', 7, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(242, 81, '1982-08-24', 'DUAL', 7, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(243, 81, '1977-10-11', 'FCT', 7, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(244, 82, '1990-02-13', 'FCT', 47, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(245, 82, '1961-05-28', 'DUAL', 47, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(246, 82, '1969-05-01', 'FCT', 47, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(247, 83, '2001-08-12', 'FCT', 105, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(248, 83, '1965-08-23', 'DUAL', 105, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(249, 83, '1997-03-04', 'FCT', 105, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(250, 84, '2006-11-23', 'FCT', 75, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(251, 84, '1963-02-08', 'DUAL', 75, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(252, 84, '1987-03-03', 'FCT', 75, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(253, 85, '1988-10-20', 'FCT', 20, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(254, 85, '1998-01-08', 'DUAL', 20, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(255, 85, '1981-10-10', 'FCT', 20, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(256, 86, '1966-08-13', 'FCT', 115, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(257, 86, '1994-09-25', 'DUAL', 115, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(258, 86, '1960-03-29', 'FCT', 115, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(259, 87, '1980-10-28', 'FCT', 122, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(260, 87, '1989-08-06', 'DUAL', 122, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(261, 87, '1989-06-28', 'FCT', 122, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(262, 88, '1963-09-26', 'FCT', 45, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(263, 88, '1970-10-22', 'DUAL', 45, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(264, 88, '1985-08-02', 'FCT', 45, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(265, 89, '2007-05-12', 'FCT', 65, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(266, 89, '2000-06-18', 'DUAL', 65, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(267, 89, '1979-03-15', 'FCT', 65, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(268, 90, '1990-05-18', 'FCT', 50, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(269, 90, '1995-09-29', 'DUAL', 50, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(270, 90, '1962-03-24', 'FCT', 50, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(271, 91, '2001-09-27', 'FCT', 55, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(272, 91, '1994-05-15', 'DUAL', 55, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(273, 91, '1960-02-23', 'FCT', 55, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(274, 92, '1987-10-15', 'FCT', 100, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(275, 92, '1964-11-16', 'DUAL', 100, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(276, 92, '1969-01-08', 'FCT', 100, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(277, 93, '2008-05-16', 'FCT', 27, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(278, 93, '1991-04-14', 'DUAL', 27, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(279, 93, '1988-01-16', 'FCT', 27, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(280, 94, '2007-11-15', 'FCT', 57, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(281, 94, '1987-10-28', 'DUAL', 57, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(282, 94, '2003-02-23', 'FCT', 57, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(283, 95, '1975-02-22', 'FCT', 57, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(284, 95, '1994-03-04', 'DUAL', 57, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(285, 95, '1981-05-26', 'FCT', 57, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(286, 96, '2008-02-03', 'FCT', 125, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(287, 96, '1977-11-17', 'DUAL', 125, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(288, 96, '1964-01-14', 'FCT', 125, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(289, 97, '1986-10-26', 'FCT', 47, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(290, 97, '1966-11-08', 'DUAL', 47, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(291, 97, '1997-11-25', 'FCT', 47, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(292, 98, '1996-04-22', 'FCT', 47, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(293, 98, '1986-02-14', 'DUAL', 47, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(294, 98, '1985-09-20', 'FCT', 47, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(295, 99, '2006-02-10', 'FCT', 92, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(296, 99, '1988-12-16', 'DUAL', 92, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(297, 99, '2003-02-11', 'FCT', 92, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(298, 100, '1966-01-01', 'FCT', 97, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(299, 100, '1969-02-18', 'DUAL', 97, 'Rellenar base de Datos', 'Aqui va una observacion ...'),
(300, 100, '1984-07-13', 'FCT', 97, 'Rellenar base de Datos', 'Aqui va una observacion ...');

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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `profesor`
--

INSERT INTO `profesor` (`id`, `nombre`, `apellido`, `contraseña`, `correo`) VALUES
(1, 'Adolfo', 'Juanillo', NULL, 'AdolfoiMell@mail.com'),
(2, 'Alberto', 'Ramon', NULL, 'Albertoi98@mail.com'),
(3, 'Miguel', 'Bernard', NULL, 'MigueliPlis@mail.com'),
(4, 'Adrian', 'Joseju', 0xd80ca175bd8cc262fa4dbfa5daef8c48, 'Adriani32@mail.com'),
(5, 'Alejandro', 'Vinance', NULL, 'AlejandroiTri@mail.com'),
(6, 'Adolfo', 'Juanillo', NULL, 'AdolfoiCruf@mail.com'),
(7, 'Alberto', 'Santos', NULL, 'Albertoi12Cal@mail.com'),
(8, 'Pepe', 'Alvarez', NULL, 'Pepei3@mail.com'),
(9, 'Antonio', 'Rodriguez', NULL, 'AntonioiDos@mail.com'),
(10, 'Aaron', 'Jimenez', NULL, 'AaroniMun@mail.com');

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
