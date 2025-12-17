package com.medsync.patient_service.api.controller;

import com.medsync.patient_service.api.dto.PatientRequest;
import com.medsync.patient_service.api.dto.PatientResponse;
import com.medsync.patient_service.application.patient.PatientCreateCommand;
import com.medsync.patient_service.application.patient.PatientService;
import com.medsync.patient_service.application.patient.PatientUpdateCommand;
import com.medsync.patient_service.domain.patient.Gender;
import com.medsync.patient_service.domain.patient.Patient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/patients")
@Tag(name = "Patient Service", description = "APIs for interacting with Patients")
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
    @Operation(summary = "Patient creating their profile")
    public ResponseEntity<PatientResponse> create(@Valid @RequestBody PatientRequest request,
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
        return ResponseEntity.ok(toResponse(patient));
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
    @Operation(summary = "Patient viewing their own profile")
    public ResponseEntity<PatientResponse> me(Authentication authentication) {
        Long userId = (Long) authentication.getDetails();
        Patient patient = service.getByUserId(userId);
        return ResponseEntity.ok(toResponse(patient));
    }

    /**
     * PATIENT updates their own profile.
     * PUT /patients/me
     */
    @PutMapping("/me")
    @Operation(summary = "Patient updates their own profile")
    public ResponseEntity<PatientResponse> updateMe(@Valid @RequestBody PatientRequest request,
                                    Authentication authentication) {

        Long userId = (Long) authentication.getDetails();

        LocalDate dob = convertLocalDate(request.getDateOfBirth());
        Gender gender = convertGender(request.getGender());

        PatientUpdateCommand cmd = new PatientUpdateCommand(
                userId,
                request.getFullName(),
                dob,
                gender,
                request.getPhone(),
                request.getAddress(),
                request.getAllergies(),
                request.getChronicConditions()
        );

        Patient updated = service.updateForUser(cmd);
        return ResponseEntity.ok(toResponse(updated));
    }

    private PatientResponse toResponse(Patient p) {

        return new PatientResponse(
                p.getId(),
                p.getUserId(),
                p.getFullName(),
                p.getDateOfBirth(),
                p.getGender(),
                p.getPhone(),
                p.getAddress(),
                p.getAllergies(),
                p.getChronicConditions(),
                p.getCreatedAt(),
                p.getUpdatedAt()
        );
    }

    // ---------------- ADMIN ENDPOINTS ----------------
    /**
     * ADMIN: get all patients
     * GET /patients
     */

    @GetMapping
    @Operation(summary = "List all the patients")
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
    @Operation(summary = "Patient by Id")
    public ResponseEntity<PatientResponse> getById(@PathVariable Long id) {
        Patient patient = service.getById(id);
        PatientResponse response = toResponse(patient);
        return ResponseEntity.ok(response);
    }
}
