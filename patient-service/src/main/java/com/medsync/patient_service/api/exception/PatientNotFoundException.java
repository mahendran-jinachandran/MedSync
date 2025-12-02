package com.medsync.patient_service.api.exception;

public class PatientNotFoundException extends RuntimeException {
    public PatientNotFoundException(Long userId) {
        super("Patient not found for userId: " + userId);
    }
}
