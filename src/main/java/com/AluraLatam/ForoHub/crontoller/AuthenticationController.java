package com.AluraLatam.ForoHub.crontoller;

import com.AluraLatam.ForoHub.domain.autor.Autor;
import com.AluraLatam.ForoHub.domain.autor.DatosAutenticacionAutor;
import com.AluraLatam.ForoHub.infra.seguridad.DatosJWTToken;
import com.AluraLatam.ForoHub.infra.seguridad.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;
    @PostMapping
    public ResponseEntity autenticacionUsuario(@RequestBody @Valid DatosAutenticacionAutor datosAutenticarAutor){
        Authentication authtoken= new UsernamePasswordAuthenticationToken(datosAutenticarAutor.correo(),datosAutenticarAutor.contrasena());
        var usuarioAutenticado=authenticationManager.authenticate(authtoken);
        var jwtToken = tokenService.generaToken((Autor) usuarioAutenticado.getPrincipal());
        return ResponseEntity.ok(new DatosJWTToken(jwtToken));
    }

}
