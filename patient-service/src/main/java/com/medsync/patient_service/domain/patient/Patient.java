package com.medsync.patient_service.domain.patient;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Patient {
    private Long id;
    private Long userId;
    private String fullName;
    private LocalDate dateOfBirth;
    private Gender gender;
    private String phone;
    private String address;
    private String allergies;
    private String chronicConditions;
    private Instant createdAt;
    private Instant updatedAt;

    public static Patient newPatient(
            Long userId,
            String fullName,
            LocalDate dateOfBirth,
            Gender gender,
            String phone,
            String address,
            String allergies,
            String chronicConditions
    ) {
        Instant now = Instant.now();
        Patient p = new Patient();
        p.userId = userId;
        p.fullName = fullName;
        p.dateOfBirth = dateOfBirth;
        p.gender = gender;
        p.phone = phone;
        p.address = address;
        p.allergies = allergies;
        p.chronicConditions = chronicConditions;
        p.createdAt = now;
        p.updatedAt = now;
        return p;
    }

    public void updatePersonalInfo(String fullName, LocalDate dob, Gender gender) {
        this.fullName = fullName;
        this.dateOfBirth = dob;
        this.gender = gender;
        this.updatedAt = Instant.now();
    }

    public void updateContactInfo(String phone, String address) {
        this.phone = phone;
        this.address = address;
        this.updatedAt = Instant.now();
    }

    public void updateMedicalInfo(String allergies, String chronicConditions) {
        this.allergies = allergies;
        this.chronicConditions = chronicConditions;
        this.updatedAt = Instant.now();
    }
}
