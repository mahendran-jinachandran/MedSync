package com.medsync.patient_service.infrastructure.persistence.patient;

import com.medsync.patient_service.domain.patient.Patient;
import com.medsync.patient_service.domain.patient.PatientRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PatientRepositoryImpl implements PatientRepository {

    private final PatientJpaRepository jpaRepository;

    public PatientRepositoryImpl(PatientJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<Patient> findById(Long id) {
        return jpaRepository.findById(id).map(PatientMapper::toDomain);
    }

    @Override
    public Optional<Patient> findByUserId(Long userId) {
        return jpaRepository.findByUserId(userId).map(PatientMapper::toDomain);
    }

    @Override
    public boolean existsByUserId(Long userId) {
        return jpaRepository.existsByUserId(userId);
    }

    @Override
    public Patient save(Patient patient) {
        PatientEntity entity = PatientMapper.toEntity(patient);
        PatientEntity saved = jpaRepository.save(entity);
        return PatientMapper.toDomain(saved);
    }

    @Override
    public List<Patient> getAll() {
        return jpaRepository.findAll()
                .stream()
                .map(PatientMapper::toDomain)
                .toList();
    }
}
