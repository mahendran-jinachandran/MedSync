package com.medsync.patient_service.infrastructure.persistence.patient;


import com.medsync.patient_service.domain.patient.Patient;

public class PatientMapper {

    public static Patient toDomain(PatientEntity e) {
        if (e == null) return null;

        Patient p = new Patient();
        p.setId(e.getId());
        p.setUserId(e.getUserId());
        p.setFullName(e.getFullName());
        p.setDateOfBirth(e.getDateOfBirth());
        p.setGender(e.getGender());
        p.setPhone(e.getPhone());
        p.setAddress(e.getAddress());
        p.setAllergies(e.getAllergies());
        p.setChronicConditions(e.getChronicConditions());
        p.setCreatedAt(e.getCreatedAt());
        p.setUpdatedAt(e.getUpdatedAt());
        return p;
    }

    public static PatientEntity toEntity(Patient p) {
        PatientEntity e = new PatientEntity();
        e.setId(p.getId());
        e.setUserId(p.getUserId());
        e.setFullName(p.getFullName());
        e.setDateOfBirth(p.getDateOfBirth());
        e.setGender(p.getGender());
        e.setPhone(p.getPhone());
        e.setAddress(p.getAddress());
        e.setAllergies(p.getAllergies());
        e.setChronicConditions(p.getChronicConditions());
        e.setCreatedAt(p.getCreatedAt());
        e.setUpdatedAt(p.getUpdatedAt());
        return e;
    }
}

