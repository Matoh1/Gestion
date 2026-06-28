DROP TABLE IF EXISTS Espacios;
DROP TABLE IF EXISTS Espacio;
DROP TABLE IF EXISTS User;

CREATE TABLE Espacio (
    Espacio_ID INT AUTO_INCREMENT PRIMARY KEY,
    Nombre_Espacio VARCHAR(40) NOT NULL,
    Tipo_Espacio VARCHAR(30) NOT NULL,
    Capacidad INT NOT NULL
);

CREATE TABLE Espacios (
    Espacios_ID INT AUTO_INCREMENT PRIMARY KEY,
    Espacio_ID INT NOT NULL,
    Residencia_ID INT NOT NULL
);

CREATE TABLE User (
    user_ID INT AUTO_INCREMENT PRIMARY KEY,
    user_nombre VARCHAR(40) NOT NULL,
    user_apellido VARCHAR(50) NOT NULL,
    user_rut VARCHAR(11) NOT NULL,
    user_email VARCHAR(45),
    user_telefono INT,
    Residencia_ID INT NULL
);

INSERT INTO Espacio (Nombre_Espacio, Tipo_Espacio, Capacidad) VALUES
('Tacon', 'Sala de baile', 35),
('Limeno', 'Apartamento', 92);

INSERT INTO Espacios (Espacio_ID, Residencia_ID) VALUES 
(1, 1),
(2, 2);

INSERT INTO User (user_nombre, user_apellido, user_rut, user_email, user_telefono, Residencia_ID) VALUES
('Jamie', 'Rogers', '218543328', 'jamrog@gmail.com', 987621756, NULL),
('Dan', 'Bennet', '208243358', 'danito@gmail.com', 935410570, NULL);
