package br.com.schedulebarber.scheduleBarber.Security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationProvider;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    private final JwtAuthenticationFilter jwtAuthFilter;



    private final AuthenticationProvider authenticationProvider;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter, AuthenticationProvider authenticationProvider) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.authenticationProvider = authenticationProvider;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
        .authorizeHttpRequests(authorizeConfig -> {
            authorizeConfig.requestMatchers("/auth/**").permitAll();
            authorizeConfig.requestMatchers("/barber/id/**").hasAnyRole("BARBER", "ADMIN");
            authorizeConfig.requestMatchers("/barber/save").hasRole("ADMIN");
            authorizeConfig.requestMatchers("/barber/update/**").hasAnyRole("BARBER", "ADMIN");
            authorizeConfig.requestMatchers("/barber/delete/**").hasRole("ADMIN");
            authorizeConfig.anyRequest().authenticated();
        })
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }







}
