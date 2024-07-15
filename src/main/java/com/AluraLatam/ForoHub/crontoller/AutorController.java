package com.AluraLatam.ForoHub.crontoller;

import com.AluraLatam.ForoHub.domain.autor.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/autores")
public class AutorController {
@Autowired
    private AutorRepository autorRepository;


    @PostMapping
    public ResponseEntity<DatosRespuestaAutor> registrarAutor(@RequestBody @Valid DatosRegistroAutor datoRegistroAutor, UriComponentsBuilder uriComponentsBuilder){
        Autor autor= autorRepository.save(new Autor(datoRegistroAutor));
        //return  ResponseEntity.ok(new DatosRespuestaAutor(autor));
        URI url = uriComponentsBuilder.path("/autores/{id}").buildAndExpand(autor.getId()).toUri();
        return ResponseEntity.created(url).body(new DatosRespuestaAutor(autor));
    }

    @GetMapping
    public ResponseEntity <Page<DatosListaAutor>> listaAutores(Pageable pageable) {
        return ResponseEntity.ok(autorRepository.findAll(pageable).map(DatosListaAutor::new));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity eliminarAutor (@PathVariable Long id){
        Autor autor = autorRepository.getReferenceById(id);
        autorRepository.delete(autor);
        return ResponseEntity.noContent().build();
    }
    @PutMapping
    @Transactional
    public  ResponseEntity<DatosRespuestaAutor> actualizarAutor(@RequestBody @Valid DatosActualizarAutor datosActualizarAutor){
        Autor autor= autorRepository.getReferenceById(datosActualizarAutor.id());
        autor.actualizarDatos(datosActualizarAutor);

        return ResponseEntity.ok(new DatosRespuestaAutor(autor));
    }
    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaAutor> retornaAutor(@PathVariable Long id){
        Autor autor = autorRepository.getReferenceById(id);
        var datoAutor = new DatosRespuestaAutor(autor);
        return ResponseEntity.ok(datoAutor);
    }





}
