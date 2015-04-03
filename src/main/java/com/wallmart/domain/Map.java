package com.wallmart.domain;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class Map {

	private String name;

	private List<Route> routes;

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public List<Route> getRoutes() {

		return routes;
	}

	public void setRoutes(List<Route> routes) {

		this.routes = routes;
	}

	public void validate() {

		if (StringUtils.isEmpty(name)) throw new IllegalArgumentException("Empty map name");
		if (routes == null) throw new IllegalArgumentException("Empty routes");

		routes.forEach(r -> r.validate());
	}
}
