package com.AluraLatam.ForoHub.domain.autor;

public record DatosRespuestaAutor(
        Long id,
        String nombre,
        String correo

) {

    public DatosRespuestaAutor(Autor autor){
        this(autor.getId(), autor.getNombre(), autor.getCorreo());
    }
}
