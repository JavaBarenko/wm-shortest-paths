package com.wallmart;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.mongodb.client.MongoDatabase;
import com.wallmart.guice.MongoDatabaseProvider;
import com.wallmart.resource.MapResource;

public class Bootstrap {

	private Injector injector;

	public static void main(String[] args) {

		Bootstrap bootstrap = new Bootstrap();

		bootstrap.configureGuice();
		bootstrap.startHTTPServer();
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

	public void startHTTPServer() {

		injector.getInstance(MapResource.class);
	}
}
