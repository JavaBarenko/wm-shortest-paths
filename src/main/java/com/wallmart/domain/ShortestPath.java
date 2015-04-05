package com.wallmart.domain;

public class ShortestPath {

	private String mapName;

	private String origin;

	private String destination;

	private String[] path;

	private float distance;

	public ShortestPath(Map map, String origin, String destination) {

		// TODO: implementar djkistra

		float distance = 1;
		String[] path = new String[] { "São Paulo", "Limeira", "Americana" };

		this.mapName = map.getName();
		this.origin = origin;
		this.destination = destination;
		this.path = path;
		this.distance = distance;

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
