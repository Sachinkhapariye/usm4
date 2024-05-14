package com.usm4.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration  // configuration class are the one that run first befor the project code run first because
                // first is configuration and running the project
                //** so any file mark with configuration run first
public class SecurityConfig {

    //here coming http incoming request sabse pahale api ki request iske pass ayegi permit karega nahi karega
    //in config file i can controlling which url can access by user and admin

    private  JWTResponseFilter jwtResponseFilter;

    public SecurityConfig(JWTResponseFilter jwtResponseFilter) {
        this.jwtResponseFilter = jwtResponseFilter;
    }

    @Bean // create oject and this objectt details goes to spring boot ioc
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)throws Exception{
        httpSecurity.csrf().disable().cors().disable(); // url not be access on diffrent platform stop the attacker
        httpSecurity.addFilterBefore(jwtResponseFilter, AuthorizationFilter.class);

        httpSecurity.authorizeHttpRequests()
//                .requestMatchers("/api/v1/auth/{addUser}","/api/v1/auth/login")
//                .permitAll()
//                .requestMatchers("/api/v1/countries/addCountry").hasRole("ADMIN")
//                .requestMatchers("/api/v1/auth/profile").hasAnyRole("ADMIN","USER")
//                .anyRequest().authenticated();

                .anyRequest().permitAll();
        return httpSecurity.build();
    }

}
