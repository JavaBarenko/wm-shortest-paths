package com.wallmart.domain;

public class ShortestPathCost {

	private ShortestPath shortestPath;

	private Float autonomy;

	private Float fuelPrice;

	private Float cost;

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