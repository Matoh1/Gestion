-- Catalogo de tipos de incidencia (Plomeria, Electricidad, etc.)
CREATE TABLE Tipo_Incidencia (
    Tipo_Incidencia_ID INT AUTO_INCREMENT PRIMARY KEY,
    Nombre_Tipo VARCHAR(50) NOT NULL
);

-- Reporte (cabecera). Residencia_ID es solo el id del microservicio Residencias,
-- NO una llave foranea: cada microservicio tiene su propia base de datos.
CREATE TABLE Incidencias (
    Incidencias_ID INT AUTO_INCREMENT PRIMARY KEY,
    Residencia_ID INT NOT NULL,
    Titulo_Reporte VARCHAR(200) NOT NULL,
    Fecha_Reporte DATE NOT NULL,
    Prioridad VARCHAR(50) NOT NULL
);

-- Detalle: cada incidencia pertenece a un reporte y a un tipo (relaciones internas).
CREATE TABLE Incidencia (
    Incidencia_ID INT AUTO_INCREMENT PRIMARY KEY,
    Incidencias_ID INT NOT NULL,
    Tipo_Incidencia_ID INT NOT NULL,
    Descripcion TEXT NOT NULL,
    Estado VARCHAR(50) NOT NULL,
    CONSTRAINT fk_incidencia_reporte FOREIGN KEY (Incidencias_ID)
        REFERENCES Incidencias(Incidencias_ID) ON DELETE CASCADE,
    CONSTRAINT fk_incidencia_tipo FOREIGN KEY (Tipo_Incidencia_ID)
        REFERENCES Tipo_Incidencia(Tipo_Incidencia_ID)
);

INSERT INTO Tipo_Incidencia (Nombre_Tipo) VALUES
('Plomeria'),
('Electricidad'),
('Seguridad');

-- Residencia_ID 1 y 2 corresponden a 'Torre A' y 'Torre B' del microservicio Residencias.
INSERT INTO Incidencias (Residencia_ID, Titulo_Reporte, Fecha_Reporte, Prioridad) VALUES
(1, 'Fuga de agua en el pasillo del primer piso', '2026-06-01', 'Alta'),
(2, 'Cortes de luz intermitentes en Torre B', '2026-06-05', 'Media');

INSERT INTO Incidencia (Incidencias_ID, Tipo_Incidencia_ID, Descripcion, Estado) VALUES
(1, 1, 'El agua brota desde la conexion principal del bano comun', 'Abierta'),
(1, 3, 'El pasillo mojado representa riesgo de caidas para los residentes', 'En proceso'),
(2, 2, 'La luz del hall central se apaga cada 10 minutos', 'Abierta');
