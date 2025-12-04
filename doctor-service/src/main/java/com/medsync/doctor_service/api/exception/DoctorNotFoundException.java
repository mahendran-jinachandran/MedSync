package com.medsync.doctor_service.api.exception;

public class DoctorNotFoundException extends RuntimeException {
    public DoctorNotFoundException(Long id) {
        super("Doctor not found with id: " + id);
    }

    public DoctorNotFoundException(Long userId, boolean byUserId) {
        super("Doctor profile not found for userId: " + userId);
    }
}
