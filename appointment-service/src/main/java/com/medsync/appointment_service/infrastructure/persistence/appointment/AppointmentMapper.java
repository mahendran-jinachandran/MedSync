package com.medsync.appointment_service.infrastructure.persistence.appointment;

import com.medsync.appointment_service.domain.appointment.Appointment;
import org.springframework.stereotype.Component;

@Component
public class AppointmentMapper {

    public Appointment toDomain(AppointmentEntity e) {
        Appointment a = new Appointment();
        a.setId(e.getId());
        a.setPatientId(e.getPatientId());
        a.setDoctorId(e.getDoctorId());
        a.setStartTime(e.getStartTime());
        a.setEndTime(e.getEndTime());
        a.setReason(e.getReason());
        a.setStatus(e.getStatus());
        a.setCreatedAt(e.getCreatedAt());
        a.setUpdatedAt(e.getUpdatedAt());
        return a;
    }

    public AppointmentEntity toEntity(Appointment a) {
        AppointmentEntity e = new AppointmentEntity();
        e.setId(a.getId());
        e.setPatientId(a.getPatientId());
        e.setDoctorId(a.getDoctorId());
        e.setStartTime(a.getStartTime());
        e.setEndTime(a.getEndTime());
        e.setReason(a.getReason());
        e.setStatus(a.getStatus());
        e.setCreatedAt(a.getCreatedAt());
        e.setUpdatedAt(a.getUpdatedAt());
        return e;
    }
}
