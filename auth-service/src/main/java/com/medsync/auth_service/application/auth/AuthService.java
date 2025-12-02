package com.medsync.auth_service.application.auth;

import com.medsync.auth_service.api.exception.InvalidCredentialsException;
import com.medsync.auth_service.api.exception.UserAlreadyExistsException;
import com.medsync.auth_service.domain.role.RoleName;
import com.medsync.auth_service.domain.user.User;
import com.medsync.auth_service.domain.user.UserRepository;
import com.medsync.security.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtTokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    public AuthResult register(RegisterCommand command) {
        if (userRepository.existsByEmail(command.email())) {
            throw new UserAlreadyExistsException(command.email());
        }

        RoleName roleName = RoleName.valueOf(command.role().toUpperCase());

        String encodedPassword = passwordEncoder.encode(command.rawPassword());

        User newUser = User.newUser(
                command.email(),
                encodedPassword,
                command.fullName(),
                Set.of(roleName)
        );

        User saved = userRepository.save(newUser);

        // convert roles to Set<String> for shared JwtTokenProvider
        Set<String> roleNames = saved.getRoles().stream()
                .map(Enum::name)
                .collect(Collectors.toSet());

        String token = tokenProvider.generateAccessToken(
                saved.getId(),
                saved.getEmail(),
                roleNames
        );

        return new AuthResult(
                token,
                saved.getId(),
                saved.getEmail(),
                saved.getFullName(),
                saved.getRoles()
        );
    }

    public AuthResult login(LoginCommand command) {
        User user = userRepository.findByEmail(command.email())
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(command.rawPassword(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        Set<String> roleNames = user.getRoles().stream()
                .map(Enum::name)
                .collect(Collectors.toSet());

        String token = tokenProvider.generateAccessToken(
                user.getId(),
                user.getEmail(),
                roleNames
        );

        return new AuthResult(
                token,
                user.getId(),
                user.getEmail(),
                user.getFullName(),
                user.getRoles()
        );
    }
}
