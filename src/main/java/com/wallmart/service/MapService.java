package com.wallmart.service;

import com.google.inject.Inject;
import com.wallmart.domain.Map;
import com.wallmart.domain.ShortestPath;
import com.wallmart.domain.ShortestPathCost;
import com.wallmart.persistence.MapRepository;

public class MapService {

	// TODO: map name = id

	@Inject
	private MapRepository repository;

	public void save(Map map) {

		repository.save(map);
	}

	public ShortestPathCost calculateShortestPathCost(
	    String mapName,
	    String origin,
	    String destination,
	    Float autonomy,
	    Float fuelPrice) {

		ShortestPath shortestPath = calculateShortestPath(mapName, origin, destination);

		float cost = (shortestPath.getDistance() / autonomy) * fuelPrice;

		ShortestPathCost shortestPathCost = new ShortestPathCost();
		shortestPathCost.setShortestPath(shortestPath);
		shortestPathCost.setAutonomy(autonomy);
		shortestPathCost.setFuelPrice(fuelPrice);
		shortestPathCost.setCost(cost);

		return shortestPathCost;
	}

	public ShortestPath calculateShortestPath(String mapName, String origin, String destination) {

		// TODO: implementar djkistra

		float distance = 1;
		String[] path = new String[] { "São Paulo", "Limeira", "Americana" };

		ShortestPath shortestPath = new ShortestPath();
		shortestPath.setMapName(mapName);
		shortestPath.setOrigin(origin);
		shortestPath.setDestination(destination);
		shortestPath.setPath(path);
		shortestPath.setDistance(distance);

		return shortestPath;
	}
}
