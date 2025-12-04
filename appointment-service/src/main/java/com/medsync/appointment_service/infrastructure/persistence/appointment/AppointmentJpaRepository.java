package com.medsync.appointment_service.infrastructure.persistence.appointment;

import com.medsync.appointment_service.domain.appointment.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

public interface AppointmentJpaRepository extends JpaRepository<AppointmentEntity, Long> {

    List<AppointmentEntity> findByPatientId(Long patientId);

    List<AppointmentEntity> findByDoctorId(Long doctorId);

    // Overlap condition:
    // existing.startTime < newEnd AND existing.endTime > newStart
    boolean existsByDoctorIdAndStatusInAndStartTimeLessThanAndEndTimeGreaterThan(
            Long doctorId,
            Collection<AppointmentStatus> statuses,
            Instant newEnd,
            Instant newStart
    );
}
