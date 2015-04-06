package com.wallmart.resource;

import static spark.Spark.get;
import static spark.Spark.halt;
import static spark.Spark.post;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.wallmart.domain.Map;
import com.wallmart.domain.ShortestPathCost;
import com.wallmart.service.MapService;

public class MapResource {

	@Inject
	private MapService service;

	public MapResource() {

		/**
		 * POST /maps
		 */
		post("/maps", "application/json", (req, resp) -> {

			Map map = new Gson().fromJson(req.body(), Map.class);

			if (map == null) halt(400, "Empty body");

			try {

				service.save(map);

			} catch (IllegalArgumentException e) {

				halt(400, e.getMessage());
			}

			resp.status(202);
			resp.type("application/json");
			return new Gson().toJson(map);
		});

		/**
		 * GET /maps/{name}/shortestPathCosts?origin={origin}&destination={destination}&autonomy={autonomy}&fuelPrice={
		 * fuelPrice}
		 */
		get("/maps/:name/shortest-path-costs", (req, resp) -> {

			String mapName = req.params(":name");
			String origin = req.queryParams("origin");
			String destination = req.queryParams("destination");
			Float autonomy = null;
			Float fuelPrice = null;

			try {
				autonomy = Float.valueOf(req.queryParams("autonomy"));
			} catch (Exception e) {
				halt(400, "Autonomy should be a numeric float");
			}

			try {
				fuelPrice = Float.valueOf(req.queryParams("fuelPrice"));
			} catch (Exception e2) {
				halt(400, "Fuel price should be a numeric float");
			}

			ShortestPathCost shortestPathCost = null;

			try {

				shortestPathCost = service.calculateShortestPathCost(
				    mapName,
				    origin,
				    destination,
				    autonomy,
				    fuelPrice);

			} catch (IllegalArgumentException e) {

				halt(400, e.getMessage());
			}

			resp.status(200);
			resp.type("application/json");
			return new Gson().toJson(shortestPathCost);
		});

	}
}
