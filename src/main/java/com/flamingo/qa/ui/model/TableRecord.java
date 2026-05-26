package com.flamingo.qa.ui.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TableRecord {

    private String firstName;
    private String lastName;
    private String age;
    private String email;
    private String salary;
    private String department;
}