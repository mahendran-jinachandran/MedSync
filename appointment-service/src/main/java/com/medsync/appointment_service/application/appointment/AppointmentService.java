package com.medsync.appointment_service.application.appointment;

import com.medsync.appointment_service.api.exception.AppointmentNotFoundException;
import com.medsync.appointment_service.api.exception.InvalidAppointmentTimeException;
import com.medsync.appointment_service.api.exception.OverlappingAppointmentException;
import com.medsync.appointment_service.domain.appointment.Appointment;
import com.medsync.appointment_service.domain.appointment.AppointmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository repository;

    public AppointmentService(AppointmentRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Appointment createAppointment(AppointmentCreateCommand cmd) {
        validateTimes(cmd.startTime(), cmd.endTime());

        // check overlapping for doctor
        if (repository.existsOverlappingForDoctor(cmd.doctorId(), cmd.startTime(), cmd.endTime())) {
            throw new OverlappingAppointmentException(cmd.doctorId());
        }

        Appointment appt = Appointment.scheduleNew(
                cmd.patientId(),
                cmd.doctorId(),
                cmd.startTime(),
                cmd.endTime(),
                cmd.reason()
        );

        return repository.save(appt);
    }

    @Transactional
    public Appointment cancelAppointment(AppointmentCancelCommand cmd) {
        Appointment appt = repository.findById(cmd.appointmentId())
                .orElseThrow(() -> new AppointmentNotFoundException(cmd.appointmentId()));

        // in a more advanced version, we'd also verify patientId matches
        appt.cancel();
        return repository.save(appt);
    }

    @Transactional
    public Appointment rescheduleAppointment(AppointmentRescheduleCommand cmd) {
        validateTimes(cmd.newStartTime(), cmd.newEndTime());

        Appointment appt = repository.findById(cmd.appointmentId())
                .orElseThrow(() -> new AppointmentNotFoundException(cmd.appointmentId()));

        // check overlapping for doctor with new times
        if (repository.existsOverlappingForDoctor(appt.getDoctorId(), cmd.newStartTime(), cmd.newEndTime())) {
            throw new OverlappingAppointmentException(appt.getDoctorId());
        }

        appt.reschedule(cmd.newStartTime(), cmd.newEndTime());
        return repository.save(appt);
    }

    @Transactional(readOnly = true)
    public Appointment getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<Appointment> getForPatient(Long patientId) {
        return repository.findByPatientId(patientId);
    }

    @Transactional(readOnly = true)
    public List<Appointment> getForDoctor(Long doctorId) {
        return repository.findByDoctorId(doctorId);
    }

    private void validateTimes(Instant start, Instant end) {
        Instant now = Instant.now();
        if (start.isBefore(now)) {
            throw new InvalidAppointmentTimeException("Start time cannot be in the past");
        }
        if (!end.isAfter(start)) {
            throw new InvalidAppointmentTimeException("End time must be after start time");
        }
    }
}
