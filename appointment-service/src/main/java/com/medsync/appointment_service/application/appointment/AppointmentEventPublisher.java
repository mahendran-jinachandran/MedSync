package com.medsync.appointment_service.application.appointment;

import com.medsync.appointment_service.domain.appointment.Appointment;

public interface AppointmentEventPublisher {

    void publishAppointmentCreated(Appointment appointment);

    void publishAppointmentCancelled(Appointment appointment);

    void publishAppointmentRescheduled(Appointment appointment);
}
