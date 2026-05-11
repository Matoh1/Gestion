<img src="https://media.giphy.com/media/WUlplcMpOCEmTGBtBW/giphy.gif" width="300">

# (👉ﾟヮﾟ)👉 Sistema de Gestión de Residencias e Incidencias

Este proyecto es una API REST construida con Java + Spring Boot, conectada a una base de datos MySQL usando JPA (Java Persistence API) e Hibernate para la persistencia de datos.

La API permite administrar un sistema residencial con regiones, comunas, residencias, usuarios, espacios e incidencias. También permite vincular usuarios a residencias y espacios a residencias, ideal para probar operaciones desde Postman.

Permite manejar operaciones básicas de un servicio web:

- GET (obtener datos)
- POST (crear registros)
- PUT (actualizar registros o vincular recursos)
- PATCH (actualizar parcialmente registros)
- DELETE (eliminar registros)

Todo esto puede ser probado fácilmente con Postman.

## ༼ つ ◕_◕ ༽つ Entorno de desarrollo

🛠️ Editor: Visual Studio Code

Con extensiones como:

- Extension Pack for Java
- Spring Boot Tools
- Maven for Java

🐬 Base de Datos: MySQL mediante Laragon

☕ JDK: Java 21

🌱 Framework: Spring Boot

📦 ORM: Hibernate + JPA

## ヾ(⌐■_■)ノ♪ Funcionalidades implementadas

- Módulo geográfico: gestión de regiones y comunas.
- Módulo residencial: gestión de residencias y su comuna asociada.
- Módulo de usuarios: registro de datos personales y contacto.
- Módulo de asignaciones: vinculación entre usuarios y residencias.
- Módulo de espacios: creación de espacios comunes y vinculación con residencias.
- Módulo de incidencias: reportes, detalle de incidencias y tipos de incidencia.

## (▀̿Ĺ̯▀̿ ̿) Instalación

1. Clona el repositorio:

```bash
git clone https://github.com/SangsterPrime/Gestion.git
```

2. Asegúrate de tener MySQL corriendo en Laragon.

3. Crea la base de datos:

```sql
CREATE DATABASE db_gestion;
```

4. Configura `application.properties` en `src/main/resources`:

```properties
spring.application.name=Gestion
spring.datasource.url=jdbc:mysql://localhost:3306/db_gestion
spring.datasource.username=root
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
```

5. Abre el proyecto en VS Code y ejecuta con el botón "Run" o desde terminal con:

```bash
mvn spring-boot:run
```

## (っ´Ι`)っ Pruebas con Postman

La API corre por defecto en:

```bash
http://localhost:8080/api/v1
```

Algunos endpoints disponibles:

- `GET /api/v1/region`
- `POST /api/v1/region`
- `DELETE /api/v1/region/{id}`
- `GET /api/v1/comuna`
- `POST /api/v1/comuna`
- `PUT /api/v1/comuna/{comunaId}/residencia/{residenciaId}`
- `GET /api/v1/residencia`
- `POST /api/v1/residencia`
- `PATCH /api/v1/residencia/{id}`
- `DELETE /api/v1/residencia/{id}`
- `GET /api/v1/user`
- `POST /api/v1/user`
- `POST /api/v1/user/{userId}/{residenciaId}`
- `GET /api/v1/espacio`
- `POST /api/v1/espacio`
- `POST /api/v1/espacio/{espacioId}/{residenciaId}`
- `GET /api/v1/incidencias`
- `POST /api/v1/incidencias`
- `GET /api/v1/incidencia`
- `POST /api/v1/incidencia`
- `GET /api/v1/tipo_incidencias`
- `POST /api/v1/tipo_incidencias`

## ԅ(¯﹃¯ԅ) Estructura del proyecto

```css
src
|
+---main
|   +---java
|   |   \---com
|   |       \---example
|   |           \---Gestion
|   |               +---controller
|   |               +---DTO
|   |               +---model
|   |               +---repository
|   |               \---service
|   \---resources
|       \---application.properties
\---test
    \---java
        \---com
            \---example
                \---Gestion
```

## ヾ(＠⌒ー⌒＠)ノ Tablas principales

- `Region`
- `Comuna`
- `Residencia`
- `User`
- `Residencias`
- `Espacio`
- `Espacios`
- `Incidencias`
- `Incidencia`
- `Tipo_Incidencia`

## ✍️ Autores

- Matias Alejandro Contreras
- Joel Etienne Sangster
- Matias Alejandro Contreras
- Román Valentino Oliva

Estudiantes de Ingeniería en Informática, apasionados por el backend y el desarrollo con Java y Spring Boot.
