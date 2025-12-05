package com.medsync.notification_service.api.controller;

import com.medsync.notification_service.application.appointment.AppointmentNotificationService;
import com.medsync.notification_service.messaging.appointment.AppointmentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DebugController {

    private final AppointmentNotificationService notificationService;

    public DebugController(AppointmentNotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/debug/appointments/events")
    public AppointmentEvent[] recentAppointmentEvents() {
        return notificationService.getRecentEvents();
    }
}
