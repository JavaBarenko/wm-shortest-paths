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

			if (StringUtils.isEmpty(map.getName())) {
				halt(400);
			}

			resp.status(202);
			resp.type("application/json");
			return new Gson().toJson(map);
		});

	}
}
