package com.medsync.appointment_service.application.appointment;

import java.time.Instant;

public record AppointmentCreateCommand(
        Long patientId,
        Long doctorId,
        Instant startTime,
        Instant endTime,
        String reason
) { }
