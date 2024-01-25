package com.example.interviewgft.prices.infrastructure;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("/data.sql")
public class PriceQueryControllerTest {

    @LocalServerPort
    private int port;

    private static Stream<Arguments> providePriceQueryParameters() {
        return Stream.of(
                Arguments.of("2020-06-14-10.00.00", 35455, 1, 35.5F),
                Arguments.of("2020-06-14-16.00.00", 35455, 1, 25.45F),
                Arguments.of("2020-06-14-21.00.00", 35455, 1, 35.5F),
                Arguments.of("2020-06-15-10.00.00", 35455, 1, 30.5F),
                Arguments.of("2020-06-16-21.00.00", 35455, 1, 38.95F)
        );
    }

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
    }

    @ParameterizedTest
    @MethodSource("providePriceQueryParameters")
    public void testPriceQuery(String date, int productId, int brandId, float expectedPrice) {
        given()
                .param("date", date)
                .param("productId", productId)
                .param("brandId", brandId)
                .when()
                .get("/price")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("price", equalTo(expectedPrice));
    }

    @Test
    public void whenPriceNotFoundThenReturnsNoContent() {
        given()
                .param("date", "2023-01-01-10.00.00")
                .param("productId", 2)
                .param("brandId", 2)
                .when()
                .get("/price")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void whenInvalidDateThenReturnsBadRequest() {
        given()
                .param("date", "invalid-date")
                .param("productId", 1)
                .param("brandId", 1)
                .when()
                .get("/price")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

}
