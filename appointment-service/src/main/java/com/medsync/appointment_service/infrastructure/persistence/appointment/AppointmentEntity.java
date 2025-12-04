package com.medsync.appointment_service.infrastructure.persistence.appointment;

import com.medsync.appointment_service.domain.appointment.AppointmentStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Entity
@Data
@Table(name = "appointments")
public class AppointmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // NOTE: we treat these as USER IDs for now (not patient/doctor table IDs)
    private Long patientId;
    private Long doctorId;

    private Instant startTime;
    private Instant endTime;

    private String reason;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    private Instant createdAt;
    private Instant updatedAt;

}
