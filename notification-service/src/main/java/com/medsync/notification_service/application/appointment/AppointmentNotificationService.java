package com.medsync.notification_service.application.appointment;

import com.medsync.notification_service.messaging.appointment.AppointmentEvent;
import com.medsync.notification_service.messaging.appointment.AppointmentEventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AppointmentNotificationService {

    private static final Logger log = LoggerFactory.getLogger(AppointmentNotificationService.class);

    // In-memory log of last N events for debugging (optional)
    private static final int MAX_EVENTS = 100;
    private final CircularEventBuffer buffer = new CircularEventBuffer(MAX_EVENTS);

    public void handleEvent(AppointmentEvent event) {
        log.info("Received appointment event: type={}, appointmentId={}, patientId={}, doctorId={}, time={} - {}",
                event.getEventType(),
                event.getAppointmentId(),
                event.getPatientId(),
                event.getDoctorId(),
                event.getStartTime(),
                event.getStatus()
        );

        buffer.add(event);

        // Later:
        // switch (event.getEventType()) {
        //   case APPOINTMENT_CREATED -> sendCreationNotifications(event);
        //   case APPOINTMENT_CANCELLED -> sendCancellationNotifications(event);
        //   case APPOINTMENT_RESCHEDULED -> sendRescheduleNotifications(event);
        // }
    }

    public AppointmentEvent[] getRecentEvents() {
        return buffer.snapshot();
    }

    // simple inner class for circular buffer
    private static class CircularEventBuffer {
        private final AppointmentEvent[] events;
        private int index = 0;
        private int size = 0;

        CircularEventBuffer(int capacity) {
            this.events = new AppointmentEvent[capacity];
        }

        synchronized void add(AppointmentEvent event) {
            events[index] = event;
            index = (index + 1) % events.length;
            if (size < events.length) {
                size++;
            }
        }

        synchronized AppointmentEvent[] snapshot() {
            AppointmentEvent[] result = new AppointmentEvent[size];
            for (int i = 0; i < size; i++) {
                int pos = (index - size + i + events.length) % events.length;
                result[i] = events[pos];
            }
            return result;
        }
    }
}
