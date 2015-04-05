package com.wallmart.service;

import org.bson.Document;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.wallmart.domain.Map;
import com.wallmart.domain.ShortestPath;
import com.wallmart.domain.ShortestPathCost;

public class MapService {

	public void save(Map map) {

		// TODO: separar persistencia
		// TODO: fazer teste unitario do service
		// TODO: implementar djkistra

		MongoClient client = new MongoClient();
		MongoDatabase db = client.getDatabase("logistic");
		MongoCollection<Document> collection = db.getCollection("map");

		Document doc = Document.parse(new Gson().toJson(map));
		collection.insertOne(doc);

		client.close();
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
