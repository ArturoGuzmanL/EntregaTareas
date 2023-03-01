CREATE TABLE `gestion_empresa`.`sudo` (
  `id` INT NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `contraseña` BLOB NOT NULL,
  PRIMARY KEY (`id`));

INSERT INTO gestion_empresa.sudo (id, email, contraseña)
VALUES (1, 'SUDO_ADMIN', aes_encrypt('admin1234', SHA2('CLAVE',512)));