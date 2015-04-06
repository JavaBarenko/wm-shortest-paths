package com.wallmart.service;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import com.wallmart.domain.Map;
import com.wallmart.domain.Route;
import com.wallmart.domain.ShortestPath;
import com.wallmart.domain.ShortestPathCost;
import com.wallmart.persistence.MapRepository;

public class MapServiceTest {

	private MapService service = null;

	private MapRepository repositoryMock = null;

	private final Route ROUTE_SAOPAULO_LIMEIRA = new Route();

	private final Route ROUTE_LIMEIRA_AMERICANA = new Route();

	private final Map MAP_SP = new Map();

	@Before
	public void tearUp() {

		repositoryMock = mock(MapRepository.class);

		service = new MapService();
		service.setRepository(repositoryMock);

		ROUTE_SAOPAULO_LIMEIRA.setOrigin("São Paulo");
		ROUTE_SAOPAULO_LIMEIRA.setDestination("Limeira");
		ROUTE_SAOPAULO_LIMEIRA.setDistance(150f);

		ROUTE_LIMEIRA_AMERICANA.setOrigin("Limeira");
		ROUTE_LIMEIRA_AMERICANA.setDestination("Americana");
		ROUTE_LIMEIRA_AMERICANA.setDistance(30f);

		MAP_SP.setName("SP");
		MAP_SP.setRoutes(Arrays.asList(ROUTE_SAOPAULO_LIMEIRA, ROUTE_LIMEIRA_AMERICANA));
	}

	/*
	 * save()
	 */

	@Test
	public void testSaveMap() {

		service.save(MAP_SP);

		verify(repositoryMock, times(1)).save("SP", MAP_SP);

	}

	@Test(expected = IllegalArgumentException.class)
	public void testSaveMapWithoutName() {

		MAP_SP.setName(null);
		service.save(MAP_SP);

		verify(repositoryMock, times(0)).save(null, MAP_SP);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSaveMapWithEmptyName() {

		MAP_SP.setName("");
		service.save(MAP_SP);

		verify(repositoryMock, times(0)).save("", MAP_SP);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSaveMapWithoutRoutes() {

		MAP_SP.setRoutes(null);
		service.save(MAP_SP);

		verify(repositoryMock, times(0)).save("SP", MAP_SP);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSaveMapWithEmptyRoutes() {

		MAP_SP.setRoutes(new ArrayList<Route>());
		service.save(MAP_SP);

		verify(repositoryMock, times(0)).save("SP", MAP_SP);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSaveMapWithRouteWithoutOrigin() {

		ROUTE_SAOPAULO_LIMEIRA.setOrigin(null);
		service.save(MAP_SP);

		verify(repositoryMock, times(0)).save("SP", MAP_SP);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSaveMapWithRouteWithEmptyOrigin() {

		ROUTE_SAOPAULO_LIMEIRA.setOrigin("");
		service.save(MAP_SP);

		verify(repositoryMock, times(0)).save("SP", MAP_SP);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSaveMapWithRouteWithoutDestination() {

		ROUTE_SAOPAULO_LIMEIRA.setDestination(null);
		service.save(MAP_SP);

		verify(repositoryMock, times(0)).save("SP", MAP_SP);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSaveMapWithRouteWithEmptyDestination() {

		ROUTE_SAOPAULO_LIMEIRA.setDestination("");
		service.save(MAP_SP);

		verify(repositoryMock, times(0)).save("SP", MAP_SP);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSaveMapWithRouteWithoutDistance() {

		ROUTE_SAOPAULO_LIMEIRA.setDistance(null);
		service.save(MAP_SP);

		verify(repositoryMock, times(0)).save("SP", MAP_SP);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSaveMapWithRouteWithNegativeDistance() {

		ROUTE_SAOPAULO_LIMEIRA.setDistance(-150f);
		service.save(MAP_SP);

		verify(repositoryMock, times(0)).save("SP", MAP_SP);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSaveMapWithRouteWithZeroDistance() {

		ROUTE_SAOPAULO_LIMEIRA.setDistance(0f);
		service.save(MAP_SP);

		verify(repositoryMock, times(0)).save("SP", MAP_SP);
	}

	/*
	 * calculateShortestPath()
	 */

	@SuppressWarnings("unchecked")
	@Test(expected = NoSuchElementException.class)
	public void testCalculateShortestPathOfNonExistentMap() {

		when(repositoryMock.findById("NonExistentMap")).thenThrow(NoSuchElementException.class);

		service.calculateShortestPath("NonExistentMap", "São Paulo", "Americana");

		verify(repositoryMock, times(1)).findById("NonExistentMap");
	}

	@Test
	public void testCalculateShortestPathWithOneEdge() {

		when(repositoryMock.findById("SP")).thenReturn(MAP_SP);

		ShortestPath shortestPath = service.calculateShortestPath("SP", "São Paulo", "Americana");

		verify(repositoryMock, times(1)).findById("SP");
		assertEquals(shortestPath.getMapName(), "SP");
		assertEquals(shortestPath.getOrigin(), "São Paulo");
		assertEquals(shortestPath.getDestination(), "Americana");
		assertArrayEquals(shortestPath.getPath(), new String[] { "São Paulo", "Limeira", "Americana" });
		assertEquals(shortestPath.getDistance(), 180f, 0.001f);
	}

	/*
	 * calculateShortestPathCost()
	 */

	@Test
	public void testCalculateShortestCostPathWithOneEdge() {

		when(repositoryMock.findById("SP")).thenReturn(MAP_SP);

		ShortestPathCost cost = service.calculateShortestPathCost("SP", "São Paulo", "Americana", 11f, 3.5f);

		verify(repositoryMock, times(1)).findById("SP");
		assertEquals(cost.getShortestPath().getMapName(), "SP");
		assertEquals(cost.getShortestPath().getOrigin(), "São Paulo");
		assertEquals(cost.getShortestPath().getDestination(), "Americana");
		assertArrayEquals(cost.getShortestPath().getPath(), new String[] { "São Paulo", "Limeira", "Americana" });
		assertEquals(cost.getShortestPath().getDistance(), 180f, 0.0001f);
		assertEquals(cost.getAutonomy(), 11f, 0.0001);
		assertEquals(cost.getFuelPrice(), 3.5f, 0.0001);
		assertEquals(cost.getCost(), 57.27272f, 0.0001);
	}
}
