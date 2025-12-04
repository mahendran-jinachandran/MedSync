package com.medsync.doctor_service.api.controller;

import com.medsync.doctor_service.api.dto.DoctorRequest;
import com.medsync.doctor_service.api.dto.DoctorResponse;
import com.medsync.doctor_service.application.doctor.DoctorCreateCommand;
import com.medsync.doctor_service.application.doctor.DoctorService;
import com.medsync.doctor_service.application.doctor.DoctorUpdateCommand;
import com.medsync.doctor_service.domain.doctor.Doctor;
import com.medsync.doctor_service.domain.doctor.Specialization;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService service;

    public DoctorController(DoctorService service) {
        this.service = service;
    }

    // Create doctor profile for logged-in user
    @PostMapping
    public DoctorResponse create(@Valid @RequestBody DoctorRequest request,
                                 Authentication authentication) {

        Long userId = (Long) authentication.getDetails();

        Specialization specialization = mapSpecialization(request.getSpecialization());

        DoctorCreateCommand cmd = new DoctorCreateCommand(
                userId,
                request.getFullName(),
                specialization,
                request.getQualifications(),
                request.getYearsOfExperience(),
                request.getClinicName(),
                request.getClinicAddress(),
                request.getCity(),
                request.getPhone(),
                request.getEmail(),
                request.getAbout()
        );

        Doctor doctor = service.createDoctor(cmd);
        return toResponse(doctor);
    }

    // Get current doctor's own profile
    @GetMapping("/me")
    public DoctorResponse me(Authentication authentication) {
        Long userId = (Long) authentication.getDetails();
        Doctor doctor = service.getByUserId(userId);
        return toResponse(doctor);
    }

    // Update current doctor's profile
    @PutMapping("/me")
    public DoctorResponse updateMe(@Valid @RequestBody DoctorRequest request,
                                   Authentication authentication) {

        Long userId = (Long) authentication.getDetails();
        Specialization specialization = mapSpecialization(request.getSpecialization());

        DoctorUpdateCommand cmd = new DoctorUpdateCommand(
                request.getFullName(),
                specialization,
                request.getQualifications(),
                request.getYearsOfExperience(),
                request.getClinicName(),
                request.getClinicAddress(),
                request.getCity(),
                request.getPhone(),
                request.getEmail(),
                request.getAbout()
        );

        Doctor updated = service.updateForUser(userId, cmd);
        return toResponse(updated);
    }

    // Public: get doctor by id
    @GetMapping("/{id}")
    public DoctorResponse getById(@PathVariable Long id) {
        Doctor doctor = service.getById(id);
        return toResponse(doctor);
    }

    // Public: list/search doctors (basic v1)
    @GetMapping
    public List<DoctorResponse> search(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String specialization
    ) {
        List<Doctor> doctors;

        if (city != null && specialization != null) {
            Specialization spec = mapSpecialization(specialization);
            doctors = service.searchByCityAndSpecialization(city, spec);
        } else {
            doctors = service.listAll();
        }

        return doctors.stream()
                .map(this::toResponse)
                .toList();
    }

    // helper: string -> enum with uppercase
    private Specialization mapSpecialization(String value) {
        if (value == null || value.isBlank()) {
            return Specialization.OTHER;
        }
        return Specialization.valueOf(value.trim().toUpperCase());
    }

    private DoctorResponse toResponse(Doctor d) {
        DoctorResponse res = new DoctorResponse();
        res.setId(d.getId());
        res.setUserId(d.getUserId());
        res.setFullName(d.getFullName());
        res.setSpecialization(d.getSpecialization());
        res.setQualifications(d.getQualifications());
        res.setYearsOfExperience(d.getYearsOfExperience());
        res.setClinicName(d.getClinicName());
        res.setClinicAddress(d.getClinicAddress());
        res.setCity(d.getCity());
        res.setPhone(d.getPhone());
        res.setEmail(d.getEmail());
        res.setAbout(d.getAbout());
        res.setCreatedAt(d.getCreatedAt());
        res.setUpdatedAt(d.getUpdatedAt());
        return res;
    }
}

