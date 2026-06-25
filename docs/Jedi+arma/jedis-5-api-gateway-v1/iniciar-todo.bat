@echo off

cd gateway
start cmd /k "mvnw spring-boot:run"

cd ../jedis
start cmd /k "mvnw spring-boot:run"

cd ../sables
start cmd /k "mvnw spring-boot:run"