package com.flamingo.qa.ui.pages;

import com.flamingo.qa.ui.core.PlaywrightConfig;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;

public class WebTablesPage {

    private final Page page;

    public WebTablesPage(Page page) {
        this.page = page;
    }

    @Step("Navigate to Web Tables page")
    public WebTablesPage navigate() {
        page.navigate(PlaywrightConfig.getBaseUrl() + "/webtables");
        page.waitForSelector("table.table");
        page.evaluate("document.getElementById('fixedban')?.remove()");
        return this;
    }

    @Step("Click 'Add' button to open registration form")
    public WebTablesPage clickAddButton() {
        page.locator("#addNewRecordButton").click();
        page.waitForSelector("#firstName");
        return this;
    }

    @Step("Search for: {searchTerm}")
    public WebTablesPage search(String searchTerm) {
        page.locator("#searchBox").fill(searchTerm);
        page.waitForFunction("term => document.querySelectorAll('tbody tr').length > 0", searchTerm);
        return this;
    }

    @Step("Click edit button for row containing: {rowIdentifier}")
    public WebTablesPage clickEditForRow(String rowIdentifier) {
        getRowByText(rowIdentifier)
                .locator("[title='Edit']")
                .click();
        page.waitForSelector("#firstName");
        return this;
    }

    @Step("Click delete button for row containing: {rowIdentifier}")
    public WebTablesPage clickDeleteForRow(String rowIdentifier) {
        getRowByText(rowIdentifier)
                .locator("[title='Delete']")
                .click();
        return this;
    }

    public boolean isRecordVisible(String text) {
        return page.locator("tbody td").filter(
                new Locator.FilterOptions().setHasText(text)
        ).count() > 0;
    }


    //==================================================================================================================

    public int getVisibleRowCount() {
        return page.locator("tbody tr").count();
    }

    private Locator getRowByText(String text) {
        return page.locator("tbody tr").filter(
                new Locator.FilterOptions().setHasText(text)
        ).first();
    }
}