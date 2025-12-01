package com.medsync.auth_service.infrastructure.persistence;

import com.medsync.auth_service.domain.user.User;
import com.medsync.auth_service.domain.user.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository jpaRepository;

    public UserRepositoryImpl(UserJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepository.findByEmail(email)
                .map(UserMapper::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    @Override
    public User save(User user) {
        var savedEntity = jpaRepository.save(UserMapper.toEntity(user));
        User savedDomain = UserMapper.toDomain(savedEntity);
        // ensure domain id is set
        user.setId(savedDomain.getId());
        return savedDomain;
    }

    @Override
    public Optional<User> findById(Long id) {
        return jpaRepository.findById(id)
                .map(UserMapper::toDomain);
    }
}
