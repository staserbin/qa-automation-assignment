package com.flamingo.qa.ui.components;

import com.flamingo.qa.ui.model.TableRecord;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;

public class RegistrationFormModal {

    private final Page page;

    public RegistrationFormModal(Page page) {
        this.page = page;
    }

    @Step("Fill registration form with record data")
    public RegistrationFormModal fillForm(TableRecord record) {
        page.locator("#firstName").fill(record.getFirstName());
        page.locator("#lastName").fill(record.getLastName());
        page.locator("#userEmail").fill(record.getEmail());
        page.locator("#age").fill(record.getAge());
        page.locator("#salary").fill(record.getSalary());
        page.locator("#department").fill(record.getDepartment());
        return this;
    }

    @Step("Submit registration form")
    public void submit() {
        page.locator("#submit").click();
    }
}