CREATE TABLE cristal (
    id_cristal INT AUTO_INCREMENT PRIMARY KEY,
    color_cristal VARCHAR(20) NOT NULL,
    tamano_cristal DOUBLE NOT NULL
);

CREATE TABLE sables (
    id INT AUTO_INCREMENT PRIMARY KEY,
    bando VARCHAR(30) NOT NULL,
    jedi_id INT NOT NULL, 
    id_cristal_fk INT,   
    CONSTRAINT fk_sables_cristal FOREIGN KEY (id_cristal_fk) 
        REFERENCES cristal(id_cristal) ON DELETE SET NULL
);

INSERT INTO cristal (color_cristal, tamano_cristal) VALUES 
('Verde Adegan', 4.5),
('Violeta Hurrikane', 5.2),
('Rojo Carmesí', 6.0);

INSERT INTO sables (bando, jedi_id, id_cristal_fk) VALUES 
('Luminoso', 1, 1),
('Luminoso', 3, 2),
('Oscuro', 2, 3);