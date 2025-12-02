package com.medsync.patient_service.api.dto;

import jakarta.validation.constraints.NotBlank;

public class PatientRequest {

    @NotBlank
    private String fullName;

    // keep as String, we'll parse it manually
    private String dateOfBirth;   // format: yyyy-MM-dd

    // keep as String, we'll map to Gender enum manually
    private String gender;        // values: MALE, FEMALE, OTHER, UNKNOWN

    private String phone;
    private String address;
    private String allergies;
    private String chronicConditions;

    // ----- getters & setters -----

    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getAllergies() {
        return allergies;
    }
    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getChronicConditions() {
        return chronicConditions;
    }
    public void setChronicConditions(String chronicConditions) {
        this.chronicConditions = chronicConditions;
    }
}
