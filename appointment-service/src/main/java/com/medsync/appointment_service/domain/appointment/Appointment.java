package com.medsync.appointment_service.domain.appointment;

import lombok.Data;

import java.time.Instant;

@Data
public class Appointment {

    private Long id;
    private Long patientId;
    private Long doctorId;

    private Instant startTime;
    private Instant endTime;

    private String reason;
    private AppointmentStatus status;

    private Instant createdAt;
    private Instant updatedAt;

    // Factory for new appointment
    public static Appointment scheduleNew(
            Long patientId,
            Long doctorId,
            Instant startTime,
            Instant endTime,
            String reason
    ) {
        Instant now = Instant.now();

        Appointment a = new Appointment();
        a.patientId = patientId;
        a.doctorId = doctorId;
        a.startTime = startTime;
        a.endTime = endTime;
        a.reason = reason;
        a.status = AppointmentStatus.SCHEDULED;
        a.createdAt = now;
        a.updatedAt = now;
        return a;
    }

    // Business operations

    public void cancel() {
        if (this.status == AppointmentStatus.CANCELLED) {
            return;
        }
        this.status = AppointmentStatus.CANCELLED;
        this.updatedAt = Instant.now();
    }

    public void complete() {
        if (this.status == AppointmentStatus.CANCELLED) {
            throw new IllegalStateException("Cannot complete a cancelled appointment");
        }
        this.status = AppointmentStatus.COMPLETED;
        this.updatedAt = Instant.now();
    }

    public void reschedule(Instant newStart, Instant newEnd) {
        if (this.status == AppointmentStatus.CANCELLED) {
            throw new IllegalStateException("Cannot reschedule a cancelled appointment");
        }
        this.startTime = newStart;
        this.endTime = newEnd;
        this.status = AppointmentStatus.RESCHEDULED;
        this.updatedAt = Instant.now();
    }
}
