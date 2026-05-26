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

@Tag("graphql")
@Feature("GraphQL Positive Scenarios")
class GraphQlPositiveTests extends BaseApiTest {

    private static final String EXISTING_MOVIE_ID = "clq16555f0nqq0ak8fvxe2c0d";

    private static Stream<Arguments> paginationProvider() {
        return Stream.of(
                Arguments.of(1, 0, 1),  // 1st page, single result
                Arguments.of(2, 0, 2),  // 1st page, 2 results
                Arguments.of(1, 1, 1)   // 2nd page, confirms skip offset works
        );
    }


    @ParameterizedTest(name = "[{index}] first={0}, skip={1} -> expects {2} movie(s)")
    @MethodSource("paginationProvider")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that movies query with various first/skip combinations returns " +
            "the correct number of results with non-empty id and title fields")
    void getMoviesWithPaginationTest(int first, int skip, int expectedSize) {
        GraphQlRequest request = buildPaginatedRequest(first, skip);
        GraphQlResponse response = graphQlClient.execute(
                "Get movies paginated (first=%d, skip=%d)".formatted(first, skip), request
        );
        verifyPaginatedMoviesResponse(response, expectedSize);
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that movie query with a valid ID passed as a GraphQL variable returns " +
            "the correct movie with matching id and title")
    void getMovieByIdWithVariablesTest() {
        GraphQlRequest request = buildMovieByIdRequest(EXISTING_MOVIE_ID);
        GraphQlResponse response = graphQlClient.execute("Get movie by ID with variables", request);
        verifyMovieByIdResponse(response, EXISTING_MOVIE_ID, "Jaws");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that movie query resolves nested fields across types: " +
            "movie -> publishedBy -> name returns a non-null publisher name")
    void getMovieWithNestedPublishedByTest() {
        GraphQlRequest request = buildNestedMovieRequest(EXISTING_MOVIE_ID);
        GraphQlResponse response = graphQlClient.execute("Get movie with nested publishedBy", request);
        verifyNestedPublishedByResponse(response, "Lo Etheridge");
    }


    //==================================================================================================================

    @Step("Build paginated movies request: first={first}, skip={skip}")
    private GraphQlRequest buildPaginatedRequest(int first, int skip) {
        return GraphQlRequest.builder()
                .query(QueryLoader.load("get_movies_paginated.graphql"))
                .variables(Map.of("first", first, "skip", skip))
                .build();
    }

    @Step("Build get movie by ID request: id={id}")
    private GraphQlRequest buildMovieByIdRequest(String id) {
        return GraphQlRequest.builder()
                .query(QueryLoader.load("get_movie_by_id.graphql"))
                .variables(Map.of("id", id))
                .build();
    }

    @Step("Build nested movie request: id={id}")
    private GraphQlRequest buildNestedMovieRequest(String id) {
        return GraphQlRequest.builder()
                .query(QueryLoader.load("get_movie_nested.graphql"))
                .variables(Map.of("id", id))
                .build();
    }

    @Step("Verify paginated movies response contains {expectedSize} movie(s) with non-empty id and title")
    private void verifyPaginatedMoviesResponse(GraphQlResponse response, int expectedSize) {
        assertThat(response.hasErrors())
                .as("Response shouldn't contain errors")
                .isFalse();
        assertThat(response.getData().get("movies").isArray())
                .as("'movies' field should be an array")
                .isTrue();
        assertThat(response.getData().get("movies").size())
                .as("Should return exactly %d movie(s)".formatted(expectedSize))
                .isEqualTo(expectedSize);
        assertThat(response.getData().get("movies").get(0).get("id").asText())
                .as("Each movie should have a non-empty id")
                .isNotBlank();
        assertThat(response.getData().get("movies").get(0).get("title").asText())
                .as("Each movie should have a non-empty title")
                .isNotBlank();
    }

    @Step("Verify movie response matches id={expectedId} and title={expectedTitle}")
    private void verifyMovieByIdResponse(GraphQlResponse response, String expectedId, String expectedTitle) {
        assertThat(response.hasErrors())
                .as("Response shouldn't contain errors")
                .isFalse();
        assertThat(response.getData().get("movie").get("id").asText())
                .as("Returned movie ID should match the requested ID")
                .isEqualTo(expectedId);
        assertThat(response.getData().get("movie").get("title").asText())
                .as("Returned movie title should be '%s'".formatted(expectedTitle))
                .isEqualTo(expectedTitle);
    }

    @Step("Verify nested publishedBy.name equals '{expectedName}'")
    private void verifyNestedPublishedByResponse(GraphQlResponse response, String expectedName) {
        assertThat(response.hasErrors())
                .as("Response shouldn't contain errors")
                .isFalse();
        assertThat(response.getData().get("movie").get("publishedBy").isNull())
                .as("'publishedBy' field should not be null")
                .isFalse();
        assertThat(response.getData().get("movie").get("publishedBy").get("name").asText())
                .as("'publishedBy.name' should be '%s'".formatted(expectedName))
                .isEqualTo(expectedName);
    }
}