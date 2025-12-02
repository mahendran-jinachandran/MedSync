package com.medsync.patient_service.api.controller;

import com.medsync.patient_service.api.dto.PatientRequest;
import com.medsync.patient_service.api.dto.PatientResponse;
import com.medsync.patient_service.application.patient.PatientCreateCommand;
import com.medsync.patient_service.application.patient.PatientService;
import com.medsync.patient_service.application.patient.PatientUpdateCommand;
import com.medsync.patient_service.domain.patient.Gender;
import com.medsync.patient_service.domain.patient.Patient;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientService service;

    public PatientController(PatientService service) {
        this.service = service;
    }

    @PostMapping
    public PatientResponse create(@Valid @RequestBody PatientRequest request,
                                  Authentication authentication) {

        Long userId = (Long) authentication.getDetails();

        LocalDate dob = request.getDateOfBirth() != null && !request.getDateOfBirth().isBlank()
                ? LocalDate.parse(request.getDateOfBirth())
                : null;

        Gender gender = request.getGender() != null && !request.getGender().isBlank()
                ? Gender.valueOf(request.getGender().toUpperCase())
                : Gender.UNKNOWN;

        PatientCreateCommand cmd = new PatientCreateCommand(
                userId,
                request.getFullName(),
                dob,
                gender,
                request.getPhone(),
                request.getAddress(),
                request.getAllergies(),
                request.getChronicConditions()
        );

        Patient patient = service.createPatient(cmd);
        return toResponse(patient);
    }

    @GetMapping("/me")
    public PatientResponse me(Authentication authentication) {
        Long userId = (Long) authentication.getDetails();
        Patient patient = service.getByUserId(userId);
        return toResponse(patient);
    }

    @PutMapping("/me")
    public PatientResponse updateMe(@Valid @RequestBody PatientRequest request,
                                    Authentication authentication) {

        Long userId = (Long) authentication.getDetails();

        LocalDate dob = request.getDateOfBirth() != null && !request.getDateOfBirth().isBlank()
                ? LocalDate.parse(request.getDateOfBirth())
                : null;

        Gender gender = request.getGender() != null && !request.getGender().isBlank()
                ? Gender.valueOf(request.getGender().toUpperCase())
                : Gender.UNKNOWN;

        PatientUpdateCommand cmd = new PatientUpdateCommand(
                request.getFullName(),
                dob,
                gender,
                request.getPhone(),
                request.getAddress(),
                request.getAllergies(),
                request.getChronicConditions()
        );

        Patient updated = service.updateForUser(userId, cmd);
        return toResponse(updated);
    }

    private PatientResponse toResponse(Patient p) {
        PatientResponse response = new PatientResponse();
        response.setId(p.getId());
        response.setUserId(p.getUserId());
        response.setFullName(p.getFullName());
        response.setDateOfBirth(p.getDateOfBirth());
        response.setGender(p.getGender());
        response.setPhone(p.getPhone());
        response.setAddress(p.getAddress());
        response.setAllergies(p.getAllergies());
        response.setChronicConditions(p.getChronicConditions());
        response.setCreatedAt(p.getCreatedAt());
        response.setUpdatedAt(p.getUpdatedAt());
        return response;
    }
}
