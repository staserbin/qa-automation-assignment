package com.flamingo.qa.ui.core;

import com.flamingo.qa.ui.components.RegistrationFormModal;
import com.flamingo.qa.ui.components.SuccessModalComponent;
import com.flamingo.qa.ui.pages.RegistrationFormPage;
import com.flamingo.qa.ui.pages.WebTablesPage;
import com.microsoft.playwright.Page;

public class PageManager {

    private final Page page;

    private RegistrationFormPage registrationFormPage;
    private WebTablesPage webTablesPage;
    private RegistrationFormModal registrationFormModal;
    private SuccessModalComponent successModalComponent;

    public PageManager(Page page) {
        this.page = page;
    }

    public RegistrationFormPage registrationFormPage() {
        if (registrationFormPage == null) {
            registrationFormPage = new RegistrationFormPage(page);
        }
        return registrationFormPage;
    }

    public WebTablesPage webTablesPage() {
        if (webTablesPage == null) {
            webTablesPage = new WebTablesPage(page);
        }
        return webTablesPage;
    }

    public RegistrationFormModal registrationFormModal() {
        if (registrationFormModal == null) {
            registrationFormModal = new RegistrationFormModal(page);
        }
        return registrationFormModal;
    }

    public SuccessModalComponent successModalComponent() {
        if (successModalComponent == null) {
            successModalComponent = new SuccessModalComponent(page);
        }
        return successModalComponent;
    }
}