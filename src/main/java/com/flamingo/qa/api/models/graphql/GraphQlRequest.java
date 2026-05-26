package com.flamingo.qa.api.models.graphql;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GraphQlRequest {

    @JsonProperty("query")
    private String query;

    @JsonProperty("variables")
    private Map<String, Object> variables;
}