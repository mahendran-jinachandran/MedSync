package com.medsync.doctor_service.infrastructure.persistence.doctor;

import com.medsync.doctor_service.domain.doctor.Doctor;
import com.medsync.doctor_service.domain.doctor.DoctorRepository;
import com.medsync.doctor_service.domain.doctor.Specialization;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DoctorRepositoryImpl implements DoctorRepository {

    private final DoctorJpaRepository jpaRepository;
    private final DoctorMapper mapper;

    public DoctorRepositoryImpl(DoctorJpaRepository jpaRepository, DoctorMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Doctor> findById(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Doctor> findByUserId(Long userId) {
        return jpaRepository.findByUserId(userId)
                .map(mapper::toDomain);
    }

    @Override
    public boolean existsByUserId(Long userId) {
        return jpaRepository.existsByUserId(userId);
    }

    @Override
    public Doctor save(Doctor doctor) {
        DoctorEntity entity = mapper.toEntity(doctor);
        DoctorEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public List<Doctor> findByCityAndSpecialization(String city, Specialization specialization) {
        return jpaRepository.findByCityAndSpecialization(city, specialization)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Doctor> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}
