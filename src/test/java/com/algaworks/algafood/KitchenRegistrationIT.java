package com.algaworks.algafood;

import static io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import org.apache.commons.lang3.StringUtils;
//import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import com.algaworks.algafood.domain.model.Kitchen;
import com.algaworks.algafood.domain.repository.KitchenRepository;
import com.algaworks.algafood.util.DatabaseCleaner;
import com.algaworks.algafood.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
class KitchenRegistrationIT {
	
	private static final int KITCHEN_ID_INEXIST = 100;
	private final static String KITCHEN_ARGENTINA = "Argentina";
	private final static String KITCHEN_BRASILEIRA = "Brasileira";
	private final static String PATH_JSON_KITCHEN_CREATE = "/json/correct/kichen-create.json";

	@LocalServerPort
	private int port;
	
//	@Autowired
//	private Flyway flayway; 
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Autowired
	private KitchenRepository kitchenRepository; 
	
	private Kitchen kitchenArgentina;
	private Kitchen kitchenBrasileira;
	private int amountKitchenCreted;
	private String jsonKichenCreate;
	
	@BeforeEach
	public void setUp() {
		enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/kitchens";
		
		jsonKichenCreate = ResourceUtils.getContentFromResource(PATH_JSON_KITCHEN_CREATE);
		
//		flayway.migrate();
		databaseCleaner.clearTables();
		prepareData();
	}
	
	@Test
	public void shouldReturnStatus200WhenListKitchens() {
		given()			
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());		
	}
	
	@Test
	public void shouldReturnAmountKitchensCorrectWhenListKitchens() {
		given()			
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.body(StringUtils.EMPTY, hasSize(amountKitchenCreted));
	}
	
	@Test
	public void shouldReturnStatus201WhenAddKitchens() {
		given()			
			.body(jsonKichenCreate)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());		
	}
	
	@Test
	public void shouldReturnResponseAndStatusCorrectWhenFindKitchenExist() {
		given()			
			.pathParam("kitchenId", kitchenBrasileira.getId())
			.accept(ContentType.JSON)
		.when()
			.get("/{kitchenId}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("name", equalTo(KITCHEN_BRASILEIRA));
	}
	
	@Test
	public void shouldReturnStatus404WhenFindKitchenNonexist() {
		given()			
			.pathParam("kitchenId", KITCHEN_ID_INEXIST)
			.accept(ContentType.JSON)
		.when()
			.get("/{kitchenId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	private void prepareData() {
		kitchenArgentina = new Kitchen();
		kitchenArgentina.setName(KITCHEN_ARGENTINA);
		kitchenRepository.save(kitchenArgentina);
		
		kitchenBrasileira = new Kitchen();
		kitchenBrasileira.setName(KITCHEN_BRASILEIRA);
		kitchenRepository.save(kitchenBrasileira);
		
		amountKitchenCreted = (int) kitchenRepository.count();
	}
}
