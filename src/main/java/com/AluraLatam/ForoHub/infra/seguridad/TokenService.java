package com.AluraLatam.ForoHub.infra.seguridad;

import com.AluraLatam.ForoHub.domain.autor.Autor;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    public String generaToken(Autor autor){
        try {
            Algorithm algorithm = Algorithm.HMAC256("123456");//no deberia poner la informacion pero noi deja de otra forma
            return  JWT.create()
                    .withIssuer("voll med")
                    .withSubject(autor.getCorreo())
                    .withClaim("id",autor.getId())//para quien esta siendo generado el token
                    .withExpiresAt(generarFechaExpiracion())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException();
        }
    }

    public String getSubject(String token) {
        if (token == null){
            throw new RuntimeException();
        }

        DecodedJWT verifier = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256("123456"); //validando la firma
            verifier = JWT.require(algorithm)
                    .withIssuer("voll med")
                    .build().verify(token);
            verifier.getSubject();
        } catch (JWTVerificationException exception) {
            // Invalid signature/claims
            System.out.println(exception.toString());
        }
        if (verifier.getSubject() == null){
            throw  new RuntimeException("Verifier invalido");
        }
        return verifier.getSubject();
    }

    private Instant generarFechaExpiracion(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-05:00"));
    }

}
