package com.medsync.doctor_service.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DoctorRequest {

    @NotBlank
    private String fullName;

    @NotBlank
    private String specialization; // e.g., "CARDIOLOGY"

    private String qualifications;

    @Min(0)
    private int yearsOfExperience;

    private String clinicName;
    private String clinicAddress;
    private String city;

    private String phone;
    private String email;
    private String about;
}
