package com.medsync.patient_service.domain.patient;

import java.util.Optional;
import java.util.List;

public interface PatientRepository {
    Optional<Patient> findById(Long id);

    Optional<Patient> findByUserId(Long userId);

    boolean existsByUserId(Long userId);

    Patient save(Patient patient);

    List<Patient> getAll();

    Patient getById(Long userId);
}
