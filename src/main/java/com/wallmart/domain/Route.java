package com.wallmart.domain;

import org.apache.commons.lang3.StringUtils;

public class Route {

	private String origin;

	private String destination;

	private Float distance;

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

	public Float getDistance() {

		return distance;
	}

	public void setDistance(Float distance) {

		this.distance = distance;
	}

	public void validate() {

		if (StringUtils.isEmpty(origin)) throw new IllegalArgumentException("Route with empty origin");
		if (StringUtils.isEmpty(destination))
		    throw new IllegalArgumentException("Route with empty destination");

		if (distance == null) throw new IllegalArgumentException("Route with empty distance");
		if (distance <= 0) throw new IllegalArgumentException("Route with negative distance");

	}

}
