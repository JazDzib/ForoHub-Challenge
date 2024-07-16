package com.AluraLatam.ForoHub.infra.errores;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

import static org.aspectj.weaver.bcel.asm.AsmDetector.rootCause;

@RestControllerAdvice
public class TratadorErrores {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratar404(){
        return ResponseEntity.notFound().build();
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarError400 (MethodArgumentNotValidException e){
        // se usa un fielderrors porque debe devolver la lista de los errores que se generaron Y mapeamos el record que creamos para que muestre los unicos parametros deseados
        var errores = e.getFieldErrors().stream().map(DatosErrorValidacion::new);
        return ResponseEntity.badRequest().body(errores);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> errorIntegridadDatosSql(DataIntegrityViolationException e){
        String errorMessage = "Unknown data integrity violation";
        String errorType = "DATA_INTEGRITY_VIOLATION";

        Throwable rootCause = e.getRootCause();
        if (rootCause instanceof SQLException) {
            SQLException sqlException = (SQLException) rootCause;
            String sqlState = sqlException.getSQLState();

            if ("23505".equals(sqlState)) { // PostgreSQL unique violation SQL state
                errorMessage = "Algunos datos ya fueron registrados anteriormente";
                if (sqlException.getMessage().contains("unique_titulo")) {
                    errorMessage = "El Titulo ya existe";
                } else if (sqlException.getMessage().contains("unique_mensaje")) {
                    errorMessage = "El mensaje ya existe";
                }
                errorType = "UNIQUE_CONSTRAINT_VIOLATION";
            } else if ("23503".equals(sqlState)) { // PostgreSQL foreign key violation SQL state
                errorMessage = "Autor no encontrado";
                errorType = "FOREIGN_KEY_VIOLATION";
            }
        }
        return ResponseEntity.badRequest().body("{\nError"+ errorMessage + "\nTipo error"+ errorType+"\n");
    }





    public record DatosErrorValidacion(String campo, String error){
        public DatosErrorValidacion(FieldError error){
            //esta mapenado los mensajes que deben salir en este caso el error generado(getfield) y el mensaje del porque(getdefauldmensage)
            this(error.getField(), error.getDefaultMessage());
        }

    }

}