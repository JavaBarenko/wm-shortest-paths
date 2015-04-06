package com.wallmart.domain;

public class DijkstraResult {

	private Float distance;
	private String[] path;

	public DijkstraResult(float distance, String[] path) {

		this.distance = distance;
		this.path = path;
	}

	public Float getDistance() {

		return distance;
	}

	public String[] getPath() {

		return path;
	}

}
