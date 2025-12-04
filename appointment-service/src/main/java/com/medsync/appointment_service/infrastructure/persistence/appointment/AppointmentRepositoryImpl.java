package com.medsync.appointment_service.infrastructure.persistence.appointment;

import com.medsync.appointment_service.domain.appointment.Appointment;
import com.medsync.appointment_service.domain.appointment.AppointmentRepository;
import com.medsync.appointment_service.domain.appointment.AppointmentStatus;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

@Repository
public class AppointmentRepositoryImpl implements AppointmentRepository {

    private final AppointmentJpaRepository jpaRepository;
    private final AppointmentMapper mapper;

    public AppointmentRepositoryImpl(AppointmentJpaRepository jpaRepository,
                                     AppointmentMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Appointment save(Appointment appointment) {
        AppointmentEntity entity = mapper.toEntity(appointment);
        AppointmentEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Appointment> findById(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<Appointment> findByPatientId(Long patientId) {
        return jpaRepository.findByPatientId(patientId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Appointment> findByDoctorId(Long doctorId) {
        return jpaRepository.findByDoctorId(doctorId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsOverlappingForDoctor(Long doctorId, Instant start, Instant end) {
        // treat active statuses as overlapping (ignore CANCELLED)
        var active = EnumSet.of(AppointmentStatus.SCHEDULED, AppointmentStatus.RESCHEDULED);
        return jpaRepository.existsByDoctorIdAndStatusInAndStartTimeLessThanAndEndTimeGreaterThan(
                doctorId, active, end, start
        );
    }
}
