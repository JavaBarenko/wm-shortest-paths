package com.wallmart.domain;

import java.util.List;

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

}
