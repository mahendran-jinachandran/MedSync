package com.medsync.appointment_service.api.exception;

public class OverlappingAppointmentException extends RuntimeException {
    public OverlappingAppointmentException(Long doctorId) {
        super("Doctor already has an appointment in this time range. doctorId: " + doctorId);
    }
}
