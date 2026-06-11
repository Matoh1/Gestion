CREATE TABLE comuna (
    id_comuna INT AUTO_INCREMENT PRIMARY KEY,
    nombre_comuna VARCHAR(50) NOT NULL,
    region_id INT ,
    CONSTRAINT fk_comuna_region FOREIGN KEY (region_id)
        REFERENCES region(id_region) ON DELETE SET NULL
);

CREATE TABLE region (
    id_region INT AUTO_INCREMENT PRIMARY KEY,
    nombre_region VARCHAR(50) NOT NULL
);

CREATE TABLE residencia (
    id_residencia INT AUTO_INCREMENT PRIMARY KEY,
    nombre_residencia VARCHAR(100) NOT NULL,
    direccion VARCHAR(200) NOT NULL,
    comuna_id INT ,
    CONSTRAINT fk_residencia_comuna FOREIGN KEY (comuna_id)
        REFERENCES comuna(id_comuna) ON DELETE SET NULL
);

CREATE TABLE Residencias (
    id INT AUTO_INCREMENT PRIMARY KEY,
    residencia_id INT,
    user_id INT,
    CONSTRAINT fk_intermedia_residencia FOREIGN KEY (residencia_id) 
        REFERENCES residencia(id) ON DELETE SET NULL,
    CONSTRAINT fk_intermedia_user FOREIGN KEY (user_id) 
        REFERENCES user(id) ON DELETE SET NULL);

INSERT INTO comuna (nombre_comuna, region_id) VALUES 
('Santiago', 1),
('Pudahuel', 1),
('Lo Barnechea', 2);

INSERT INTO region (nombre_region) VALUES 
('Region Metropolitana'),
('Region de Valparaiso');

INSERT INTO residencia (nombre_residencia, direccion, comuna_id) VALUES 
('Torre A', 'Avenida Siempre Viva #123', 1),
('Torre B', 'Calle falsa #456', 2),
('Torre C', 'Avenida Bonghomeri #789', 3);

INSERT INTO Residencias (residencia_id, user_id) VALUES 
(1, 1),
(2, 2),
(3, 3);