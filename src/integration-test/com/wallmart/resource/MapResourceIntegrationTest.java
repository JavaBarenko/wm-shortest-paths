package com.wallmart.resource;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.jayway.restassured.RestAssured;
import com.wallmart.Bootstrap;
import com.wallmart.IntegrationTest;

@Category(IntegrationTest.class)
public class MapResourceIntegrationTest {

	@BeforeClass
	public static void tearUp() {

		// TODO: separate test and production db
		new Bootstrap().start();

		RestAssured.proxy("localhost", 4567);
	}

	/*
	 * POST /maps
	 */

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
		    .body("routes.get(0).distance", is(50f));

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
		    .body("routes.get(0).distance", is(30f))
		    .body("routes.get(1).origin", equalTo("Cabo Frio"))
		    .body("routes.get(1).destination", equalTo("Belford Roxo"))
		    .body("routes.get(1).distance", is(10f));

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
		    .statusCode(400);

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

	@Test
	public void testPostWithRouteWithZeroDistance() {

		given()
		    .body("{\"name\": \"SP\", \"routes\": [{\"origin\": \"Limeira\", \"destination\": \"Americana\", \"distance\": 0}]}")
		    .contentType("application/json; charset=UTF-8")
		    .when()
		    .post("/maps")
		    .then()
		    .statusCode(400);

	}

	/*
	 * GET /maps/:name/shortest-path-costs
	 */

	@Test
	public void testGetShortestPathWithOneEdge() {

		given()
		    .body("{\"name\":\"RJ\", \"routes\": [{\"origin\": \"Rio de Janeiro\", \"destination\": \"Belford Roxo\", \"distance\": 1}]}")
		    .contentType("application/json; charset=UTF-8")
		    .when()
		    .post("/maps")
		    .then()
		    .statusCode(202);

		given()
		    .queryParam("origin", "Rio de Janeiro")
		    .queryParam("destination", "Belford Roxo")
		    .queryParam("autonomy", "5")
		    .queryParam("fuelPrice", "3.8")
		    .contentType("application/json; charset=UTF-8")
		    .when()
		    .get("/maps/RJ/shortest-path-costs")
		    .then()
		    .statusCode(200)
		    .body("autonomy", is(5f))
		    .body("fuelPrice", is(3.8f))
		    .body("cost", is(0.76f))
		    .body("shortestPath.path.size()", equalTo(2))
		    .body("shortestPath.path.get(0)", equalTo("Rio de Janeiro"))
		    .body("shortestPath.path.get(1)", equalTo("Belford Roxo"));

	}

	@Test
	public void testGetShortestPathWithTwoEdges() {

		given()
		    .body("{\"name\":\"SP\", \"routes\": [{\"origin\": \"São Paulo\", \"destination\": \"Limeira\", \"distance\": 0.5}, {\"origin\": \"Limeira\", \"destination\": \"Americana\", \"distance\": 0.5}]}")
		    .contentType("application/json; charset=UTF-8")
		    .when()
		    .post("/maps")
		    .then()
		    .statusCode(202);

		given()
		    .queryParam("origin", "São Paulo")
		    .queryParam("destination", "Americana")
		    .queryParam("autonomy", "10")
		    .queryParam("fuelPrice", "4")
		    .contentType("application/json; charset=UTF-8")
		    .when()
		    .get("/maps/SP/shortest-path-costs")
		    .then()
		    .statusCode(200)
		    .body("autonomy", is(10f))
		    .body("fuelPrice", is(4f))
		    .body("cost", is(0.4f))
		    .body("shortestPath.mapName", equalTo("SP"))
		    .body("shortestPath.origin", equalTo("São Paulo"))
		    .body("shortestPath.destination", equalTo("Americana"))
		    .body("shortestPath.mapName", equalTo("SP"))
		    .body("shortestPath.path.size()", equalTo(3))
		    .body("shortestPath.path.get(0)", equalTo("São Paulo"))
		    .body("shortestPath.path.get(1)", equalTo("Limeira"))
		    .body("shortestPath.path.get(2)", equalTo("Americana"))
		    .body("shortestPath.distance", is(1f));

	}

