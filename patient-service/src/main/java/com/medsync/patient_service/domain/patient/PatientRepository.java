package com.medsync.patient_service.domain.patient;

import java.util.Optional;

public interface PatientRepository {
    Optional<Patient> findById(Long id);

    Optional<Patient> findByUserId(Long userId);

    boolean existsByUserId(Long userId);

    Patient save(Patient patient);
}
