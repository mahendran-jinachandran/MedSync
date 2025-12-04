package com.medsync.doctor_service.domain.doctor;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository {
    Optional<Doctor> findById(Long id);

    Optional<Doctor> findByUserId(Long userId);

    boolean existsByUserId(Long userId);

    Doctor save(Doctor doctor);

    List<Doctor> findByCityAndSpecialization(String city, Specialization specialization);

    List<Doctor> findAll();
}
