package com.medsync.doctor_service.application.doctor;

import com.medsync.doctor_service.api.exception.DoctorAlreadyExistsException;
import com.medsync.doctor_service.api.exception.DoctorNotFoundException;
import com.medsync.doctor_service.domain.doctor.Doctor;
import com.medsync.doctor_service.domain.doctor.DoctorRepository;
import com.medsync.doctor_service.domain.doctor.Specialization;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DoctorService {

    private final DoctorRepository repository;

    public DoctorService(DoctorRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Doctor createDoctor(DoctorCreateCommand cmd) {
        if (repository.existsByUserId(cmd.userId())) {
            throw new DoctorAlreadyExistsException(cmd.userId());
        }

        Doctor doctor = Doctor.newDoctor(
                cmd.userId(),
                cmd.fullName(),
                cmd.specialization(),
                cmd.qualifications(),
                cmd.yearsOfExperience(),
                cmd.clinicName(),
                cmd.clinicAddress(),
                cmd.city(),
                cmd.phone(),
                cmd.email(),
                cmd.about()
        );

        return repository.save(doctor);
    }

    @Transactional(readOnly = true)
    public Doctor getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new DoctorNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public Doctor getByUserId(Long userId) {
        return repository.findByUserId(userId)
                .orElseThrow(() -> new DoctorNotFoundException(userId, true));
    }

    @Transactional
    public Doctor updateForUser(Long userId, DoctorUpdateCommand cmd) {
        Doctor doctor = repository.findByUserId(userId)
                .orElseThrow(() -> new DoctorNotFoundException(userId, true));

        doctor.updateProfile(
                cmd.fullName(),
                cmd.specialization(),
                cmd.qualifications(),
                cmd.yearsOfExperience(),
                cmd.clinicName(),
                cmd.clinicAddress(),
                cmd.city(),
                cmd.phone(),
                cmd.email(),
                cmd.about()
        );

        return repository.save(doctor);
    }

    @Transactional(readOnly = true)
    public List<Doctor> listAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Doctor> searchByCityAndSpecialization(String city, Specialization specialization) {
        return repository.findByCityAndSpecialization(city, specialization);
    }
}

