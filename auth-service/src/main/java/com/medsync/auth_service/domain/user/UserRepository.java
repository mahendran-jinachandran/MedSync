package com.medsync.auth_service.domain.user;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    public Optional<User> findByEmail(String email);
    public boolean existsByEmail(String email);

    public User save(User user);

    public Optional<User> findById(Long uuid);
}
