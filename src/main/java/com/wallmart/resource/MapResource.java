package com.wallmart.resource;

import static spark.Spark.get;
import static spark.Spark.halt;
import static spark.Spark.post;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.wallmart.domain.Map;
import com.wallmart.domain.ShortestPathCost;
import com.wallmart.service.MapService;

// TODO: doc API (swagger)?
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
		get("/maps/:name/shortestPathCosts", (req, resp) -> {

			if (StringUtils.isEmpty(req.queryParams("autonomy"))) halt(400, "Empty autonomy");
			if (StringUtils.isEmpty(req.queryParams("fuelPrice"))) halt(400, "Empty fuelPrice");
			if (StringUtils.isEmpty(req.queryParams("origin"))) halt(400, "Empty origin");
			if (StringUtils.isEmpty(req.queryParams("destination"))) halt(400, "Empty destination");

			String mapName = req.params(":name");
			String origin = req.queryParams("origin");
			String destination = req.queryParams("destination");
			Float autonomy = null;
			Float fuelPrice = null;

			try {
				autonomy = Float.valueOf(req.queryParams("autonomy"));
			} catch (NumberFormatException e) {
				halt(400, "Autonomy should be a numeric float");
			}

			try {
				fuelPrice = Float.valueOf(req.queryParams("fuelPrice"));
			} catch (NumberFormatException e) {
				halt(400, "Fuel price should be a numeric float");
			}

			if (autonomy <= 0) halt(400, "Autonomy should be greater or equal than 0");
			if (fuelPrice <= 0) halt(400, "Fuel price should be greater or equal than 0");

			ShortestPathCost shortestPathCost = service.calculateShortestPathCost(
			    mapName,
			    origin,
			    destination,
			    autonomy,
			    fuelPrice);

			resp.status(200);
			resp.type("application/json");
			return new Gson().toJson(shortestPathCost);
		});

	}
}