	@Test
	public void testGetShortestPathWithoutOrigin() {

		given()
		    .body("{\"name\":\"SP\", \"routes\": [{\"origin\": \"São Paulo\", \"destination\": \"Limeira\", \"distance\": 0.5}, {\"origin\": \"Limeira\", \"destination\": \"Americana\", \"distance\": 0.5}]}")
		    .contentType("application/json; charset=UTF-8")
		    .when()
		    .post("/maps")
		    .then()
		    .statusCode(202);

		given()
		    .queryParam("destination", "Americana")
		    .queryParam("autonomy", "10")
		    .queryParam("fuelPrice", "4")
		    .contentType("application/json; charset=UTF-8")
		    .when()
		    .get("/maps/SP/shortest-path-costs")
		    .then()
		    .statusCode(400);
	}

	@Test
	public void testGetShortestPathWithEmptyOrigin() {

		given()
		    .body("{\"name\":\"SP\", \"routes\": [{\"origin\": \"São Paulo\", \"destination\": \"Limeira\", \"distance\": 0.5}, {\"origin\": \"Limeira\", \"destination\": \"Americana\", \"distance\": 0.5}]}")
		    .contentType("application/json; charset=UTF-8")
		    .when()
		    .post("/maps")
		    .then()
		    .statusCode(202);

		given()
		    .queryParam("origin", "")
		    .queryParam("destination", "Americana")
		    .queryParam("autonomy", "10")
		    .queryParam("fuelPrice", "4")
		    .contentType("application/json; charset=UTF-8")
		    .when()
		    .get("/maps/SP/shortest-path-costs")
		    .then()
		    .statusCode(400);
	}

	@Test
	public void testGetShortestPathWithoutDestination() {

		given()
		    .body("{\"name\":\"SP\", \"routes\": [{\"origin\": \"São Paulo\", \"destination\": \"Limeira\", \"distance\": 0.5}, {\"origin\": \"Limeira\", \"destination\": \"Americana\", \"distance\": 0.5}]}")
		    .contentType("application/json; charset=UTF-8")
		    .when()
		    .post("/maps")
		    .then()
		    .statusCode(202);

		given()
		    .queryParam("origin", "São Paulo")
		    .queryParam("autonomy", "10")
		    .queryParam("fuelPrice", "4")
		    .contentType("application/json; charset=UTF-8")
		    .when()
		    .get("/maps/SP/shortest-path-costs")
		    .then()
		    .statusCode(400);
	}

	@Test
	public void testGetShortestPathWithEmptyDestination() {

		given()
		    .body("{\"name\":\"SP\", \"routes\": [{\"origin\": \"São Paulo\", \"destination\": \"Limeira\", \"distance\": 0.5}, {\"origin\": \"Limeira\", \"destination\": \"Americana\", \"distance\": 0.5}]}")
		    .contentType("application/json; charset=UTF-8")
		    .when()
		    .post("/maps")
		    .then()
		    .statusCode(202);

		given()
		    .queryParam("origin", "São Paulo")
		    .queryParam("destination", "")
		    .queryParam("autonomy", "10")
		    .queryParam("fuelPrice", "4")
		    .contentType("application/json; charset=UTF-8")
		    .when()
		    .get("/maps/SP/shortest-path-costs")
		    .then()
		    .statusCode(400);
	}

	@Test
	public void testGetShortestPathWithoutAutonomy() {

		given()
		    .body("{\"name\":\"SP\", \"routes\": [{\"origin\": \"São Paulo\", \"destination\": \"Limeira\", \"distance\": 0.5}, {\"origin\": \"Limeira\", \"destination\": \"Americana\", \"distance\": 0.5}]}")
		    .contentType("application/json; charset=UTF-8")
		    .when()
		    .post("/maps")
		    .then()
		    .statusCode(202);

		given()
		    .queryParam("origin", "São Paulo")
		    .queryParam("destination", "Americana")
		    .queryParam("fuelPrice", "4")
		    .contentType("application/json; charset=UTF-8")
		    .when()
		    .get("/maps/SP/shortest-path-costs")
		    .then()
		    .statusCode(400);
	}

