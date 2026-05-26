package com.flamingo.qa.ui.tests;

import com.flamingo.qa.ui.base.BaseUiTest;
import com.flamingo.qa.ui.builders.TableRecordFactory;
import com.flamingo.qa.ui.model.TableRecord;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("ui")
@Feature("Web Tables")
class WebTablesTest extends BaseUiTest {

    @BeforeEach
    void navigateToPage() {
        pages.webTablesPage().navigate();
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that clicking Add and filling the form adds a new record visible in the table")
    void addNewRecordTest() {
        TableRecord record = TableRecordFactory.newRecord();

        pages.webTablesPage()
                .clickAddButton();

        pages.registrationFormModal()
                .fillForm(record)
                .submit();

        assertThat(pages.webTablesPage().isRecordVisible(record.getFirstName()))
                .as("Newly added record should be visible in the table")
                .isTrue();
        assertThat(pages.webTablesPage().isRecordVisible(record.getEmail()))
                .as("Newly added record email should be visible in the table")
                .isTrue();
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that editing an existing record updates the data correctly in the table")
    void editExistingRecordTest() {
        TableRecord original = TableRecordFactory.newRecord();
        TableRecord updated = TableRecordFactory.updatedRecord();

        pages.webTablesPage()
                .clickAddButton();

        pages.registrationFormModal()
                .fillForm(original)
                .submit();

        pages.webTablesPage()
                .clickEditForRow(original.getFirstName());

        pages.registrationFormModal()
                .fillForm(updated)
                .submit();

        assertThat(pages.webTablesPage().isRecordVisible(updated.getEmail()))
                .as("Updated email should be visible in the table after edit")
                .isTrue();
        assertThat(pages.webTablesPage().isRecordVisible(updated.getSalary()))
                .as("Updated salary should be visible in the table after edit")
                .isTrue();
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that deleting a record removes it from the table")
    void deleteRecordTest() {
        TableRecord record = TableRecordFactory.newRecord();

        pages.webTablesPage()
                .clickAddButton();

        pages.registrationFormModal()
                .fillForm(record)
                .submit();

        assertThat(pages.webTablesPage().isRecordVisible(record.getEmail()))
                .as("Record should be visible before deletion")
                .isTrue();

        pages.webTablesPage()
                .clickDeleteForRow(record.getFirstName());

        assertThat(pages.webTablesPage().isRecordVisible(record.getEmail()))
                .as("Record should not be visible after deletion")
                .isFalse();
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that searching by a term filters the table to show only matching records")
    void searchFunctionalityTest() {
        pages.webTablesPage().search("Cierra");

        assertThat(pages.webTablesPage().isRecordVisible("Cierra"))
                .as("Search result should contain the searched term")
                .isTrue();
        assertThat(pages.webTablesPage().getVisibleRowCount())
                .as("Search should filter table to show only 1 matching record")
                .isEqualTo(1);
    }
}