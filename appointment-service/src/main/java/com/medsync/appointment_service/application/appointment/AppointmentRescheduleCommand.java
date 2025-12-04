package com.medsync.appointment_service.application.appointment;

import java.time.Instant;

public record AppointmentRescheduleCommand(
        Long appointmentId,
        Long patientId,
        Instant newStartTime,
        Instant newEndTime
) { }
