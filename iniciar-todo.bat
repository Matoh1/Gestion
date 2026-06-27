@echo off

echo Iniciando Servidor de Descubrimiento Eureka (Puerto 8761)...
cd eureka
start cmd /k "mvnw spring-boot:run"

echo Esperando 12 segundos a que Eureka se estabilice...
timeout /t 12 /nobreak > nul

echo Iniciando API Gateway...
cd ../gateway
start cmd /k "mvnw spring-boot:run"

echo Iniciando Microservicio Residencias...
cd ../Residencias
start cmd /k "mvnw spring-boot:run"

echo Iniciando Microservicio Espacios...
cd ../Espacios
start cmd /k "mvnw spring-boot:run"

echo Iniciando Microservicio Incidencias...
cd ../Incidencias
start cmd /k "mvnw spring-boot:run"

echo Ecosistema lanzado. Dashboard disponible en http://localhost:8761
