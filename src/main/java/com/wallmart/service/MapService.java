package com.wallmart.service;

import org.bson.Document;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.wallmart.domain.Map;

public class MapService {

	public void save(Map map) {

		MongoClient client = new MongoClient();
		MongoDatabase db = client.getDatabase("logistic");
		MongoCollection<Document> collection = db.getCollection("map");

		Document doc = Document.parse(new Gson().toJson(map));
		collection.insertOne(doc);

		client.close();
	}
}
