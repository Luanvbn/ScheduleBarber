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
                    authorizeConfig.requestMatchers("/barber/name/{name}").hasAnyRole("BARBER", "ADMIN", "CLIENT");
                    authorizeConfig.requestMatchers("barber/findAllBarber").hasAnyRole("BARBER", "ADMIN", "CLIENT");
                    authorizeConfig.requestMatchers("/barber/id/{id}").hasAnyRole("BARBER", "ADMIN", "CLIENT");
                    authorizeConfig.requestMatchers("/barber/save").hasRole("ADMIN");
                    authorizeConfig.requestMatchers("/barber/update/{id}").hasAnyRole("BARBER", "ADMIN");
                    authorizeConfig.requestMatchers("/barber/delete/{id}").hasRole("ADMIN");
                    authorizeConfig.requestMatchers("/barber/getscheduling/{id}").hasAnyRole("ADMIN", "BARBER");

                    authorizeConfig.requestMatchers("/service/id/{id}").hasAnyRole("BARBER", "ADMIN");
                    authorizeConfig.requestMatchers("/service/save/{id}").hasAnyRole("BARBER", "ADMIN");
                    authorizeConfig.requestMatchers("/service/update/{id}").hasAnyRole("BARBER", "ADMIN");
                    authorizeConfig.requestMatchers("/delete/save/{id}").hasAnyRole("BARBER", "ADMIN");


                    authorizeConfig.requestMatchers("/client/name/{name}").hasAnyRole("BARBER", "ADMIN", "CLIENT");
                    authorizeConfig.requestMatchers("/client/findAllClient").hasAnyRole("BARBER", "ADMIN", "CLIENT");
                    authorizeConfig.requestMatchers("/client/id/{id}").hasAnyRole("BARBER", "ADMIN", "CLIENT");
                    authorizeConfig.requestMatchers("/client/save").hasAnyRole("ADMIN", "BARBER");
                    authorizeConfig.requestMatchers("/client/update/{id}").hasAnyRole("CLIENT", "BARBER");
                    authorizeConfig.requestMatchers("/client/delete/{id}").hasRole("ADMIN");
                    authorizeConfig.requestMatchers("/client/getscheduling/{id}").hasAnyRole("CLIENT", "BARBER");

                    authorizeConfig.requestMatchers("/scheduling/findAllScheduling").hasAnyRole("BARBER", "ADMIN", "CLIENT");
                    authorizeConfig.requestMatchers("/scheduling/id/{id}").hasAnyRole("BARBER", "ADMIN", "CLIENT");
                    authorizeConfig.requestMatchers("/scheduling/createScheduling").hasAnyRole("BARBER", "ADMIN", "CLIENT");
                    authorizeConfig.requestMatchers("/scheduling/update/{id}").hasAnyRole("ADMIN", "BARBER");
                    authorizeConfig.requestMatchers("/scheduling/delete/{id}").hasAnyRole("ADMIN", "BARBER");

                    authorizeConfig.requestMatchers("/access/id/{id}").hasRole("ADMIN");
                    authorizeConfig.requestMatchers("/access/update/{id}").hasRole("ADMIN");
                    authorizeConfig.requestMatchers("/access/delete/{id}").hasRole("ADMIN");

                    authorizeConfig.requestMatchers("/role/id/{id}").hasRole("ADMIN");
                    authorizeConfig.requestMatchers("/role/findAllRoles").hasRole("ADMIN");
                    authorizeConfig.requestMatchers("/role/save").hasRole("ADMIN");
                    authorizeConfig.requestMatchers("/role/update/{id}").hasRole("ADMIN");
                    authorizeConfig.requestMatchers("/role/delete/{id}").hasRole("ADMIN");


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
