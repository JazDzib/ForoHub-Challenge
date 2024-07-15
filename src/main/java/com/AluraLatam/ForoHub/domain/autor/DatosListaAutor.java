package com.AluraLatam.ForoHub.domain.autor;

public record DatosListaAutor (Long id, String nombre, String correo){

    public DatosListaAutor(Autor autor){
        this(autor.getId(),autor.getNombre(),autor.getCorreo());
    }

}
