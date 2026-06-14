CREATE TABLE jedis (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    bando VARCHAR(20) NOT NULL,
    midiclorianos INT NOT NULL
);

INSERT INTO jedis (nombre, bando, midiclorianos) VALUES ('Grogu', 'Luminoso', 15000);
