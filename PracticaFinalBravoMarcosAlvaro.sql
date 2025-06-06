create database PracticaFinalBravoMarcosAlvaro;
use PracticaFinalBravoMarcosAlvaro;
CREATE table Usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) UNIQUE NOT NULL,
	contraseña VARCHAR(255)  NOT NULL,
    uuid CHAR(36) NOT NULL
);


CREATE TABLE Coches (
    id INT AUTO_INCREMENT PRIMARY KEY,
    marca VARCHAR(255) NOT NULL,
    modelo VARCHAR(255) NOT NULL,
    matricula VARCHAR(255) NOT NULL,
    anio INT NOT NULL
);


CREATE TABLE Gastos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    coche_id INT,
    tipo ENUM('gasolina', 'revisión', 'ITV', 'cambio de aceite', 'otros') NOT NULL,
    kilometraje INT NOT NULL,
    fecha DATE NOT NULL,
    importe DECIMAL(10, 2) NOT NULL,
    descripcion TEXT,
    FOREIGN KEY (coche_id) REFERENCES Coches(id)
);


CREATE TABLE Propietarios (
    coche_id INT,
    usuario_id INT,
    PRIMARY KEY (coche_id, usuario_id),
    FOREIGN KEY (coche_id) REFERENCES Coches(id),
    FOREIGN KEY (usuario_id) REFERENCES Usuarios(id)
);

select * from Usuarios;
select * from Coches ;
select * from Propietarios ;

select * from Gastos ;

-- INSERT INTO Usuarios (nombre, contraseña, uuid) value ("a", "a","1bf4b7ae-7dfd-44bd-8242-a85c8057e721")
