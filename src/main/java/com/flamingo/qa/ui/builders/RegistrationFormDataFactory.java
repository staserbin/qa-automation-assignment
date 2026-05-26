package com.flamingo.qa.ui.builders;

import com.flamingo.qa.ui.model.RegistrationFormData;
import com.github.javafaker.Faker;

public class RegistrationFormDataFactory {

    private RegistrationFormDataFactory() {}

    private static final Faker faker = new Faker();

    public static RegistrationFormData defaultStudent() {
        return RegistrationFormData.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(faker.internet().emailAddress())
                .gender("Male")
                .mobile(faker.number().digits(10))
                .dobDay("15")
                .dobMonth("May")
                .dobYear("1990")
                .subject("Maths")
                .hobby("Sports")
                .address(faker.address().streetAddress())
                .state("NCR")
                .city("Delhi")
                .build();
    }
}