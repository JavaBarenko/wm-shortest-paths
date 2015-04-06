package com.wallmart.service;

import com.google.inject.Inject;
import com.wallmart.domain.Map;
import com.wallmart.domain.ShortestPath;
import com.wallmart.domain.ShortestPathCost;
import com.wallmart.persistence.MapRepository;

public class MapService {

	private MapRepository repository;

	@Inject
	public void setRepository(MapRepository repository) {

		this.repository = repository;
	}

	public void save(Map map) {

		map.validate();
		repository.save(map.getName(), map);
	}

	public ShortestPath calculateShortestPath(String mapName, String origin, String destination) {

		Map map = repository.findById(mapName);

		return new ShortestPath(map, origin, destination);
	}

	public ShortestPathCost calculateShortestPathCost(
	    String mapName,
	    String origin,
	    String destination,
	    Float autonomy,
	    Float fuelPrice) {

		ShortestPath shortestPath = calculateShortestPath(mapName, origin, destination);

		return new ShortestPathCost(shortestPath, autonomy, fuelPrice);
	}
}
