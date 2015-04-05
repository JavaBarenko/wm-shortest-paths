package com.wallmart.persistence;

import org.bson.Document;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;

public abstract class MongoBaseRepository<T> extends BaseRepository<T> {

	public abstract MongoCollection<Document> getMongoCollection();

	public T save(T o) {

		Document doc = Document.parse(new Gson().toJson(o));
		getMongoCollection().insertOne(doc);

		return o;
	}
}
