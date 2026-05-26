package com.flamingo.qa.ui.builders;

import com.flamingo.qa.ui.model.TableRecord;

public class TableRecordFactory {

    private TableRecordFactory() {}

    public static TableRecord newRecord() {
        return TableRecord.builder()
                .firstName("John")
                .lastName("Smith")
                .age("25")
                .email("john.smith.qa@test.com")
                .salary("60000")
                .department("QA")
                .build();
    }

    public static TableRecord updatedRecord() {
        return TableRecord.builder()
                .firstName("John")
                .lastName("Smith")
                .age("30")
                .email("john.smith.dev@test.com")
                .salary("75000")
                .department("Engineering")
                .build();
    }
}