package com.medsync.appointment_service.api.controller;

import com.medsync.appointment_service.api.dto.AppointmentRequest;
import com.medsync.appointment_service.api.dto.AppointmentRescheduleRequest;
import com.medsync.appointment_service.api.dto.AppointmentResponse;
import com.medsync.appointment_service.application.appointment.AppointmentCancelCommand;
import com.medsync.appointment_service.application.appointment.AppointmentCreateCommand;
import com.medsync.appointment_service.application.appointment.AppointmentRescheduleCommand;
import com.medsync.appointment_service.application.appointment.AppointmentService;
import com.medsync.appointment_service.domain.appointment.Appointment;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService service;

    public AppointmentController(AppointmentService service) {
        this.service = service;
    }

    // Create appointment (patient)
    @PostMapping
    public AppointmentResponse create(@Valid @RequestBody AppointmentRequest request,
                                      Authentication authentication) {

        Long userId = (Long) authentication.getDetails(); // this is patient USER id

        Instant start = Instant.parse(request.getStartTime());
        Instant end = Instant.parse(request.getEndTime());

        AppointmentCreateCommand cmd = new AppointmentCreateCommand(
                userId,                  // patientId from JWT
                request.getDoctorId(),   // doctorId from request (assume doctor userId for now)
                start,
                end,
                request.getReason()
        );

        Appointment appt = service.createAppointment(cmd);
        return toResponse(appt);
    }

    // Cancel appointment
    @PutMapping("/{id}/cancel")
    public AppointmentResponse cancel(@PathVariable Long id,
                                      Authentication authentication) {

        Long userId = (Long) authentication.getDetails();
        AppointmentCancelCommand cmd = new AppointmentCancelCommand(id, userId);
        Appointment ap = service.cancelAppointment(cmd);
        return toResponse(ap);
    }

    // Reschedule appointment
    @PutMapping("/{id}/reschedule")
    public AppointmentResponse reschedule(@PathVariable Long id,
                                          @Valid @RequestBody AppointmentRescheduleRequest request,
                                          Authentication authentication) {

        Long userId = (Long) authentication.getDetails();

        Instant start = Instant.parse(request.getStartTime());
        Instant end = Instant.parse(request.getEndTime());

        AppointmentRescheduleCommand cmd = new AppointmentRescheduleCommand(
                id,
                userId,
                start,
                end
        );

        Appointment appt = service.rescheduleAppointment(cmd);
        return toResponse(appt);
    }

    // Get by id
    @GetMapping("/{id}")
    public AppointmentResponse getById(@PathVariable Long id,
                                       Authentication authentication) {
        // For v1, we don’t check ownership; later we can enforce patient/doctor viewing rules
        Appointment appt = service.getById(id);
        return toResponse(appt);
    }

    // List for current patient
    @GetMapping("/patient/me")
    public List<AppointmentResponse> getForPatient(Authentication authentication) {
        Long userId = (Long) authentication.getDetails();
        return service.getForPatient(userId).stream()
                .map(this::toResponse)
                .toList();
    }

    // List for current doctor
    @GetMapping("/doctor/me")
    public List<AppointmentResponse> getForDoctor(Authentication authentication) {
        Long userId = (Long) authentication.getDetails(); // doctor user id
        return service.getForDoctor(userId).stream()
                .map(this::toResponse)
                .toList();
    }

    private AppointmentResponse toResponse(Appointment a) {
        AppointmentResponse res = new AppointmentResponse();
        res.setId(a.getId());
        res.setPatientId(a.getPatientId());
        res.setDoctorId(a.getDoctorId());
        res.setStartTime(a.getStartTime());
        res.setEndTime(a.getEndTime());
        res.setReason(a.getReason());
        res.setStatus(a.getStatus());
        res.setCreatedAt(a.getCreatedAt());
        res.setUpdatedAt(a.getUpdatedAt());
        return res;
    }
}
