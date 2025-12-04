package com.medsync.doctor_service.infrastructure.persistence.doctor;

import com.medsync.doctor_service.domain.doctor.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DoctorJpaRepository extends JpaRepository<DoctorEntity, Long> {

    Optional<DoctorEntity> findByUserId(Long userId);

    boolean existsByUserId(Long userId);

    List<DoctorEntity> findByCityAndSpecialization(String city, Specialization specialization);
}