	@Test
	public void testGetShortestPathWithEmptyAutonomy() {

		given()
		    .body("{\"name\":\"SP\", \"routes\": [{\"origin\": \"São Paulo\", \"destination\": \"Limeira\", \"distance\": 0.5}, {\"origin\": \"Limeira\", \"destination\": \"Americana\", \"distance\": 0.5}]}")
		    .contentType("application/json; charset=UTF-8")
		    .when()
		    .post("/maps")
		    .then()
		    .statusCode(202);

		given()
		    .queryParam("origin", "São Paulo")
		    .queryParam("destination", "Americana")
		    .queryParam("autonomy", "")
		    .queryParam("fuelPrice", "4")
		    .contentType("application/json; charset=UTF-8")
		    .when()
		    .get("/maps/SP/shortest-path-costs")
		    .then()
		    .statusCode(400);
	}

	@Test
	public void testGetShortestPathWithInvalidAutonomyType() {

		given()
		    .body("{\"name\":\"SP\", \"routes\": [{\"origin\": \"São Paulo\", \"destination\": \"Limeira\", \"distance\": 0.5}, {\"origin\": \"Limeira\", \"destination\": \"Americana\", \"distance\": 0.5}]}")
		    .contentType("application/json; charset=UTF-8")
		    .when()
		    .post("/maps")
		    .then()
		    .statusCode(202);

		given()
		    .queryParam("origin", "São Paulo")
		    .queryParam("destination", "Americana")
		    .queryParam("autonomy", "one")
		    .queryParam("fuelPrice", "4")
		    .contentType("application/json; charset=UTF-8")
		    .when()
		    .get("/maps/SP/shortest-path-costs")
		    .then()
		    .statusCode(400);
	}

	@Test
	public void testGetShortestPathWithNegativeAutonomy() {

		given()
		    .body("{\"name\":\"SP\", \"routes\": [{\"origin\": \"São Paulo\", \"destination\": \"Limeira\", \"distance\": 0.5}, {\"origin\": \"Limeira\", \"destination\": \"Americana\", \"distance\": 0.5}]}")
		    .contentType("application/json; charset=UTF-8")
		    .when()
		    .post("/maps")
		    .then()
		    .statusCode(202);

		given()
		    .queryParam("origin", "São Paulo")
		    .queryParam("destination", "Americana")
		    .queryParam("autonomy", "-1")
		    .queryParam("fuelPrice", "4")
		    .contentType("application/json; charset=UTF-8")
		    .when()
		    .get("/maps/SP/shortest-path-costs")
		    .then()
		    .statusCode(400);
	}

	@Test
	public void testGetShortestPathWithZeroAutonomy() {

		given()
		    .body("{\"name\":\"SP\", \"routes\": [{\"origin\": \"São Paulo\", \"destination\": \"Limeira\", \"distance\": 0.5}, {\"origin\": \"Limeira\", \"destination\": \"Americana\", \"distance\": 0.5}]}")
		    .contentType("application/json; charset=UTF-8")
		    .when()
		    .post("/maps")
		    .then()
		    .statusCode(202);

		given()
		    .queryParam("origin", "São Paulo")
		    .queryParam("destination", "Americana")
		    .queryParam("autonomy", "0")
		    .queryParam("fuelPrice", "4")
		    .contentType("application/json; charset=UTF-8")
		    .when()
		    .get("/maps/SP/shortest-path-costs")
		    .then()
		    .statusCode(400);
	}

