package com.medsync.doctor_service.api.dto;

import com.medsync.doctor_service.domain.doctor.Specialization;
import lombok.Data;

import java.time.Instant;

@Data
public class DoctorResponse {

    private Long id;
    private Long userId;
    private String fullName;
    private Specialization specialization;
    private String qualifications;
    private int yearsOfExperience;
    private String clinicName;
    private String clinicAddress;
    private String city;
    private String phone;
    private String email;
    private String about;
    private Instant createdAt;
    private Instant updatedAt;
}
