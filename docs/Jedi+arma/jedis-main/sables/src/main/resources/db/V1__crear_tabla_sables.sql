CREATE TABLE sables (
    id INT AUTO_INCREMENT PRIMARY KEY,
    color VARCHAR(20) NOT NULL,
    cristal_kyber VARCHAR(30) NOT NULL,
    jedi_id INT NOT NULL
);

INSERT INTO sables (color, cristal_kyber, jedi_id) VALUES ('Azul', 'Ilum Ancestral', 1);
INSERT INTO sables (color, cristal_kyber, jedi_id) VALUES ('Rojo', 'Sintético Oscuro', 2);