package com.medsync.appointment_service.domain.appointment;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository {

    Appointment save(Appointment appointment);

    Optional<Appointment> findById(Long id);

    List<Appointment> findByPatientId(Long patientId);

    List<Appointment> findByDoctorId(Long doctorId);

    boolean existsOverlappingForDoctor(Long doctorId, Instant start, Instant end);
}
