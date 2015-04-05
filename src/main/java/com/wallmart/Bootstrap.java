package com.wallmart;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.mongodb.client.MongoDatabase;
import com.wallmart.guice.MongoDatabaseProvider;
import com.wallmart.resource.MapResource;

public class Bootstrap {

	// TODO: separate test and production db

	private Injector injector;

	public static void main(String[] args) {

		new Bootstrap().start();
	}

	public void start() {

		configureGuice();
		startHTTPServer();
	}

	private void configureGuice() {

		injector = Guice.createInjector(

		    new Module() {

			    @Override
			    public void configure(Binder binder) {

				    binder.bind(MongoDatabase.class).toProvider(MongoDatabaseProvider.class);

			    }
		    });

	}

	private void startHTTPServer() {

		injector.getInstance(MapResource.class);
	}
}
