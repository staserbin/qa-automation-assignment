package com.flamingo.qa.ui.pages;

import com.flamingo.qa.ui.components.SuccessModalComponent;
import com.flamingo.qa.ui.core.PlaywrightConfig;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;

import java.nio.file.Path;

public class RegistrationFormPage {

    private final Page page;

    public RegistrationFormPage(Page page) {
        this.page = page;
    }

    @Step("Navigate to Practice Form page")
    public RegistrationFormPage navigate() {
        page.navigate(PlaywrightConfig.getBaseUrl() + "/automation-practice-form");
        page.waitForSelector("#firstName");
        page.evaluate("document.getElementById('fixedban')?.remove()");
        page.evaluate("document.getElementById('ad-container')?.remove()");
        return this;
    }

    @Step("Fill first name: {firstName}")
    public RegistrationFormPage fillFirstName(String firstName) {
        page.locator("#firstName").fill(firstName);
        return this;
    }

    @Step("Fill last name: {lastName}")
    public RegistrationFormPage fillLastName(String lastName) {
        page.locator("#lastName").fill(lastName);
        return this;
    }

    @Step("Fill email: {email}")
    public RegistrationFormPage fillEmail(String email) {
        page.locator("#userEmail").fill(email);
        return this;
    }

    @Step("Select gender: {gender}")
    public RegistrationFormPage selectGender(String gender) {
        page.locator("label[for='gender-radio-" + genderIndex(gender) + "']")
                .click(new com.microsoft.playwright.Locator.ClickOptions().setForce(true));
        return this;
    }

    @Step("Fill mobile number: {mobile}")
    public RegistrationFormPage fillMobile(String mobile) {
        page.locator("#userNumber").fill(mobile);
        return this;
    }

    @Step("Set date of birth: {day} {month} {year}")
    public RegistrationFormPage setDateOfBirth(String day, String month, String year) {
        page.locator("#dateOfBirthInput").click();
        page.locator(".react-datepicker__month-select").selectOption(month);
        page.locator(".react-datepicker__year-select").selectOption(year);
        page.locator(String.format(".react-datepicker__day--0%s:not(.react-datepicker__day--outside-month)", day))
                .first()
                .click();
        return this;
    }

    @Step("Add subject: {subject}")
    public RegistrationFormPage addSubject(String subject) {
        page.locator("#subjectsInput").fill(subject);
        page.locator(".subjects-auto-complete__option")
                .first()
                .click();
        return this;
    }

    @Step("Select hobby: {hobby}")
    public RegistrationFormPage selectHobby(String hobby) {
        page.locator("label[for='hobbies-checkbox-" + hobbyIndex(hobby) + "']")
                .click(new com.microsoft.playwright.Locator.ClickOptions().setForce(true));
        return this;
    }

    @Step("Upload file: {filePath}")
    public RegistrationFormPage uploadFile(Path filePath) {
        page.locator("#uploadPicture").setInputFiles(filePath);
        return this;
    }

    @Step("Fill current address: {address}")
    public RegistrationFormPage fillCurrentAddress(String address) {
        page.locator("#currentAddress").fill(address);
        return this;
    }

    @Step("Select state: {state}")
    public RegistrationFormPage selectState(String state) {
        page.locator("#state").click();
        page.waitForSelector("[id^='react-select-3-option']");
        page.locator("[role='option']")
                .filter(new com.microsoft.playwright.Locator.FilterOptions().setHasText(state))
                .first()
                .click();
        return this;
    }

    @Step("Select city: {city}")
    public RegistrationFormPage selectCity(String city) {
        page.locator("#city").click();
        page.waitForSelector("[id^='react-select-4-option']");
        page.locator("[role='option']")
                .filter(new com.microsoft.playwright.Locator.FilterOptions().setHasText(city))
                .first()
                .click();
        return this;
    }

    @Step("Submit the form")
    public SuccessModalComponent submit() {
        page.locator("#submit").scrollIntoViewIfNeeded();
        page.locator("#submit").click();
        return new SuccessModalComponent(page);
    }


    //==================================================================================================================

    private String genderIndex(String gender) {
        return switch (gender.toLowerCase()) {
            case "male"   -> "1";
            case "female" -> "2";
            case "other"  -> "3";
            default -> throw new IllegalArgumentException("Unknown gender: " + gender);
        };
    }

    private String hobbyIndex(String hobby) {
        return switch (hobby.toLowerCase()) {
            case "sports"  -> "1";
            case "reading" -> "2";
            case "music"   -> "3";
            default -> throw new IllegalArgumentException("Unknown hobby: " + hobby);
        };
    }
}