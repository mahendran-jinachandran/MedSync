package com.medsync.appointment_service.application.appointment;

public record AppointmentCancelCommand(
        Long appointmentId,
        Long patientId
) { }