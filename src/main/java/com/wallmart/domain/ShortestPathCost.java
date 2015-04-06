package com.wallmart.domain;

public class ShortestPathCost {

	private ShortestPath shortestPath;

	private Float autonomy;

	private Float fuelPrice;

	private Float cost;

	public ShortestPathCost(ShortestPath shortestPath, Float autonomy, Float fuelPrice) {

		if (autonomy == null) throw new IllegalArgumentException("Empty autonomy");
		if (fuelPrice == null) throw new IllegalArgumentException("Empty fuel price");
		if (autonomy <= 0) throw new IllegalArgumentException("Autonomy should be greater or equal than 0");
		if (fuelPrice <= 0) throw new IllegalArgumentException("Fuel price should be greater or equal than 0");

		Float cost = (shortestPath.getDistance() / autonomy) * fuelPrice;

		this.shortestPath = shortestPath;
		this.autonomy = autonomy;
		this.fuelPrice = fuelPrice;
		this.cost = cost;
	}

	public ShortestPath getShortestPath() {

		return shortestPath;
	}

	public void setShortestPath(ShortestPath shortestPath) {

		this.shortestPath = shortestPath;
	}

	public Float getAutonomy() {

		return autonomy;
	}

	public void setAutonomy(Float autonomy) {

		this.autonomy = autonomy;
	}

	public Float getFuelPrice() {

		return fuelPrice;
	}

	public void setFuelPrice(Float fuelPrice) {

		this.fuelPrice = fuelPrice;
	}

	public Float getCost() {

		return cost;
	}

	public void setCost(Float cost) {

		this.cost = cost;
	}

}