	@Test
	public void testGetShortestPathWithoutFuelPrice() {

		given()
		    .body("{\"name\":\"SP\", \"routes\": [{\"origin\": \"São Paulo\", \"destination\": \"Limeira\", \"distance\": 0.5}, {\"origin\": \"Limeira\", \"destination\": \"Americana\", \"distance\": 0.5}]}")
		    .contentType("application/json; charset=UTF-8")
		    .when()
		    .post("/maps")
		    .then()
		    .statusCode(202);

		given()
		    .queryParam("origin", "São Paulo")
		    .queryParam("destination", "Americana")
		    .queryParam("autonomy", "10")
		    .contentType("application/json; charset=UTF-8")
		    .when()
		    .get("/maps/SP/shortest-path-costs")
		    .then()
		    .statusCode(400);
	}

	@Test
	public void testGetShortestPathWithEmptyFuelPrice() {

		given()
		    .body("{\"name\":\"SP\", \"routes\": [{\"origin\": \"São Paulo\", \"destination\": \"Limeira\", \"distance\": 0.5}, {\"origin\": \"Limeira\", \"destination\": \"Americana\", \"distance\": 0.5}]}")
		    .contentType("application/json; charset=UTF-8")
		    .when()
		    .post("/maps")
		    .then()
		    .statusCode(202);

		given()
		    .queryParam("origin", "São Paulo")
		    .queryParam("destination", "Americana")
		    .queryParam("autonomy", "10")
		    .queryParam("fuelPrice", "")
		    .contentType("application/json; charset=UTF-8")
		    .when()
		    .get("/maps/SP/shortest-path-costs")
		    .then()
		    .statusCode(400);
	}

	@Test
	public void testGetShortestPathWithInvalidFuelPriceType() {

		given()
		    .body("{\"name\":\"SP\", \"routes\": [{\"origin\": \"São Paulo\", \"destination\": \"Limeira\", \"distance\": 0.5}, {\"origin\": \"Limeira\", \"destination\": \"Americana\", \"distance\": 0.5}]}")
		    .contentType("application/json; charset=UTF-8")
		    .when()
		    .post("/maps")
		    .then()
		    .statusCode(202);

		given()
		    .queryParam("origin", "São Paulo")
		    .queryParam("destination", "Americana")
		    .queryParam("autonomy", "10")
		    .queryParam("fuelPrice", "one")
		    .contentType("application/json; charset=UTF-8")
		    .when()
		    .get("/maps/SP/shortest-path-costs")
		    .then()
		    .statusCode(400);
	}

	@Test
	public void testGetShortestPathWithNegativeFuelPrice() {

		given()
		    .body("{\"name\":\"SP\", \"routes\": [{\"origin\": \"São Paulo\", \"destination\": \"Limeira\", \"distance\": 0.5}, {\"origin\": \"Limeira\", \"destination\": \"Americana\", \"distance\": 0.5}]}")
		    .contentType("application/json; charset=UTF-8")
		    .when()
		    .post("/maps")
		    .then()
		    .statusCode(202);

		given()
		    .queryParam("origin", "São Paulo")
		    .queryParam("destination", "Americana")
		    .queryParam("autonomy", "10")
		    .queryParam("fuelPrice", "-1")
		    .contentType("application/json; charset=UTF-8")
		    .when()
		    .get("/maps/SP/shortest-path-costs")
		    .then()
		    .statusCode(400);
	}

	@Test
	public void testGetShortestPathWithZeroFuelPrice() {

		given()
		    .body("{\"name\":\"SP\", \"routes\": [{\"origin\": \"São Paulo\", \"destination\": \"Limeira\", \"distance\": 0.5}, {\"origin\": \"Limeira\", \"destination\": \"Americana\", \"distance\": 0.5}]}")
		    .contentType("application/json; charset=UTF-8")
		    .when()
		    .post("/maps")
		    .then()
		    .statusCode(202);

		given()
		    .queryParam("origin", "São Paulo")
		    .queryParam("destination", "Americana")
		    .queryParam("autonomy", "10")
		    .queryParam("fuelPrice", "0")
		    .contentType("application/json; charset=UTF-8")
		    .when()
		    .get("/maps/SP/shortest-path-costs")
		    .then()
		    .statusCode(400);
	}
}
