# Proyecto Base Implementando Clean Architecture

## Antes de Iniciar

Empezaremos por explicar los diferentes componentes del proyectos y partiremos de los componentes externos, continuando con los componentes core de negocio (dominio) y por �ltimo el inicio y configuraci�n de la aplicaci�n.

Lee el art�culo [Clean Architecture � Aislando los detalles](https://medium.com/bancolombia-tech/clean-architecture-aislando-los-detalles-4f9530f35d7a)

# Arquitectura

![Clean Architecture](https://miro.medium.com/max/1400/1*ZdlHz8B0-qu9Y-QO3AXR_w.png)

## Domain

Es el m�dulo m�s interno de la arquitectura, pertenece a la capa del dominio y encapsula la l�gica y reglas del negocio mediante modelos y entidades del dominio.

## Usecases

Este m�dulo gradle perteneciente a la capa del dominio, implementa los casos de uso del sistema, define l�gica de aplicaci�n y reacciona a las invocaciones desde el m�dulo de entry points, orquestando los flujos hacia el m�dulo de entities.

## Infrastructure

### Helpers

En el apartado de helpers tendremos utilidades generales para los Driven Adapters y Entry Points.

Estas utilidades no est�n arraigadas a objetos concretos, se realiza el uso de generics para modelar comportamientos
gen�ricos de los diferentes objetos de persistencia que puedan existir, este tipo de implementaciones se realizan
basadas en el patr�n de dise�o [Unit of Work y Repository](https://medium.com/@krzychukosobudzki/repository-design-pattern-bc490b256006)

Estas clases no puede existir solas y debe heredarse su compartimiento en los **Driven Adapters**

### Driven Adapters

Los driven adapter representan implementaciones externas a nuestro sistema, como lo son conexiones a servicios rest,
soap, bases de datos, lectura de archivos planos, y en concreto cualquier origen y fuente de datos con la que debamos
interactuar.

### Entry Points

Los entry points representan los puntos de entrada de la aplicaci�n o el inicio de los flujos de negocio.

## Application

Este m�dulo es el m�s externo de la arquitectura, es el encargado de ensamblar los distintos m�dulos, resolver las dependencias y crear los beans de los casos de use (UseCases) de forma autom�tica, inyectando en �stos instancias concretas de las dependencias declaradas. Adem�s inicia la aplicaci�n (es el �nico m�dulo del proyecto donde encontraremos la funci�n �public static void main(String[] args)�.

**Los beans de los casos de uso se disponibilizan automaticamente gracias a un '@ComponentScan' ubicado en esta capa.**

# Microservicio de Autenticación

Microservicio para gestión de registro de usuarios desarrollado con **Spring WebFlux** siguiendo principios de **Clean Architecture** y **Arquitectura Hexagonal**.

## 🏗️ Arquitectura

Este proyecto implementa **Clean Architecture** con los siguientes principios:
- **SOLID**: Separación de responsabilidades y inversión de dependencias
- **Arquitectura Hexagonal**: Dominio independiente de frameworks e infraestructura
- **Programación Reactiva**: Uso de Spring WebFlux con Reactor
- **Clean Code**: Código limpio y mantenible

### Estructura del Proyecto

```
├── domain/                          # Capa de Dominio (Reglas de Negocio)
│   ├── model/                       # Entidades y excepciones de dominio
│   │   └── src/main/java/co/com/msautenticacion/model/
│   │       ├── user/
│   │       │   ├── User.java        # Entidad de dominio (inmutable)
│   │       │   └── gateways/UserRepository.java
│   │       └── exception/
│   │           ├── InvalidUserDataException.java
│   │           └── EmailAlreadyExistsException.java
│   └── usecase/                     # Casos de uso
│       └── src/main/java/co/com/msautenticacion/usecase/
│           └── RegisterUserUseCase.java
├── infrastructure/                  # Capa de Infraestructura
│   ├── entry-points/               # Puertos de entrada
│   │   └── reactive-web/           # API REST reactiva
│   │       └── src/main/java/co/com/msautenticacion/api/
│   │           ├── controller/UserController.java
│   │           ├── dto/            # DTOs para entrada/salida
│   │           └── exception/GlobalExceptionHandler.java
│   └── driven-adapters/            # Adaptadores de salida
│       └── r2dbc-postgresql/       # Persistencia reactiva con R2DBC
│           └── src/main/java/co/com/msautenticacion/r2dbc/
│               ├── UserRepositoryAdapter.java
│               ├── entity/UserEntity.java
│               └── repository/UserDataRepository.java
└── applications/
    └── app-service/                # Configuración de la aplicación
```

## 🚀 Características

- ✅ **Registro de usuarios** con validaciones de negocio
- ✅ **Validación de emails únicos**
- ✅ **Programación reactiva** con Spring WebFlux
- ✅ **Base de datos PostgreSQL** con R2DBC
- ✅ **Manejo centralizado de excepciones**
- ✅ **Documentación API** con OpenAPI/Swagger
- ✅ **Logs de trazabilidad**
- ✅ **Validaciones de entrada** con Bean Validation

## 🛠️ Tecnologías

| Tecnología | Versión | Propósito |
|------------|---------|-----------|
| Java | 17 | Lenguaje de programación |
| Spring Boot | 3.5.4 | Framework principal |
| Spring WebFlux | 3.5.4 | Programación reactiva |
| R2DBC PostgreSQL | - | Driver reactivo de base de datos |
| Gradle | 8.x | Herramienta de construcción |
| Lombok | 1.18.38 | Reducción de boilerplate |
| Bean Validation | 3.0 | Validación de datos |

## ⚙️ Instalación y Configuración

### Prerrequisitos

- **JDK 17** o superior
- **PostgreSQL 12** o superior
- **Gradle 8.x** (incluido con wrapper)

### 1. Clonar el repositorio

```bash
git clone <repository-url>
cd pragma_crediya_autenticacion_ms
```

### 2. Configurar Base de Datos

Crear base de datos en PostgreSQL:

```sql
-- Conectarse a PostgreSQL como superusuario
CREATE DATABASE autenticacion_db;
CREATE USER test WITH PASSWORD 'test';
GRANT ALL PRIVILEGES ON DATABASE autenticacion_db TO test;

-- Conectarse a autenticacion_db
\c autenticacion_db;

-- Crear tabla de usuarios
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    birth_date DATE,
    phone_number VARCHAR(15),
    email VARCHAR(100) NOT NULL UNIQUE,
    direction_address VARCHAR(200),
    salary DECIMAL(12,2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Otorgar permisos al usuario
GRANT ALL PRIVILEGES ON TABLE users TO test;
GRANT USAGE, SELECT ON SEQUENCE users_id_seq TO test;
```

### 3. Configurar aplicación

El archivo `application.yaml` ya está configurado para PostgreSQL local:

```yaml
server:
  port: 8080

adapters:
  r2dbc:
    host: localhost
    port: 5432
    database: autenticacion_db
    schema: public
    username: test
    password: test
```

### 4. Ejecutar la aplicación

```bash
# Construcción
./gradlew build

# Ejecutar
./gradlew bootRun
```

La aplicación estará disponible en: `http://localhost:8080`

## 📚 Documentación de API

### Swagger UI
- URL: `http://localhost:8080/swagger-ui.html`
- OpenAPI Docs: `http://localhost:8080/v3/api-docs`

### Endpoints Disponibles

#### POST /api/v1/usuarios
Registra un nuevo usuario en el sistema.

**Request Body:**
```json
{
  "name": "Juan Carlos",
  "lastName": "Pérez García",
  "birthDate": "1990-05-15",
  "phoneNumber": "3001234567",
  "email": "juan.perez@email.com",
  "directionAddress": "Calle 123 #45-67, Bogotá",
  "salary": 2500000.00
}
```

**Response (201 Created):**
```json
{
  "id": "1",
  "name": "Juan Carlos",
  "lastName": "Pérez García", 
  "birthDate": "1990-05-15",
  "phoneNumber": "3001234567",
  "email": "juan.perez@email.com",
  "directionAddress": "Calle 123 #45-67, Bogotá",
  "salary": 2500000.00
}
```

**Validaciones aplicadas:**
- `name`: Requerido, entre 2-50 caracteres
- `lastName`: Requerido, entre 2-50 caracteres  
- `email`: Requerido, formato válido, único en el sistema
- `birthDate`: Requerida, fecha pasada
- `phoneNumber`: Requerido, 10 dígitos numéricos
- `directionAddress`: Requerida, entre 5-200 caracteres
- `salary`: Requerido, entre 0.01 y 15,000,000

### Respuestas de Error

#### 400 Bad Request - Datos Inválidos
```json
{
  "timestamp": "2024-01-15T10:30:00.000Z",
  "status": 400,
  "error": "Datos Inválidos",
  "message": "El email debe tener un formato válido",
  "path": "/api/v1/usuarios"
}
```

#### 409 Conflict - Email Duplicado
```json
{
  "timestamp": "2024-01-15T10:30:00.000Z", 
  "status": 409,
  "error": "Conflicto",
  "message": "El email ya está registrado en el sistema",
  "path": "/api/v1/usuarios"
}
```

#### 500 Internal Server Error
```json
{
  "timestamp": "2024-01-15T10:30:00.000Z",
  "status": 500,
  "error": "Error Interno del Servidor",
  "message": "Ocurrió un error inesperado. Por favor, inténtelo de nuevo más tarde.",
  "path": "/api/v1/usuarios"
}
```

## 🧪 Pruebas

### Ejemplo con cURL

```bash
curl -X POST http://localhost:8080/api/v1/usuarios \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Juan Carlos",
    "lastName": "Pérez García",
    "birthDate": "1990-05-15", 
    "phoneNumber": "3001234567",
    "email": "juan.perez@email.com",
    "directionAddress": "Calle 123 #45-67, Bogotá",
    "salary": 2500000.00
  }'
```

### Ejemplo con Postman

1. **URL**: `POST http://localhost:8080/api/v1/usuarios`
2. **Headers**: `Content-Type: application/json`
3. **Body**: Raw JSON (ver ejemplo anterior)

## 🏛️ Principios de Clean Architecture Aplicados

### Capa de Dominio (domain/)
- **Entidades**: `User` - Modelo de dominio inmutable con validaciones de negocio
- **Casos de Uso**: `RegisterUserUseCase` - Lógica de negocio pura
- **Interfaces**: `UserRepository` - Contratos sin dependencias externas
- **Excepciones**: Excepciones específicas del dominio

### Capa de Infraestructura (infrastructure/)
- **Entry Points**: Controllers REST reactivos
- **Driven Adapters**: Implementación de persistencia con R2DBC
- **Configuración**: Beans de Spring y configuración de base de datos

### Beneficios Implementados
- ✅ **Independencia de frameworks**: El dominio no depende de Spring
- ✅ **Testabilidad**: Casos de uso fáciles de probar unitariamente  
- ✅ **Mantenibilidad**: Separación clara de responsabilidades
- ✅ **Escalabilidad**: Arquitectura preparada para crecer

## 📋 Health Check

Verificar estado de la aplicación:

```bash
curl http://localhost:8080/actuator/health
```

Response:
```json
{
  "status": "UP"
}
```

## 🐛 Solución de Problemas

### Error de conexión a base de datos
1. Verificar que PostgreSQL esté ejecutándose
2. Confirmar credenciales en `application.yaml`
3. Verificar que la base de datos `autenticacion_db` exista

### Error de tabla no encontrada
1. Ejecutar scripts SQL de creación de tablas
2. Verificar permisos del usuario `test`

### Puerto 8080 ocupado
Cambiar puerto en `application.yaml`:
```yaml
server:
  port: 8081
```

## 📝 Logs

Los logs están configurados para mostrar información de depuración:

```yaml
logging:
  level:
    co.com.msautenticacion: DEBUG
    org.springframework.r2dbc: DEBUG
```

## 🤝 Contribución

1. Fork del repositorio
2. Crear rama feature (`git checkout -b feature/nueva-funcionalidad`)
3. Commit cambios (`git commit -am 'Agregar nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)  
5. Crear Pull Request

## 📄 Licencia

Este proyecto está bajo la licencia [MIT](LICENSE).

---

**Autor**: Cristian Martinez  
**Versión**: 1.0.0  
**Fecha**: 2025
