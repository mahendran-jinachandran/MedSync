package com.medsync.doctor_service.api.exception;

public class DoctorAlreadyExistsException extends RuntimeException {
    public DoctorAlreadyExistsException(Long userId) {
        super("Doctor profile already exists for userId: " + userId);
    }
}
