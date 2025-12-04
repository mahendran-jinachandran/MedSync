package com.medsync.doctor_service.infrastructure.persistence.doctor;

import com.medsync.doctor_service.domain.doctor.Specialization;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Entity
@Data
@Table(name = "doctors")
public class DoctorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String fullName;

    @Enumerated(EnumType.STRING)
    private Specialization specialization;

    private String qualifications;
    private int yearsOfExperience;

    private String clinicName;
    private String clinicAddress;
    private String city;

    private String phone;
    private String email;
    private String about;

    private Instant createdAt;
    private Instant updatedAt;
}
