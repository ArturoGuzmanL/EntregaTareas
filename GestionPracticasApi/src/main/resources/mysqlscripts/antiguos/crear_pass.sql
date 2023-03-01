DELIMITER $$
CREATE PROCEDURE `crear_pass`(
	_id INT,
    _rol SMALLINT,
    _password VARCHAR(45)
)
BEGIN
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
DELIMITER ;
