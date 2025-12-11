package com.medsync.patient_service.api.controller;

import com.medsync.patient_service.api.dto.PatientRequest;
import com.medsync.patient_service.api.dto.PatientResponse;
import com.medsync.patient_service.application.patient.PatientCreateCommand;
import com.medsync.patient_service.application.patient.PatientService;
import com.medsync.patient_service.application.patient.PatientUpdateCommand;
import com.medsync.patient_service.domain.patient.Gender;
import com.medsync.patient_service.domain.patient.Patient;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientService service;

    public PatientController(PatientService service) {
        this.service = service;
    }

    // ---------------- SELF-SERVICE ENDPOINTS (PATIENT ROLE) ----------------

    /**
     * PATIENT registers their profile.
     * POST /patients
     */

    @PostMapping
    public PatientResponse create(@Valid @RequestBody PatientRequest request,
                                  Authentication authentication) {

        Long userId = (Long) authentication.getDetails();
        LocalDate dob = convertLocalDate(request.getDateOfBirth());
        Gender gender = convertGender(request.getGender());

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

    private LocalDate convertLocalDate(String dateOfBirth) {
        return dateOfBirth != null && !dateOfBirth.isBlank()
                ? LocalDate.parse(dateOfBirth)
                : null;
    }

    private Gender convertGender(String gender) {
        return gender != null && !gender.isBlank()
                ? Gender.valueOf(gender.toUpperCase())
                : Gender.UNKNOWN;
    }

    /**
     * PATIENT views their own profile.
     * GET /patients/me
     */
    @GetMapping("/me")
    public PatientResponse me(Authentication authentication) {
        Long userId = (Long) authentication.getDetails();
        Patient patient = service.getByUserId(userId);
        return toResponse(patient);
    }

    /**
     * PATIENT updates their own profile.
     * PUT /patients/me
     */
    @PutMapping("/me")
    public PatientResponse updateMe(@Valid @RequestBody PatientRequest request,
                                    Authentication authentication) {

        Long userId = (Long) authentication.getDetails();

        LocalDate dob = convertLocalDate(request.getDateOfBirth());
        Gender gender = convertGender(request.getGender());

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

    // ---------------- ADMIN ENDPOINTS ----------------
    /**
     * ADMIN: get all patients
     * GET /patients
     */

    @GetMapping
    public ResponseEntity<List<PatientResponse>> getAll() {
        List<Patient> patients = service.getAll();
        List<PatientResponse> mappedPatients = patients.stream().map(this::toResponse).toList();
        return ResponseEntity.ok(mappedPatients);
    }

    /**
     * ADMIN: get a single patient by id
     * GET /patients/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<PatientResponse> getById(@PathVariable Long id) {
        Patient patient = service.getById(id);
        PatientResponse response = toResponse(patient);
        return ResponseEntity.ok(response);
    }
}
