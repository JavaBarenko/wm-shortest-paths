package com.wallmart.persistence;

import org.bson.Document;

import com.google.inject.Inject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.wallmart.domain.Map;

public class MapRepository extends MongoBaseRepository<Map> {

	@Inject
	private MongoDatabase db;

	@Override
	public MongoCollection<Document> getMongoCollection() {

		return db.getCollection("map");
	}
}
