Procedimiento para ejecutar los scripts sql:

Esenciales para logearse es los de SUDO,
1. ejecutar crear_sudo_table.sql (este ya trae la tabla con la fila de SUDO_ADMIN)
2. ejecutar login.sql
3. Entrar en la app con usuario: SUDO_ADMIN contraseña: admin1234

Para el resto de usuarios, hay que cambiar las columnas de contraseña a tipo BLOB:
1. Borrar las contraseñas almacenadas
2. ejecutar Update_tables.sql
3. ejecutar crear_pass.sql
4. para cada usuario hay que crearle una contraseña con llamando a crear_pass (no hace falta para el SUD)

En caso de errores hay un Soluciones_posibles_errores.md que os puede venir bien
