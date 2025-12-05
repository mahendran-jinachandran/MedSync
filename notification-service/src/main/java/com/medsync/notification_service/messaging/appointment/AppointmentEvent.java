package com.medsync.notification_service.messaging.appointment;

import lombok.Data;

import java.time.Instant;

@Data
public class AppointmentEvent {
    private String eventId;
    private AppointmentEventType eventType;
    private Instant eventTimestamp;

    private Long appointmentId;
    private Long patientId;
    private Long doctorId;
    private Instant startTime;
    private Instant endTime;
    private String status;
    private String reason;
}
