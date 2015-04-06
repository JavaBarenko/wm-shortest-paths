package com.wallmart.persistence;

import static com.mongodb.client.model.Filters.eq;

import java.util.NoSuchElementException;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.google.gson.Gson;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;

public abstract class MongoBaseRepository<K, T> extends BaseRepository<K, T> {

	public abstract MongoCollection<Document> getMongoCollection();

	public T save(K id, T obj) {

		Document doc = Document.parse(new Gson().toJson(obj));

		if (id instanceof String) {
			doc.append("_id", (String) id);
		} else {
			throw new UnsupportedOperationException();
		}

		getMongoCollection().updateOne(
		    eq("_id", doc.get("_id")),
		    new Document("$set", doc),
		    new UpdateOptions().upsert(true));

		return obj;
	}

	public T save(T obj) {

		Document doc = Document.parse(new Gson().toJson(obj));
		getMongoCollection().insertOne(doc);

		return obj;
	}

	public T findById(String id) {

		Bson filterById = eq("_id", id);
		FindIterable<Document> iter = getMongoCollection().find(filterById);

		Document doc = iter.first();

		if (doc == null) throw new NoSuchElementException();

		return new Gson().fromJson(doc.toJson(), getParameterizedEntity());
	}
}
