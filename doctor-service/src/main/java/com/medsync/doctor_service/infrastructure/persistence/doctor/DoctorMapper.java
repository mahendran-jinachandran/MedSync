package com.medsync.doctor_service.infrastructure.persistence.doctor;

import com.medsync.doctor_service.domain.doctor.Doctor;
import org.springframework.stereotype.Component;

@Component
public class DoctorMapper {

    public Doctor toDomain(DoctorEntity e) {
        Doctor d = new Doctor();
        d.setId(e.getId());
        d.setUserId(e.getUserId());
        d.setFullName(e.getFullName());
        d.setSpecialization(e.getSpecialization());
        d.setQualifications(e.getQualifications());
        d.setYearsOfExperience(e.getYearsOfExperience());
        d.setClinicName(e.getClinicName());
        d.setClinicAddress(e.getClinicAddress());
        d.setCity(e.getCity());
        d.setPhone(e.getPhone());
        d.setEmail(e.getEmail());
        d.setAbout(e.getAbout());
        d.setCreatedAt(e.getCreatedAt());
        d.setUpdatedAt(e.getUpdatedAt());
        return d;
    }

    public DoctorEntity toEntity(Doctor d) {
        DoctorEntity e = new DoctorEntity();
        e.setId(d.getId());
        e.setUserId(d.getUserId());
        e.setFullName(d.getFullName());
        e.setSpecialization(d.getSpecialization());
        e.setQualifications(d.getQualifications());
        e.setYearsOfExperience(d.getYearsOfExperience());
        e.setClinicName(d.getClinicName());
        e.setClinicAddress(d.getClinicAddress());
        e.setCity(d.getCity());
        e.setPhone(d.getPhone());
        e.setEmail(d.getEmail());
        e.setAbout(d.getAbout());
        e.setCreatedAt(d.getCreatedAt());
        e.setUpdatedAt(d.getUpdatedAt());
        return e;
    }
}

