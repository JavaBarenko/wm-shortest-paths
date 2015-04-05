package com.wallmart.guice;

import com.google.inject.Provider;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoDatabaseProvider implements Provider<MongoDatabase> {

	// TODO: extract
	private String dbName = "logistic";

	@Override
	public MongoDatabase get() {

		// TODO: leak client
		MongoClient client = new MongoClient();
		MongoDatabase db = client.getDatabase(dbName);

		return db;
	}

}
