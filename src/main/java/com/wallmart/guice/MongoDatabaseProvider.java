package com.wallmart.guice;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

@Singleton
public class MongoDatabaseProvider implements Provider<MongoDatabase> {

	@Inject
	@Named("mongodb.host")
	private String host;

	@Inject
	@Named("mongodb.port")
	private int port;

	@Inject
	@Named("mongodb.db")
	private String dbName;

	@SuppressWarnings("resource")
	@Override
	public MongoDatabase get() {

		MongoClient client = new MongoClient(host, port);
		MongoDatabase db = client.getDatabase(dbName);

		return db;
	}
}
