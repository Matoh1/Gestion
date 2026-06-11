#!/bin/bash

osascript -e 'tell application "Terminal" to do script "cd \"'"$(pwd)"'/gateway\" && ./mvnw spring-boot:run"'

osascript -e 'tell application "Terminal" to do script "cd \"'"$(pwd)"'/jedis\" && ./mvnw spring-boot:run"'

# 2. Abre otra pestaña/ventana para el servicio de sables y lo ejecuta
osascript -e 'tell application "Terminal" to do script "cd \"'"$(pwd)"'/sables\" && ./mvnw spring-boot:run"'