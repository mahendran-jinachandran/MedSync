package com.medsync.appointment_service.infrastructure.messaging.appointment;

import com.medsync.appointment_service.application.appointment.AppointmentEvent;
import com.medsync.appointment_service.application.appointment.AppointmentEventPublisher;
import com.medsync.appointment_service.application.appointment.AppointmentEventType;
import com.medsync.appointment_service.domain.appointment.Appointment;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class KafkaAppointmentEventPublisher implements AppointmentEventPublisher {

    private final KafkaTemplate<String, AppointmentEvent> kafkaTemplate;
    private final String topicName;

    public KafkaAppointmentEventPublisher(
            KafkaTemplate<String, AppointmentEvent> kafkaTemplate,
            @Value("${medsync.kafka.appointments-topic}") String topicName
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.topicName = topicName;
    }

    @Override
    public void publishAppointmentCreated(Appointment appointment) {
        AppointmentEvent event = buildEvent(AppointmentEventType.APPOINTMENT_CREATED, appointment);
        sendEvent(appointment, event);
    }

    @Override
    public void publishAppointmentCancelled(Appointment appointment) {
        AppointmentEvent event = buildEvent(AppointmentEventType.APPOINTMENT_CANCELLED, appointment);
        sendEvent(appointment, event);
    }

    @Override
    public void publishAppointmentRescheduled(Appointment appointment) {
        AppointmentEvent event = buildEvent(AppointmentEventType.APPOINTMENT_RESCHEDULED, appointment);
        sendEvent(appointment, event);
    }

    private AppointmentEvent buildEvent(AppointmentEventType type, Appointment appointment) {
        return new AppointmentEvent(
                UUID.randomUUID().toString(),
                type,
                Instant.now(),
                appointment.getId(),
                appointment.getPatientId(),
                appointment.getDoctorId(),
                appointment.getStartTime(),
                appointment.getEndTime(),
                appointment.getStatus(),
                appointment.getReason()
        );
    }

    private void sendEvent(Appointment appointment, AppointmentEvent event) {
        // Use doctorId as key to keep a doctor's events in the same partition
        String key = appointment.getDoctorId() != null
                ? appointment.getDoctorId().toString()
                : "unknown";

        ProducerRecord<String, AppointmentEvent> record =
                new ProducerRecord<>(topicName, key, event);

        kafkaTemplate.send(record);
    }
}
