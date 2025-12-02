package com.medsync.patient_service.api.exception;

public class PatientAlreadyExistsException extends RuntimeException {
    public PatientAlreadyExistsException(Long userId) {
        super("Patient already exists for userId: " + userId);
    }
}
