ERROR:
You are using safe update mode and you tried to update a table without a WHERE that uses a KEY column To disable safe mode, toggle the option ....

SOLUCION:
SET SQL_SAFE_UPDATES = 0;

_______________________________________________


ERROR:
This function has none of DETERMINISTIC, NO SQL, or READS SQL DATA in its declaration and binary logging is enabled (you *might* want to use the less safe log_bin_trust_function_creators variable)

SOLUCION:
SET GLOBAL log_bin_trust_function_creators = 1;
