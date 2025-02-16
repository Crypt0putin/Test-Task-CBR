package api;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import config.TestConfig;
import io.qameta.allure.Story;
import static io.restassured.RestAssured.given;

class HttpBinTests {

    @Test
    @Story("Проверка API HttpBin")
    @DisplayName("Проверка задержки ответа")
    void checkResponseDelayTest() {
        Specifications.installSpecification(Specifications.requestSpec(TestConfig.HTTPBIN_URL), Specifications.responseSpecOK200());
        int delay = 3;
        given()
            .when()
            .post("/delay/{delay}", delay)
            .then()
            .assertThat()
            .time(greaterThanOrEqualTo((long) delay * 1000));
    }

    @Test
    void testGetMethod() {
        given()
            .when()
            .get("/get")
            .then()
            .statusCode(200);
    }

    @Test
    void testDelayEndpoint() {
        int delay = 3;
        long startTime = System.currentTimeMillis();
        
        given()
            .baseUri(TestConfig.HTTPBIN_URL)
            .relaxedHTTPSValidation()
            .when()
            .get("/delay/" + delay)
            .then()
            .time(lessThanOrEqualTo((long) (delay * 1000 * 2)))
            .statusCode(200);
        
        long responseTime = System.currentTimeMillis() - startTime;
        System.out.println("Фактическое время ответа: " + responseTime + " мс");
    }

    @Test
    void testStatusCode() {
        given()
            .when()
            .get("/get")
            .then()
            .statusCode(200);
    }
} 