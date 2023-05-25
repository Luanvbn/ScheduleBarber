package br.com.schedulebarber.scheduleBarber.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    //Filtros - Filters
    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
        return http
               // .csrf().disable()
                .authorizeHttpRequests(authorizeConfig -> {
                    authorizeConfig.requestMatchers("/barber/**").permitAll();
                    authorizeConfig.anyRequest().authenticated();
                })
                .build();
    }

}
