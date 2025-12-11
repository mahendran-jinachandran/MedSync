package com.medsync.patient_service.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PatientRequest {

    @NotBlank
    private String fullName;
    private String dateOfBirth;   // format: yyyy-MM-dd
    private String gender;        // values: MALE, FEMALE, OTHER, UNKNOWN
    private String phone;
    private String address;
    private String allergies;
    private String chronicConditions;
}
