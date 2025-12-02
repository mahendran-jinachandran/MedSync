package com.medsync.patient_service.infrastructure.persistence.patient;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientJpaRepository extends JpaRepository<PatientEntity, Long> {

    Optional<PatientEntity> findByUserId(Long userId);

    boolean existsByUserId(Long userId);
}