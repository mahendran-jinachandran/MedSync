package com.medsync.appointment_service.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AppointmentRequest {

    @NotNull
    private Long doctorId; // assume doctor USER id for now

    @NotBlank
    private String startTime; // ISO-8601 string, e.g. "2025-12-04T10:00:00Z"

    @NotBlank
    private String endTime;

    private String reason;

    // getters & setters

    public Long getDoctorId() { return doctorId; }
    public void setDoctorId(Long doctorId) { this.doctorId = doctorId; }

    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }

    public String getEndTime() { return endTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}
