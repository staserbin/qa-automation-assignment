package com.flamingo.qa.api.models.graphql;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GraphQlResponse {

    @JsonProperty("data")
    private JsonNode data;

    @JsonProperty("errors")
    private List<GraphQlError> errors;

    public boolean hasErrors() {
        return errors != null && !errors.isEmpty();
    }

    public boolean hasData() {
        return data != null && !data.isNull();
    }
}