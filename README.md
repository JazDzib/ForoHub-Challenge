# ForoHub

## Descripción
ForoHub es una API REST para gestionar un foro donde los usuarios pueden plantear determinados tópicos. La API permite crear, mostrar, actualizar y eliminar tópicos, siguiendo las mejores prácticas del modelo REST y utilizando diversas tecnologías.

## Tecnologías Utilizadas
- **Java**
- **Spring Boot**
- **Lombok**
- **Flyway**
- **Auth0 con JWT**
- **Spring Security**
- **Postgres**
- **Insomnia** (para pruebas de API)



## Uso
### Autenticación
Implementa mecanismos de autenticación en la API para que los usuarios puedan autenticarse y enviar solicitudes a ella.
### Validaciones
Para las validaciones se generan tokens de jwt para poder realizar la validacion de los usuarios.

### Ejemplos de Uso
- **Crear un nuevo tópico**
    ```http
    POST /topicos
    {
        "titulo": "¿Cómo configuro Spring Security?",
        "mensaje": "Necesito ayuda para configurar Spring Security en mi proyecto.",
        "autorId": 1
    }
    ```

- **Mostrar todos los tópicos creados**
    ```http
    GET /topicos
    ```

- **Mostrar un tópico específico**
    ```http
    GET /topicos/{id}
    ```

- **Actualizar un tópico**
    ```http
    PUT /topicos/{id}
    {
        "titulo": "¿Cómo configuro Spring Security? [Actualizado]",
        "mensaje": "He encontrado algunos problemas adicionales con la configuración."
    }
    ```

- **Eliminar un tópico**
    ```http
    DELETE /topics/{id}
    ```




