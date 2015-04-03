package com.wallmart;

import com.wallmart.resource.MapResource;

public class Bootstrap {

	public static void main(String[] args) {

		Bootstrap bootstrap = new Bootstrap();
		bootstrap.startHTTPServer();
	}

	public void startHTTPServer() {

		new MapResource();
	}
}
