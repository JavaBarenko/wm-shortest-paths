package com.wallmart.resource;

import static spark.Spark.halt;
import static spark.Spark.post;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.wallmart.domain.Map;

public class MapResource {

	public MapResource() {

		post("/maps", "application/json", (req, resp) -> {

			Map map = new Gson().fromJson(req.body(), Map.class);

			if (map == null) halt(400, "Empty body");
			if (StringUtils.isEmpty(map.getName())) halt(400, "Empty map name");
			if (map.getRoutes() == null) halt(400, "Empty routes");

			// TODO: pode distancia negativa? e zero?
			map.getRoutes().forEach(r -> {
				if (StringUtils.isEmpty(r.getOrigin())) halt(400, "Route with empty origin");
				if (StringUtils.isEmpty(r.getDestination())) halt(400, "Route with empty destination");
				if (r.getDistance() == null) halt(400, "Route with empty distance");
				if (r.getDistance() < 0) halt(400, "Route with negative distance");
			});

			// TODO: persist

			resp.status(202);
			resp.type("application/json");
			return new Gson().toJson(map);
		});

		// TODO: route calculate

	}
}
