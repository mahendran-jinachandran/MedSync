package com.medsync.auth_service.domain.user;

import com.medsync.auth_service.domain.role.RoleName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
public class User {

    private Long id;
    private String email;
    private String password;
    private String fullName;
    private UserStatus status;
    private Set<RoleName> roles;
    private Instant createdAt;
    private Instant updatedAt;

    public static User newUser(String email, String passwordHash, String fullName, Set<RoleName> roles) {

        Instant now = Instant.now();
        return new User(
                null,
                email,
                passwordHash,
                fullName,
                UserStatus.ACTIVE,
                roles,
                now,
                now
        );
    }

    public void addRole(RoleName role) {
        this.roles.add(role);
    }

    public void removeRole(RoleName role) {
        this.roles.remove(role);
    }
}
