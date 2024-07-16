package com.AluraLatam.ForoHub.infra.seguridad;

import com.AluraLatam.ForoHub.domain.autor.AutorRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
 @Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private AutorRepository autorRepository;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //obtener token
        var authorizationHeader = request.getHeader("Authorization");//le da autorixacion al token
        if (authorizationHeader != null){
            var token = authorizationHeader.replace("Bearer", "").trim();//el trim es importante para que jale
            var nombreUsuario= tokenService.getSubject(token);
            if (nombreUsuario != null){
                //token valido
                var usuario = autorRepository.findByCorreo(nombreUsuario);
                var autenticacion = new UsernamePasswordAuthenticationToken(usuario,null,
                        usuario.getAuthorities());//Forzamos un inicio de sesion
                SecurityContextHolder.getContext().setAuthentication(autenticacion);
            }
            System.out.println(tokenService.getSubject(token));
        }
        filterChain.doFilter(request,response);
    }
}
