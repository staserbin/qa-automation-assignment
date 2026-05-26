package com.flamingo.qa.api.graphql;

import com.flamingo.qa.api.base.BaseApiTest;
import com.flamingo.qa.api.core.graphql.QueryLoader;
import com.flamingo.qa.api.models.graphql.GraphQlRequest;
import com.flamingo.qa.api.models.graphql.GraphQlResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("api")
@Tag("graphql")
@Feature("GraphQL Negative Scenarios")
class GraphQlNegativeTests extends BaseApiTest {

    private static final String NON_EXISTENT_ID = "nonexistent000000000000000";

    private static Stream<Arguments> invalidQueriesProvider() {
        return Stream.of(
                Arguments.of(
                        "{ movies( { id title }",
                        "Malformed query - unclosed args syntax error"
                ),
                Arguments.of(
                        "{ movies(first: 1) { id nonExistentField } }",
                        "Non-existent field - validation error on unknown field"
                ),
                Arguments.of(
                        "{ nonExistentRootQuery { id } }",
                        "Non-existent root query - validation error on unknown type"
                )
        );
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that querying a movie with a non-existent ID returns HTTP 200 " +
            "with data.movie = null and no errors array")
    void getMovieByNonExistentIdTest() {
        GraphQlRequest request = buildMovieByIdRequest(NON_EXISTENT_ID);
        GraphQlResponse response = graphQlClient.execute("Get movie by non-existent ID", request);
        verifyNonExistentIdResponse(response);
    }

    @ParameterizedTest(name = "[{index}] {1}")
    @MethodSource("invalidQueriesProvider")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that various types of invalid GraphQL queries return a non-empty errors[].message and no data field")
    void invalidQueryReturnsErrorsTest(String query, String testDescription) {
        GraphQlRequest request = buildInvalidQueryRequest(query);
        GraphQlResponse response = graphQlClient.executeExpectingError(testDescription, request);
        verifyErrorResponseWithNoData(response);
    }


    //==================================================================================================================

    @Step("Build movie by ID request: id={id}")
    private GraphQlRequest buildMovieByIdRequest(String id) {
        return GraphQlRequest.builder()
                .query(QueryLoader.load("get_movie_by_id.graphql"))
                .variables(Map.of("id", id))
                .build();
    }

    @Step("Build invalid GraphQL query request: {testDescription}")
    private GraphQlRequest buildInvalidQueryRequest(String query) {
        return GraphQlRequest.builder()
                .query(query)
                .build();
    }

    @Step("Verify non-existent ID returns HTTP 200 with data.movie = null and no errors")
    private void verifyNonExistentIdResponse(GraphQlResponse response) {
        assertThat(response.hasErrors())
                .as("Non-existent ID shouldn't produce errors array, data.movie is null instead")
                .isFalse();
        assertThat(response.getData().get("movie").isNull())
                .as("'data.movie' should be null for non-existent ID")
                .isTrue();
    }

    @Step("Verify invalid query returns non-empty errors[].message and no data")
    private void verifyErrorResponseWithNoData(GraphQlResponse response) {
        assertThat(response.hasErrors())
                .as("Invalid query should return errors array")
                .isTrue();
        assertThat(response.getErrors().get(0).getMessage())
                .as("'errors[0].message' should be present and non-empty")
                .isNotBlank();
        assertThat(response.hasData())
                .as("Invalid query should not return data")
                .isFalse();
    }
}