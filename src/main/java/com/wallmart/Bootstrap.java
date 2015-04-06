package com.wallmart;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.name.Names;
import com.mongodb.client.MongoDatabase;
import com.wallmart.guice.MongoDatabaseProvider;
import com.wallmart.resource.MapResource;

public class Bootstrap {

	private Injector injector;

	public static void main(String[] args) {

		new Bootstrap().start();
	}

	public void start() {

		try {
			configureGuice();

		} catch (IOException e) {

			System.out.println("Fail to load properties file");
			e.printStackTrace();
		}

		startHTTPServer();
	}

	private void configureGuice() throws FileNotFoundException, IOException {

		Properties properties = new Properties();
		properties.load(new FileReader("src/main/resources/config.properties"));

		injector = Guice.createInjector(

		    new Module() {

			    @Override
			    public void configure(Binder binder) {

				    binder.bind(MongoDatabase.class).toProvider(MongoDatabaseProvider.class);

				    Names.bindProperties(binder, properties);
			    }
		    });

	}

	private void startHTTPServer() {

		injector.getInstance(MapResource.class);
	}
}
