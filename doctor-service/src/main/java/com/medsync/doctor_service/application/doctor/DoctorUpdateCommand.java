package com.medsync.doctor_service.application.doctor;

import com.medsync.doctor_service.domain.doctor.Specialization;

public record DoctorUpdateCommand(
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
) { }
