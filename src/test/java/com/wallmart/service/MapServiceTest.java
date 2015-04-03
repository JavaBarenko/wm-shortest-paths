package com.wallmart.service;

import org.junit.Test;

import com.wallmart.domain.Map;

public class MapServiceTest {

	@Test
	public void testSaveMap() {

		Map map = new Map();

		new MapService().save(map);
	}
}
