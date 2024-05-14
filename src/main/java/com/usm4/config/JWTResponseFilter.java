


package com.usm4.config;


import com.usm4.entity.AppUser;
import com.usm4.repository.AppUserRepository;
import com.usm4.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

//ise us time chalana hai jab authenticated url request aaye
@Component // this annotation handover the class to spring boot and spring boot creates its bean execute bean and destroy bean
public class JWTResponseFilter extends OncePerRequestFilter { //here direct com unauthenticate url and authorizzed here

    private JWTService jwtService;
    private AppUserRepository appUserRepository;

    public JWTResponseFilter(JWTService jwtService, AppUserRepository appUserRepository) {
        this.jwtService = jwtService;
        this.appUserRepository = appUserRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenHeader = request.getHeader("Authorization");
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")){
            String token = tokenHeader.substring(8, tokenHeader.length() - 1);
            String userName = jwtService.getUserName(token);

            //To Track current user logged in

            Optional<AppUser> byUsername = appUserRepository.findByUsername(userName);
            if (byUsername.isPresent()){
                AppUser appUser = byUsername.get();
                //To Track current user logged in

                UsernamePasswordAuthenticationToken authenticationToken
                        = new UsernamePasswordAuthenticationToken(appUser,null, Collections.singleton(new SimpleGrantedAuthority(appUser.getUserRole())));

                authenticationToken.setDetails(new WebAuthenticationDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken); //it store session id of the user in the table form
            }
        }

        filterChain.doFilter(request,response);

    }
}

