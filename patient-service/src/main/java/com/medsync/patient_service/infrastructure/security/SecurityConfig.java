package com.medsync.patient_service.infrastructure.security;

import com.medsync.security.JwtAuthenticationFilter;
import com.medsync.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth
                        // 1) Public endpoints - Just to check if the microservice is up and running
                        .requestMatchers( "/ping").permitAll()

                        // 2) PATIENT-only endpoints (these still use hasRole, which is fine)
                        .requestMatchers(HttpMethod.POST, "/patients").hasRole("PATIENT")
                        .requestMatchers(HttpMethod.GET, "/patients/me").hasRole("PATIENT")
                        .requestMatchers(HttpMethod.PUT, "/patients/me").hasRole("PATIENT")

                        // 3) ADMIN-only endpoints — use hasAnyAuthority with full ROLE_ prefix
                        .requestMatchers(HttpMethod.GET, "/patients").hasAnyAuthority("ROLE_CLINIC_ADMIN", "ROLE_SYSTEM_ADMIN")
                        .requestMatchers(HttpMethod.GET, "/patients/*").hasAnyAuthority("ROLE_CLINIC_ADMIN", "ROLE_SYSTEM_ADMIN")

                        // 4) Everything else needs to be authenticated
                        .anyRequest().authenticated()
                )

                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtTokenProvider);
    }
}
