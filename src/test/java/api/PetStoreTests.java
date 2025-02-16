package api;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import config.TestConfig;
import io.qameta.allure.Story;
import static io.restassured.RestAssured.given;
import io.restassured.response.Response;

class PetStoreTests {

    @Test
    @Story("Проверка API PetStore")
    @DisplayName("Получение уникальных имен питомцев со статусом 'sold'")
    void getUniquePetNamesWithStatusSoldTest() {
        Specifications.installSpecification(Specifications.requestSpec(TestConfig.PETSTORE_URL), Specifications.responseSpecOK200());
        List<String> petNames = given()
            .param("status", "sold")
            .when()
            .get("/pet/findByStatus")
            .then()
            .extract()
            .jsonPath()
            .getList("name");

        List<String> uniquePetNames = petNames.stream()
            .distinct()
            .collect(Collectors.toList());

        org.junit.jupiter.api.Assertions.assertFalse(
            uniquePetNames.isEmpty(),
            "Список уникальных имен питомцев пуст"
        );

        System.out.println("Уникальные имена питомцев:");
        uniquePetNames.forEach(name -> System.out.println("- " + name));
    }

    @Test
    void testCreatePet() {
        Pet newPet = new Pet();
        newPet.setId(123L);
        newPet.setName("Fluffy");
        newPet.setStatus("available");
        newPet.setCategory(new Category(1L, "dogs"));
        newPet.setPhotoUrls(List.of("https://example.com/photo.jpg"));
        newPet.setTags(List.of(new Tag(1L, "cute")));

        Response response = given()
            .baseUri(TestConfig.PETSTORE_URL)
            .contentType("application/json")
            .body(newPet)
            .when()
            .post("/pet");

        assertEquals(200, response.getStatusCode());
    }
} 