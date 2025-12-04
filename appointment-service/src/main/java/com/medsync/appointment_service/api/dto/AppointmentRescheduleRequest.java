package com.medsync.appointment_service.api.dto;

import jakarta.validation.constraints.NotBlank;

public class AppointmentRescheduleRequest {

    @NotBlank
    private String startTime;

    @NotBlank
    private String endTime;

    // getters & setters

    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }

    public String getEndTime() { return endTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }
}
