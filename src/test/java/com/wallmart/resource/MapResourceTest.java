package com.wallmart.resource;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.jayway.restassured.RestAssured;
import com.wallmart.Bootstrap;
import com.wallmart.IntegrationTest;

@Category(IntegrationTest.class)
public class MapResourceTest {

	@BeforeClass
	public static void tearUp() {

		new Bootstrap().startHTTPServer();

		RestAssured.proxy("localhost", 4567);
	}

	@Test
	public void testPostWithOneRoute() {

		given()
		    .body("{\"name\":\"SP\", \"routes\": [{\"origin\": \"Limeira\", \"destination\": \"Americana\", \"distance\": 50}]}")
		    .contentType("application/json; charset=UTF-8")
		    .when()
		    .post("/maps")
		    .then()
		    .statusCode(202)
		    .body("name", equalTo("SP"))
		    .body("routes.size()", equalTo(1))
		    .body("routes.get(0).origin", equalTo("Limeira"))
		    .body("routes.get(0).destination", equalTo("Americana"))
		    .body("routes.get(0).distance", equalTo(50));

	}

	@Test
	public void testPostWithTwoRoutes() {

		given()
		    .body("{\"name\":\"RJ\", \"routes\": [{\"origin\": \"Petrópolis\", \"destination\": \"Rio de Janeiro\", \"distance\": 30}, {\"origin\": \"Cabo Frio\", \"destination\": \"Belford Roxo\", \"distance\": 10}]}")
		    .contentType("application/json; charset=UTF-8")
		    .when()
		    .post("/maps")
		    .then()
		    .statusCode(202)
		    .body("name", equalTo("RJ"))
		    .body("routes.size()", equalTo(2))
		    .body("routes.get(0).origin", equalTo("Petrópolis"))
		    .body("routes.get(0).destination", equalTo("Rio de Janeiro"))
		    .body("routes.get(0).distance", equalTo(30))
		    .body("routes.get(1).origin", equalTo("Cabo Frio"))
		    .body("routes.get(1).destination", equalTo("Belford Roxo"))
		    .body("routes.get(1).distance", equalTo(10));

	}

	@Test
	public void testPostWithoutBody() {

		given()
		    .body("")
		    .contentType("application/json; charset=UTF-8")
		    .when()
		    .post("/maps")
		    .then()
		    .statusCode(400);

	}

	@Test
	public void testPostWithEmptyJson() {

		given()
		    .body("{}")
		    .contentType("application/json; charset=UTF-8")
		    .when()
		    .post("/maps")
		    .then()
		    .statusCode(400);

	}

	@Test
	public void testPostWithoutName() {

		given()
		    .body("{\"routes\": [{\"origin\": \"Limeira\", \"destination\": \"Americana\", \"distance\": 50}]}")
		    .contentType("application/json; charset=UTF-8")
		    .when()
		    .post("/maps")
		    .then()
		    .statusCode(400);

	}

	@Test
	public void testPostWithBlankName() {

		given()
		    .body("{\"name\": \"\", \"routes\": [{\"origin\": \"Limeira\", \"destination\": \"Americana\", \"distance\": 50}]}")
		    .contentType("application/json; charset=UTF-8")
		    .when()
		    .post("/maps")
		    .then()
		    .statusCode(400);

	}

	@Test
	public void testPostWithoutRoutes() {

		given()
		    .body("{\"name\": \"SP\"}")
		    .contentType("application/json; charset=UTF-8")
		    .when()
		    .post("/maps")
		    .then()
		    .statusCode(400);

	}

	@Test
	public void testPostWithEmptyRoutes() {

		given()
		    .body("{\"name\": \"SP\", \"routes\": []}")
		    .contentType("application/json; charset=UTF-8")
		    .when()
		    .post("/maps")
		    .then()
		    .statusCode(202)
		    .body("name", equalTo("SP"))
		    .body("routes.size()", equalTo(0));

	}

	@Test
	public void testPostWithRouteWithoutOrigin() {

		given()
		    .body("{\"name\": \"SP\", \"routes\": [{\"destination\": \"Americana\", \"distance\": 50}]}")
		    .contentType("application/json; charset=UTF-8")
		    .when()
		    .post("/maps")
		    .then()
		    .statusCode(400);

	}

	@Test
	public void testPostWithRouteWithEmptyOrigin() {

		given()
		    .body("{\"name\": \"SP\", \"routes\": [{\"origin\": \"\", \"destination\": \"Americana\", \"distance\": 50}]}")
		    .contentType("application/json; charset=UTF-8")
		    .when()
		    .post("/maps")
		    .then()
		    .statusCode(400);

	}

	@Test
	public void testPostWithRouteWithoutDestination() {

		given()
		    .body("{\"name\": \"SP\", \"routes\": [{\"origin\": \"Americana\", \"distance\": 50}]}")
		    .contentType("application/json; charset=UTF-8")
		    .when()
		    .post("/maps")
		    .then()
		    .statusCode(400);

	}

	@Test
	public void testPostWithRouteWithEmptyDestination() {

		given()
		    .body("{\"name\": \"SP\", \"routes\": [{\"origin\": \"Limeira\", \"destination\": \"\", \"distance\": 50}]}")
		    .contentType("application/json; charset=UTF-8")
		    .when()
		    .post("/maps")
		    .then()
		    .statusCode(400);

	}

	@Test
	public void testPostWithRouteWithoutDistance() {

		given()
		    .body("{\"name\": \"SP\", \"routes\": [{\"origin\": \"Limeira\", \"destination\": \"Americana\"}]}")
		    .contentType("application/json; charset=UTF-8")
		    .when()
		    .post("/maps")
		    .then()
		    .statusCode(400);

	}

	@Test
	public void testPostWithRouteWithNegativeDistance() {

		given()
		    .body("{\"name\": \"SP\", \"routes\": [{\"origin\": \"Limeira\", \"destination\": \"Americana\", \"distance\": -10}]}")
		    .contentType("application/json; charset=UTF-8")
		    .when()
		    .post("/maps")
		    .then()
		    .statusCode(400);

	}
}
