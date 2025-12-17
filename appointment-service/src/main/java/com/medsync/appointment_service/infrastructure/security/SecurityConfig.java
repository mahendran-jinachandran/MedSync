package com.medsync.appointment_service.infrastructure.security;

import com.medsync.security.JwtAuthenticationFilter;
import com.medsync.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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

                        // Public
                        .requestMatchers("/actuator/health", "/ping").permitAll()

                        // ---- IMPORTANT: put specific routes before "/appointments/{id}" ----

                        // Patient-only lists
                        .requestMatchers(HttpMethod.GET, "/appointments/patient/me")
                        .hasAnyRole("PATIENT", "CLINIC_ADMIN", "SYSTEM_ADMIN")

                        // Doctor-only lists
                        .requestMatchers(HttpMethod.GET, "/appointments/doctor/me")
                        .hasAnyRole("DOCTOR", "CLINIC_ADMIN", "SYSTEM_ADMIN")

                        // Create appointment (patient)
                        .requestMatchers(HttpMethod.POST, "/appointments")
                        .hasAnyRole("PATIENT", "CLINIC_ADMIN", "SYSTEM_ADMIN")

                        // Cancel / Reschedule
                        .requestMatchers(HttpMethod.PUT, "/appointments/*/cancel")
                        .hasAnyRole("PATIENT", "DOCTOR", "CLINIC_ADMIN", "SYSTEM_ADMIN")

                        .requestMatchers(HttpMethod.PUT, "/appointments/*/reschedule")
                        .hasAnyRole("PATIENT", "DOCTOR", "CLINIC_ADMIN", "SYSTEM_ADMIN")

                        // Get by id (for now: any authenticated user; later you’ll enforce ownership)
                        .requestMatchers(HttpMethod.GET, "/appointments/*")
                        .authenticated()

                        // Everything else
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
