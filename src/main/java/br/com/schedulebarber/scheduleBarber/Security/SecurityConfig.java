package br.com.schedulebarber.scheduleBarber.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig{


   // Filtros - Filtros de permissões para acesso
    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .authorizeHttpRequests(authorizeConfig -> {
                    authorizeConfig.requestMatchers("/barber/**").permitAll();
                    authorizeConfig.requestMatchers("/client/**").permitAll();
                    authorizeConfig.requestMatchers("/access/**").permitAll();
                    authorizeConfig.anyRequest().authenticated();
                })
                .build();
    }

}
