package com.medsync.patient_service.application.patient;

import com.medsync.patient_service.domain.patient.Gender;

import java.time.LocalDate;

public record PatientUpdateCommand(

        Long userId,
        String fullName,
        LocalDate dateOfBirth,
        Gender gender,
        String phone,
        String address,
        String allergies,
        String chronicConditions
) { }
