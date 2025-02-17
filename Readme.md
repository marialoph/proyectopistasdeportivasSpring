# API Rest con Spring

**Gestión de instalaciones deportivas con una API Spring REST y React.**

## Diseño de la aplicación

En este enlace puedes [ver cómo hemos diseñado la aplicación](https://youtu.be/LaFSrDjFm1A).

En este enlace puedes ver el [front-end](https://gitlab.iesvirgendelcarmen.com/juangu/adt06-proyectoclasepistasdeportivas-front-react).

## Explicación JWT y UserDetails

TO-DO: Explicar

## Documentación del Servicio `ServiInstalacion`

Este servicio RESTful en **Spring Boot** permite gestionar recursos relacionados con las instalaciones deportivas a través de operaciones CRUD. 

### Endpoints

| Método HTTP | Endpoint                  | Descripción                                                                                             | Ejemplo de Respuesta |
|-------------|---------------------------|---------------------------------------------------------------------------------------------------------|-----------------------|
| `GET`       | `/api/instalacion`        | Obtiene una lista de todas las instalaciones deportivas disponibles.                                    | Lista de JSON         |
| `GET`       | `/api/instalacion/{id}`   | Obtiene una instalación específica por su ID. Si no existe, retorna un error `404 Not Found`.           | Objeto JSON o `404`   |
| `POST`      | `/api/admin/instalacion`        | Crea una nueva instalación deportiva. Retorna el objeto creado y la URI en el encabezado `Location`.   | Objeto JSON           |
| `PUT`       | `/api/admin/instalacion/{id}`   | Actualiza una instalación específica por su ID. Retorna el objeto actualizado o un error `404 Not Found` si no existe. | Objeto JSON o `404` |
| `DELETE`    | `/api/admin/instalacion/{id}`   | Elimina una instalación específica por su ID. Retorna el objeto eliminado o un error `404 Not Found`.  | Objeto JSON o `404`   |

---

### Implementación de Endpoints

#### 1. **GET /api/instalacion**
- **Descripción**: Retorna una lista de todas las instalaciones.
- **Respuesta**:
  ```json
  [
    {
      "id": 1,
      "nombre": "Cancha de Fútbol"
    },
    {
      "id": 2,
      "nombre": "Piscina Olímpica"
    }
  ]
  ```

---

#### 2. **GET /api/instalacion/{id}**
- **Descripción**: Busca una instalación por su `id`.
- **Parámetros**:
  - `id` (PathVariable): Identificador único de la instalación.
- **Respuesta Exitosa (`200 OK`)**:
  ```json
  {
    "id": 1,
    "nombre": "Cancha de Fútbol"
  }
  ```
- **Respuesta Error (`404 Not Found`)**: 
  ```json
  {}
  ```

---

#### 3. **POST /api/instalacion**
- **Descripción**: Crea una nueva instalación deportiva.
- **Cuerpo de la Solicitud**:
  ```json
  {
    "nombre": "Cancha de Tenis"
  }
  ```
- **Respuesta Exitosa (`201 Created`)**:
  - Encabezado `Location`: `/api/instalacion/{id}`
  - Cuerpo:
    ```json
    {
      "id": 3,
      "nombre": "Cancha de Tenis"
    }
    ```

---

#### 4. **PUT /api/instalacion/{id}**
- **Descripción**: Actualiza los detalles de una instalación existente.
- **Parámetros**:
  - `id` (PathVariable): Identificador único de la instalación.
- **Cuerpo de la Solicitud**:
  ```json
  {
    "nombre": "Nueva Cancha de Fútbol"
  }
  ```
- **Respuesta Exitosa (`200 OK`)**:
  ```json
  {
    "id": 1,
    "nombre": "Nueva Cancha de Fútbol"
  }
  ```
- **Respuesta Error (`404 Not Found`)**: 
  ```json
  {}
  ```

---

#### 5. **DELETE /api/instalacion/{id}**
- **Descripción**: Elimina una instalación específica.
- **Parámetros**:
  - `id` (PathVariable): Identificador único de la instalación.
- **Respuesta Exitosa (`200 OK`)**:
  ```json
  {
    "id": 1,
    "nombre": "Cancha de Fútbol"
  }
  ```
- **Respuesta Error (`404 Not Found`)**: 
  ```json
  {}
  ```

---

## Notas Técnicas
- **Clase Principal**: `ServiInstalacion`
- **Repositorio Utilizado**: `RepoInstalacion`
- **Modelo de Datos**: `Instalacion`
  - Campos esperados en el modelo:
    - `id`: Identificador único (Long).
    - `nombre`: Nombre de la instalación (String).

Este servicio está diseñado siguiendo las mejores prácticas REST y utiliza respuestas estándar HTTP (`200 OK`, `201 Created`, `404 Not Found`, `409 Conflict`). 


## Servicio `mis-datos`

Este servicio RESTful en **Spring Boot** permite gestionar nuestros datos a través de operaciones CRUD. 

### Endpoints

| Método HTTP | Endpoint                  | Descripción                                                                                             | Ejemplo de Respuesta |
|-------------|---------------------------|---------------------------------------------------------------------------------------------------------|-----------------------|
| `GET`       | `/api/usuario`        | Obtiene la información del usuario que ha hecho login (JWT).                                    | Lista de JSON         |
| `POST`      | `/api/usuario`        | Método no permitido.   | Código HTTP `405`.
| `PUT`      | `/api/usuario`        | Actualiza los datos del usuario que ha hecho login. Retorna el objeto modificado y la URI en el encabezado `Location`.   | Objeto JSON           |
| `DELETE`    | `/api/usuario`   | Método no permitido.  | Código `405`   |

---

## Servicio `ServiUsuarios`

Este servicio RESTful en **Spring Boot** permite gestionar recursos relacionados con las instalaciones deportivas a través de operaciones CRUD. 

### Endpoints

| Método HTTP | Endpoint                  | Descripción                                                                                             | Ejemplo de Respuesta |
|-------------|---------------------------|---------------------------------------------------------------------------------------------------------|-----------------------|
| `GET`       | `/api/admin/usuario`        | Obtiene una lista de todos los usuarios disponibles.                                    | Lista de JSON         |
| `GET`       | `/api/admin/usuario/{id}`   | Obtiene un ususariopor su ID. Si no existe, retorna un error `404 Not Found`.           | Objeto JSON o `404`   |
| `POST`      | `/api/admin/usuario`        | Crea una nuevo usuario. Retorna el objeto creado y la URI en el encabezado `Location`.   | Objeto JSON           |
| `PUT`       | `/api/admin/usuario/{id}`   | Actualiza un usuario específico por su ID. Retorna el objeto actualizado o un error `404 Not Found` si no existe. | Objeto JSON o `404` |
| `DELETE`    | `/api/admin/usuario/{id}`   | Elimina un usuario específico por su ID. Retorna el objeto eliminado o un error `404 Not Found`.  | Objeto JSON o `404`   |







## Documentación del Servicio `mis-reservas`

Este servicio RESTful en **Spring Boot** permite gestionar recursos relacionados con las instalaciones deportivas a través de operaciones CRUD. 

### Endpoints

| Método HTTP | Endpoint                  | Descripción                                                                                             | Ejemplo de Respuesta |
|-------------|---------------------------|---------------------------------------------------------------------------------------------------------|-----------------------|
| `GET`       | `/api/mis-reservas`        | Obtiene una lista de reservas de ese usuario (el que hace login).                                    | Lista de JSON         |
| `GET`       | `/api/mis-reservas/{id}`   | Obtiene el detalle de una de mis reservas. Si no existe o es de otro usuario, retorna un error `404 Not Found`.           | Objeto JSON o `404`   |
| `POST`      | `/api/mis-reservas`        | Crea una nueva reserva para una instalación deportiva a nombre del usuario que hizo login. Retorna el objeto creado y la URI en el encabezado `Location`.   | Objeto JSON           |
| `PUT`       | `/api/mis-reservas/{id}`   | Actualiza una reserva específica por su ID. Retorna el objeto actualizado o un error `404 Not Found` si no existe. | Objeto JSON o `404` |
| `DELETE`    | `/api/mis-reservas/{id}`   | Elimina una reserva específica por su ID. Retorna el objeto eliminado o un error `404 Not Found`.  | Objeto JSON o `404`   |
| `GET`       | `/api/mis-reservas/horario/instalacion/{id}/fecha/{fecha}`        | Obtiene una lista de horarios disponibles de una instalacion en una fecha. | Lista de JSON         |
