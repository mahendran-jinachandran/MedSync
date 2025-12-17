package com.medsync.patient_service.application.patient;

import com.medsync.patient_service.api.exception.PatientAlreadyExistsException;
import com.medsync.patient_service.api.exception.PatientNotFoundException;
import com.medsync.patient_service.domain.patient.Patient;
import com.medsync.patient_service.domain.patient.PatientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PatientService {

    private final PatientRepository repository;

    public PatientService(PatientRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Patient createPatient(PatientCreateCommand cmd) {
        if (repository.existsByUserId(cmd.userId())) {
            throw new PatientAlreadyExistsException(cmd.userId());
        }

        Patient patient = Patient.newPatient(
                cmd.userId(),
                cmd.fullName(),
                cmd.dateOfBirth(),
                cmd.gender(),
                cmd.phone(),
                cmd.address(),
                cmd.allergies(),
                cmd.chronicConditions()
        );

        return repository.save(patient);
    }

    @Transactional(readOnly = true)
    public Patient getByUserId(Long userId) {
        return repository.findByUserId(userId)
                .orElseThrow(() -> new PatientNotFoundException(userId));
    }

    @Transactional
    public Patient updateForUser(PatientUpdateCommand cmd) {
        Patient patient = repository.findByUserId(cmd.userId())
                .orElseThrow(() -> new PatientNotFoundException(cmd.userId()));

        patient.updatePersonalInfo(cmd.fullName(), cmd.dateOfBirth(), cmd.gender());
        patient.updateContactInfo(cmd.phone(), cmd.address());
        patient.updateMedicalInfo(cmd.allergies(), cmd.chronicConditions());

        return repository.save(patient);
    }

    //  ---------------- ADMIN methods ----------------
    @Transactional(readOnly = true)
    public List<Patient> getAll() {
        return repository.getAll();
    }

    public Patient getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException(id));
    }
}
