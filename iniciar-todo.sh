#!/bin/bash

echo "Iniciando Servidor de Descubrimiento Eureka (Puerto 8761)..."
osascript -e 'tell application "Terminal" to do script "cd \"'"$(pwd)"'/eureka\" && ./mvnw spring-boot:run"'

echo "Esperando 12 segundos a que Eureka se estabilice..."
sleep 12

echo "Iniciando API Gateway..."
osascript -e 'tell application "Terminal" to do script "cd \"'"$(pwd)"'/gateway\" && ./mvnw spring-boot:run"'

echo "Iniciando Microservicio Residencias..."
osascript -e 'tell application "Terminal" to do script "cd \"'"$(pwd)"'/Residencias\" && ./mvnw spring-boot:run"'

echo "Iniciando Microservicio Espacios..."
osascript -e 'tell application "Terminal" to do script "cd \"'"$(pwd)"'/Espacios\" && ./mvnw spring-boot:run"'

echo "Iniciando Microservicio Incidencias..."
osascript -e 'tell application "Terminal" to do script "cd \"'"$(pwd)"'/Incidencias\" && ./mvnw spring-boot:run"'

echo "Ecosistema lanzado. Dashboard disponible en http://localhost:8761"
