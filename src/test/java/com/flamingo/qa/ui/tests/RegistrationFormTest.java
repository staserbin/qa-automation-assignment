package com.flamingo.qa.ui.tests;

import com.flamingo.qa.ui.base.BaseUiTest;
import com.flamingo.qa.ui.builders.RegistrationFormDataFactory;
import com.flamingo.qa.ui.components.SuccessModalComponent;
import com.flamingo.qa.ui.model.RegistrationFormData;
import com.flamingo.qa.ui.pages.RegistrationFormPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("ui")
@Feature("Student Registration Form")
class RegistrationFormTest extends BaseUiTest {

    private static final Path UPLOAD_FILE = Paths.get("src/test/resources/upload/test-file.txt");

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that submitting the student registration form with valid data shows the success modal " +
            "with correct student name, email and mobile")
    void submitPracticeFormSuccessTest() {
        RegistrationFormData data = RegistrationFormDataFactory.defaultStudent();
        RegistrationFormPage formPage = new RegistrationFormPage(page);

        SuccessModalComponent modal = formPage
                .navigate()
                .fillFirstName(data.getFirstName())
                .fillLastName(data.getLastName())
                .fillEmail(data.getEmail())
                .selectGender(data.getGender())
                .fillMobile(data.getMobile())
                .setDateOfBirth(data.getDobDay(), data.getDobMonth(), data.getDobYear())
                .addSubject(data.getSubject())
                .selectHobby(data.getHobby())
                .uploadFile(UPLOAD_FILE)
                .fillCurrentAddress(data.getAddress())
                .selectState(data.getState())
                .selectCity(data.getCity())
                .submit();

        assertThat(modal.isVisible())
                .as("Success modal should be visible after form submission")
                .isTrue();
        assertThat(modal.getStudentName())
                .as("Student name in modal should match submitted first and last name")
                .isEqualTo(data.getFirstName() + " " + data.getLastName());
        assertThat(modal.getStudentEmail())
                .as("Student email in modal should match submitted email")
                .isEqualTo(data.getEmail());
        assertThat(modal.getMobileNumber())
                .as("Mobile number in modal should match submitted mobile")
                .isEqualTo(data.getMobile());
    }
}