package com.algaworks.algafood;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import com.algaworks.algafood.domain.model.Kitchen;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.repository.KitchenRepository;
import com.algaworks.algafood.domain.repository.RestaurantRepository;
import com.algaworks.algafood.util.DatabaseCleaner;
import com.algaworks.algafood.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class RegistrationRestaurantIT {

	private static final String PROBLEM_TYPE_BUSINESS_RULE_VIOLATION = "Violation of business rule";

    private static final String DATA_INVALID_PROBLEM_TITLE = "Invalid data";

    private static final int RESTAURANT_ID_INEXIST = 100;

    @LocalServerPort
    private int port;
    
    @Autowired
    private DatabaseCleaner databaseCleaner;
    
    @Autowired
    private KitchenRepository kitchenRepository;
    
    @Autowired
    private RestaurantRepository restauranteRepository;
    
    private String jsonRestaurantCorrect;
    private String jsonRestaurantWithoutShipping;
    private String jsonRestaurantWithoutKitchen;
    private String jsonRestaurantWithKitchenInexist;
    
    private Restaurant burgerTopRestaurant;
    
    @BeforeEach
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/restaurants";

        jsonRestaurantCorrect = ResourceUtils.getContentFromResource(
                "/json/correct/restaurant-new-york-barbecue-create.json");
        
        jsonRestaurantWithoutShipping = ResourceUtils.getContentFromResource(
                "/json/incorrect/restaurant-new-york-barbecue-without-shipping.json");
        
        jsonRestaurantWithoutKitchen = ResourceUtils.getContentFromResource(
                "/json/incorrect/restaurant-new-york-barbecue-without-kitchen.json");
        
        jsonRestaurantWithKitchenInexist = ResourceUtils.getContentFromResource(
                "/json/incorrect/restaurant-new-york-barbecue-with-kitchen-nonexist.json");
        
        databaseCleaner.clearTables();
        prepararDados();
    }
    
    @Test
    public void shouldReturnStatus200WhenListRestaurants() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .statusCode(HttpStatus.OK.value());
    }
    
    @Test
    public void shouldReturnStatus201WhenAddRestaurant() {
        given()
            .body(jsonRestaurantCorrect)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.CREATED.value());
    }
    
    @Test
    public void shouldReturnStatus400WhenAddRestaurantWithoutShipping() {
        given()
            .body(jsonRestaurantWithoutShipping)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("title", equalTo(DATA_INVALID_PROBLEM_TITLE));
    }

    @Test
    public void shouldReturnStatus400WhenAddRestaurantWithoutKitchen() {
        given()
            .body(jsonRestaurantWithoutKitchen)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("title", equalTo(DATA_INVALID_PROBLEM_TITLE));
    }
    
    @Test
    public void shouldReturnStatus400WhenAddRestaurantWithKitchenInexist() {
        given()
            .body(jsonRestaurantWithKitchenInexist)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("title", equalTo(PROBLEM_TYPE_BUSINESS_RULE_VIOLATION));
    }
    
    @Test
    public void shouldReturnResponseAndStatusCorrectWhenFindRestaurantExist() {
        given()
            .pathParam("restaurantId", burgerTopRestaurant.getId())
            .accept(ContentType.JSON)
        .when()
            .get("/{restaurantId}")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("name", equalTo(burgerTopRestaurant.getName()));
    }
    
    @Test
    public void houldReturnStatus404WhenFindRestaurantNonexist() {
        given()
            .pathParam("restaurantId", RESTAURANT_ID_INEXIST)
            .accept(ContentType.JSON)
        .when()
            .get("/{restaurantId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }
    
    private void prepararDados() {
        Kitchen kitchenBrasileira = new Kitchen();
        kitchenBrasileira.setName("Brasileira");
        kitchenRepository.save(kitchenBrasileira);

        Kitchen kitchenAmericana = new Kitchen();
        kitchenAmericana.setName("Americana");
        kitchenRepository.save(kitchenAmericana);
        
        burgerTopRestaurant = new Restaurant();
        burgerTopRestaurant.setName("Burger Top");
        burgerTopRestaurant.setFreightRate(new BigDecimal(10));
        burgerTopRestaurant.setKitchen(kitchenAmericana);
        restauranteRepository.save(burgerTopRestaurant);
        
        Restaurant comidaMineiraRestaurante = new Restaurant();
        comidaMineiraRestaurante.setName("Comida Mineira");
        comidaMineiraRestaurante.setFreightRate(new BigDecimal(10));
        comidaMineiraRestaurante.setKitchen(kitchenBrasileira);
        restauranteRepository.save(comidaMineiraRestaurante);
    }            
}
