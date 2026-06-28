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

1. Clona el repositorio y abre la carpeta en VS Code.

2. Asegúrate de tener MySQL corriendo en Laragon y crea las bases de datos:

```sql
CREATE DATABASE db_residencia_dev;
CREATE DATABASE db_espacios_dev;
CREATE DATABASE db_incidencias_dev;
```

3. Ejecuta el script según tu sistema:

**Windows:**
```bash
.\iniciar-todo.bat
```

**macOS:**
```bash
./iniciar-todo.sh
```

Esto levantará los 5 servicios en orden (Eureka → Gateway → Residencias → Espacios → Incidencias).

## (っ´Ι`)っ Pruebas con Postman

La API corre a través del Gateway en:

```bash
http://localhost:8080
```

Algunos endpoints con sus cuerpos JSON:

**Regiones (V2)**
- `GET /api/v2/region`
- `GET /api/v2/region/{id}`
- `POST /api/v2/region`
```json
{
  "nombreregion": "Región Metropolitana"
}
```
- `PUT /api/v2/region/{regionId}/comuna/{comunaId}`
- `DELETE /api/v2/region/{id}`

**Comunas (V2)**
- `GET /api/v2/comuna`
- `GET /api/v2/comuna/{id}`
- `POST /api/v2/comuna`
```json
{
  "nombrecomuna": "Santiago Centro",
  "region": { "id": 1 }
}
```
- `PUT /api/v2/comuna/{comunaId}/residencia/{residenciaId}`
- `DELETE /api/v2/comuna/{id}`

**Residencias (V2)**
- `GET /api/v2/residencia`
- `GET /api/v2/residencia/{id}`
- `POST /api/v2/residencia`
```json
{
  "nombre": "Residencia Los Olivos",
  "direccion": "Av. Providencia 1234",
  "comuna": { "id": 1 }
}
```
- `PUT /api/v2/residencia/{id}`
- `PATCH /api/v2/residencia/{id}`
- `DELETE /api/v2/residencia/{id}`

**Asignaciones (V2)**
- `GET /api/v2/residencias`
- `GET /api/v2/residencias/usuario/{userId}`
- `POST /api/v2/residencias/residencia/{residenciaId}/usuario/{userId}`
- `DELETE /api/v2/residencias/residencia/{residenciaId}/usuario/{userId}`

**Usuarios**
- `GET /api/v1/user`
- `GET /api/v1/user/{id}`
- `POST /api/v1/user`
```json
{
  "nombre": "Juan",
  "apellido": "Pérez",
  "rut": "12345678-9",
  "email": "juan.perez@email.com",
  "telefono": 987654321,
  "residenciaId": 1
}
```

**Espacios**
- `GET /api/v1/espacio`
- `POST /api/v1/espacio`
```json
{
  "nombre": "Salón Principal",
  "tipo": "Salón de eventos",
  "capacidad": 50
}
```
- `POST /api/v1/espacio/{espacioId}/{residenciaId}`

**Incidencias (reportes)**
- `GET /api/v1/incidencias`
- `POST /api/v1/incidencias`
```json
{
  "residenciaId": 1,
  "tituloReporte": "Fuga de agua en cocina",
  "fechaReporte": "2026-06-28",
  "prioridad": "Alta"
}
```

**Incidencia (detalle)**
- `GET /api/v1/incidencia`
- `POST /api/v1/incidencia`
```json
{
  "incidencias": { "id": 1 },
  "tipoIncidencia": { "id": 1 },
  "descripcion": "El lavaplatos del segundo piso tiene una fuga constante",
  "estado": "Abierto"
}
```

**Tipos de incidencia**
- `GET /api/v1/tipo_incidencias`
- `POST /api/v1/tipo_incidencias`
```json
{
  "nombretipo": "Fontanería"
}
```

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

- **Matías Alejandro Contreras** — `Residencias`
- **Román Valentino Oliva** — `Espacios`
- **Joel Etienne Sangster** — `Incidencias`

Estudiantes de Ingeniería en Informática, apasionados por el backend y el desarrollo con Java y Spring Boot.
