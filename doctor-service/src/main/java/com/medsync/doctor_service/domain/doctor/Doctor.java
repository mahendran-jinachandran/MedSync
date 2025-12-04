package com.medsync.doctor_service.domain.doctor;

import lombok.Data;

import java.time.Instant;

@Data
public class Doctor {

    private  Long id;
    private Long userId;
    private String fullName;
    private Specialization specialization;
    private String qualifications;
    private  int yearsOfExperience;

    private String clinicName;
    private String clinicAddress;
    private String city;

    private String phone;
    private String email;
    private String about;

    private Instant createdAt;
    private Instant updatedAt;

    public static Doctor newDoctor(
            Long userId,
            String fullName,
            Specialization specialization,
            String qualifications,
            int yearsOfExperience,
            String clinicName,
            String clinicAddress,
            String city,
            String phone,
            String email,
            String about
    ) {
        Instant now = Instant.now();
        Doctor d = new Doctor();
        d.userId = userId;
        d.fullName = fullName;
        d.specialization = specialization;
        d.qualifications = qualifications;
        d.yearsOfExperience = yearsOfExperience;
        d.clinicName = clinicName;
        d.clinicAddress = clinicAddress;
        d.city = city;
        d.phone = phone;
        d.email = email;
        d.about = about;
        d.createdAt = now;
        d.updatedAt = now;
        return d;
    }

    // Business methods

    public void updateProfile(
            String fullName,
            Specialization specialization,
            String qualifications,
            int yearsOfExperience,
            String clinicName,
            String clinicAddress,
            String city,
            String phone,
            String email,
            String about
    ) {
        this.fullName = fullName;
        this.specialization = specialization;
        this.qualifications = qualifications;
        this.yearsOfExperience = yearsOfExperience;
        this.clinicName = clinicName;
        this.clinicAddress = clinicAddress;
        this.city = city;
        this.phone = phone;
        this.email = email;
        this.about = about;
        this.updatedAt = Instant.now();
    }
}
