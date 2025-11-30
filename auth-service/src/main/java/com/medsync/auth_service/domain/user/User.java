package com.medsync.auth_service.domain.user;

import com.medsync.auth_service.domain.role.RoleName;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class User {

    private UUID id;
    private String email;
    private String password;
    private String fullName;
    private String status;
    private RoleName roles;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
