package com.medsync.patient_service.api.dto;

import com.medsync.patient_service.domain.patient.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PatientResponse {

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
}
