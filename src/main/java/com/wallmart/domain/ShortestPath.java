package com.wallmart.domain;

import org.apache.commons.lang3.StringUtils;

public class ShortestPath {

	private String mapName;

	private String origin;

	private String destination;

	private String[] path;

	private float distance;

	public ShortestPath(Map map, String origin, String destination) {

		map.validate();
		if (StringUtils.isEmpty(origin)) throw new IllegalArgumentException("Empty origin");
		if (StringUtils.isEmpty(destination)) throw new IllegalArgumentException("Empty destination");

		DijkstraResult calc = Dijkstra.initialize(map.getRoutes()).calculateShortestPath(origin, destination);

		this.mapName = map.getName();
		this.origin = origin;
		this.destination = destination;
		this.path = calc.getPath();
		this.distance = calc.getDistance();
	}

	public String getMapName() {

		return mapName;
	}

	public void setMapName(String mapName) {

		this.mapName = mapName;
	}

	public String getOrigin() {

		return origin;
	}

	public void setOrigin(String origin) {

		this.origin = origin;
	}

	public String getDestination() {

		return destination;
	}

	public void setDestination(String destination) {

		this.destination = destination;
	}

	public String[] getPath() {

		return path;
	}

	public void setPath(String[] path) {

		this.path = path;
	}

	public float getDistance() {

		return distance;
	}

	public void setDistance(float distance) {

		this.distance = distance;
	}

}
