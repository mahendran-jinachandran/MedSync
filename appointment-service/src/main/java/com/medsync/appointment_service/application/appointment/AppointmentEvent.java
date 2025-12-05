package com.medsync.appointment_service.application.appointment;

import com.medsync.appointment_service.domain.appointment.AppointmentStatus;
import lombok.Data;

import java.time.Instant;

@Data
public class AppointmentEvent {

    private String eventId;          // UUID string
    private AppointmentEventType eventType;
    private Instant eventTimestamp;

    private Long appointmentId;
    private Long patientId;
    private Long doctorId;
    private Instant startTime;
    private Instant endTime;
    private AppointmentStatus status;
    private String reason;

    public AppointmentEvent() {
    }

    public AppointmentEvent(String eventId,
                            AppointmentEventType eventType,
                            Instant eventTimestamp,
                            Long appointmentId,
                            Long patientId,
                            Long doctorId,
                            Instant startTime,
                            Instant endTime,
                            AppointmentStatus status,
                            String reason) {
        this.eventId = eventId;
        this.eventType = eventType;
        this.eventTimestamp = eventTimestamp;
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.reason = reason;
    }
}
