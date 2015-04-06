package com.wallmart.domain;

import java.util.Set;

import org.apache.commons.lang3.StringUtils;

public class Map {

	private String name;

	private Set<Route> routes;

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public Set<Route> getRoutes() {

		return routes;
	}

	public void setRoutes(Set<Route> routes) {

		this.routes = routes;
	}

	public void validate() {

		if (StringUtils.isEmpty(name)) throw new IllegalArgumentException("Empty map name");
		if (routes == null) throw new IllegalArgumentException("Empty routes");
		if (routes.size() <= 0) throw new IllegalArgumentException("Empty routes");

		routes.forEach(r -> r.validate());
	}
}
