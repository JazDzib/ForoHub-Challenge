package com.AluraLatam.ForoHub.domain.topico;

import com.AluraLatam.ForoHub.domain.autor.Autor;
import com.AluraLatam.ForoHub.domain.autor.DatosRespuestaAutor;

public record DatosRespuestaTopico(
        Long id,
        String titulo,
        String mensaje,
        String fechaCreacion,
        Boolean estado,
        String curso,
        DatosRespuestaAutor autor
) {
    public DatosRespuestaTopico(Topico topico){
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getEstado(),
                topico.getCurso(),
                new DatosRespuestaAutor(topico.getAutor()));
    }



}
