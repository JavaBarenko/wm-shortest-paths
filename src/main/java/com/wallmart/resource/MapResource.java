package com.wallmart.resource;

import static spark.Spark.halt;
import static spark.Spark.post;

import com.google.gson.Gson;
import com.wallmart.domain.Map;
import com.wallmart.service.MapService;

public class MapResource {

	private MapService service = new MapService();

	public MapResource() {

		post("/maps", "application/json", (req, resp) -> {

			Map map = new Gson().fromJson(req.body(), Map.class);

			if (map == null) halt(400, "Empty body");

			try {

				map.validate();
				service.save(map);

			} catch (IllegalArgumentException e) {

				halt(400, e.getMessage());
			}

			resp.status(202);
			resp.type("application/json");
			return new Gson().toJson(map);
		});

		// TODO: route calculate

	}
}
