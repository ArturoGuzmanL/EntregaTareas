SET GLOBAL log_bin_trust_function_creators = 1;

DELIMITER $$
CREATE FUNCTION `login`(
	_email VARCHAR(255),
    _password VARCHAR(255),
    _rol SMALLINT
) RETURNS tinyint
BEGIN
	DECLARE _passEncriptada BLOB DEFAULT '';
	DECLARE _resultado BOOLEAN DEFAULT 0;
    
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
