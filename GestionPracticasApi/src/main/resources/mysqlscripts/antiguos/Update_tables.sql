ALTER TABLE `gestion_empresa`.`profesor` 
CHANGE COLUMN `contraseña` `contraseña` BLOB NULL ;

ALTER TABLE `gestion_empresa`.`alumno` 
CHANGE COLUMN `contraseña` `contraseña` BLOB NULL ;