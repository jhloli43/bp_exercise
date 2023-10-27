DROP DATABASE IF EXISTS bp_exercise;
CREATE DATABASE bp_exercise;

USE bp_exercise;

DROP TABLE IF EXISTS cliente;
CREATE TABLE cliente (
	id VARCHAR(40) DEFAULT(uuid()) NOT NULL PRIMARY KEY,
	nombre NVARCHAR(200) NOT NULL,
	genero CHAR(1) NOT NULL,
	edad INT,
	identificacion VARCHAR(20) UNIQUE NOT NULL,
	direccion NVARCHAR(200) NOT NULL,
	telefono VARCHAR(20) NOT NULL,
	cliente_id VARCHAR(40) NOT NULL,
	contraseña VARCHAR(20) NOT NULL,
	estado BOOLEAN NOT NULL
);

DROP TABLE IF EXISTS cuenta;
CREATE TABLE cuenta (
	id VARCHAR(40) DEFAULT(uuid()) NOT NULL PRIMARY KEY,
	numero_cuenta VARCHAR(30) UNIQUE NOT NULL,
	tipo_cuenta CHAR(1) NOT NULL,
	saldo_inicial DOUBLE NOT NULL,
	saldo_disponible DOUBLE NOT NULL,
	estado BOOLEAN NOT NULL,
	cliente_id VARCHAR(40) NOT NULL,
	CONSTRAINT fkClienteId FOREIGN KEY (cliente_id) REFERENCES cliente(id)
);

DROP TABLE IF EXISTS movimiento;
CREATE TABLE movimiento (
	id VARCHAR(40) DEFAULT(uuid()) NOT NULL PRIMARY KEY,
	fecha DATE NOT NULL,
	tipo_movimiento CHAR(1) NOT NULL,
	valor DOUBLE NOT NULL,
	saldo DOUBLE NOT NULL,
	cuenta_id VARCHAR(40) NOT NULL,
	CONSTRAINT fkCuentaId FOREIGN KEY (cuenta_id) REFERENCES cuenta(id)
);