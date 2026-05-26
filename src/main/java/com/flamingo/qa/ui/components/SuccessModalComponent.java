package com.flamingo.qa.ui.components;

import com.microsoft.playwright.Page;

public class SuccessModalComponent {

    private final Page page;

    public SuccessModalComponent(Page page) {
        this.page = page;
    }

    public boolean isVisible() {
        page.waitForSelector("#example-modal-sizes-title-lg");
        return page.locator("#example-modal-sizes-title-lg").isVisible();
    }

    private String getValueByLabel(String label) {
        return page.locator(".table-responsive td")
                .filter(new com.microsoft.playwright.Locator.FilterOptions().setHasText(label))
                .locator("xpath=following-sibling::td")
                .textContent()
                .trim();
    }

    public String getStudentName() {
        return getValueByLabel("Student Name");
    }

    public String getStudentEmail() {
        return getValueByLabel("Student Email");
    }

    public String getMobileNumber() {
        return getValueByLabel("Mobile");
    }

    public void close() {
        page.locator("#closeLargeModal").click();
    }
}