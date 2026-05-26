package com.flamingo.qa.ui.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegistrationFormData {
    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private String mobile;
    private String dobDay;
    private String dobMonth;
    private String dobYear;
    private String subject;
    private String hobby;
    private String address;
    private String state;
    private String city;
}