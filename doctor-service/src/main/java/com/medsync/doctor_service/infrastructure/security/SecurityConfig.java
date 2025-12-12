package com.medsync.doctor_service.infrastructure.security;

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
                // Stateless, JWT-based
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth

                        // 1) Public endpoints
                        .requestMatchers("/actuator/health", "/ping").permitAll()
                        // Browse doctors publicly (for now)
                        .requestMatchers(HttpMethod.GET, "/doctors", "/doctors/*").permitAll()

                        // 2) DOCTOR-only endpoints (manage own profile)
                        .requestMatchers(HttpMethod.POST, "/doctors").hasRole("DOCTOR")
                        .requestMatchers(HttpMethod.GET, "/doctors/me").hasRole("DOCTOR")
                        .requestMatchers(HttpMethod.PUT, "/doctors/me").hasRole("DOCTOR")

                        // 3) Admin-only endpoints (if later you make them private)
                        .requestMatchers(HttpMethod.GET, "/doctors").hasAnyRole("CLINIC_ADMIN", "SYSTEM_ADMIN")
                        .requestMatchers(HttpMethod.GET, "/doctors/*").hasAnyRole("CLINIC_ADMIN", "SYSTEM_ADMIN")

                        // 4) Any other endpoint must be authenticated at least
                        .anyRequest().authenticated()
                )

                // 5) Plug in JWT filter BEFORE UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtTokenProvider);
    }
}
