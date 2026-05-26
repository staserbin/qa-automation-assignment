package com.flamingo.qa.api.models.graphql;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GraphQlError {

    @JsonProperty("message")
    private String message;

    @JsonProperty("extensions")
    private Object extensions;
}