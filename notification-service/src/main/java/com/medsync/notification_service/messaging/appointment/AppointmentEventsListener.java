package com.medsync.notification_service.messaging.appointment;

import com.medsync.notification_service.application.appointment.AppointmentNotificationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class AppointmentEventsListener {

    private final AppointmentNotificationService notificationService;

    public AppointmentEventsListener(AppointmentNotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(
            topics = "${medsync.kafka.appointments-topic}",
            containerFactory = "appointmentEventKafkaListenerContainerFactory"
    )
    public void onAppointmentEvent(@Payload AppointmentEvent event) {
        notificationService.handleEvent(event);
    }
}
