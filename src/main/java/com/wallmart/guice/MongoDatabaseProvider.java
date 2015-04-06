package com.wallmart.guice;

import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

@Singleton
public class MongoDatabaseProvider implements Provider<MongoDatabase> {

	// TODO: extract dbname to conf
	private String dbName = "logistic";

	@SuppressWarnings("resource")
	@Override
	public MongoDatabase get() {

		MongoClient client = new MongoClient();
		MongoDatabase db = client.getDatabase(dbName);

		return db;
	}

}
