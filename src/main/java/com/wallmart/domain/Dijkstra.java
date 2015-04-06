package com.wallmart.domain;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Collectors;

/*
 * Reference: http://www.math.cornell.edu/~mec/Winter2009/Thompson/search.html
 */
public class Dijkstra {

	HashMap<String, Float> distanceFromOrigin = new HashMap<String, Float>();
	HashMap<String, String> predecessorOf = new HashMap<String, String>();
	HashMap<String, Set<Route>> neighbors = new HashMap<String, Set<Route>>();
	PriorityQueue<String> unburned = new PriorityQueue<String>();

	private Dijkstra() {

	}

	public static Dijkstra initialize(Set<Route> routes) {

		Dijkstra dijkstra = new Dijkstra();

		HashSet<String> cities = new HashSet<String>();

		// Extract unique vertex (cities) and neighbors
		routes.forEach(r -> {

			cities.add(r.getOrigin());
			cities.add(r.getDestination());

			Set<Route> neighborsFromOrigin = dijkstra.neighbors.get(r.getOrigin());
			if (neighborsFromOrigin == null) neighborsFromOrigin = new HashSet<Route>();
			neighborsFromOrigin.add(r);
			dijkstra.neighbors.put(r.getOrigin(), neighborsFromOrigin);
		});

		// initialize variables
		cities.forEach(c -> {
			dijkstra.unburned.add(c);
			dijkstra.distanceFromOrigin.put(c, Float.MAX_VALUE);
			dijkstra.predecessorOf.put(c, null);
		});

		return dijkstra;

	}

	public DijkstraResult calculateShortestPath(String origin, String destination) {

		if (!unburned.contains(origin)) throw new NoSuchElementException("Origin not found");
		if (!unburned.contains(destination)) throw new NoSuchElementException("Destination not found");

		distanceFromOrigin.put(origin, 0f);

		while (!unburned.isEmpty()) {

			String city = getUnburnedCityWithTheSmallestDistanceFromOrigin();

			unburned.remove(city);

			if (city.equals(destination)) continue;

			Set<Route> unburnedNeighbors = getUnburnedNeighbors(city);

			unburnedNeighbors.forEach(n -> {

				float candidateDistance = distanceFromOrigin.get(city) + n.getDistance();

				if (candidateDistance < distanceFromOrigin.get(n.getDestination())) {

					distanceFromOrigin.put(n.getDestination(), candidateDistance);
					predecessorOf.put(n.getDestination(), city);
				}
			});

			// System.out.println(predecessorOf);
			// System.out.println(distanceFromOrigin);

		}

		return getResult(origin, destination);
	}

	private DijkstraResult getResult(String origin, String destination) {

		LinkedList<String> path = new LinkedList<String>();

		String target = destination;

		do {
			path.add(target);
			target = predecessorOf.get(target);

		} while (target != null);

		if (path.size() <= 1)
		    throw new IllegalArgumentException("No path existent from " + origin + " to destination " + destination);

		Collections.reverse(path);

		return new DijkstraResult(distanceFromOrigin.get(destination), path.toArray(new String[] {}));
	}

	Set<Route> getUnburnedNeighbors(String city) {

		if (unburned.isEmpty() || neighbors.get(city) == null) return Collections.emptySet();

		return neighbors
		    .get(city)
		    .parallelStream()
		    .filter(r -> unburned.contains(r.getDestination()))
		    .collect(Collectors.toSet());

	}

	String getUnburnedCityWithTheSmallestDistanceFromOrigin() {

		return unburned
		    .parallelStream()
		    .min((c1, c2) -> {
			    return (int) (distanceFromOrigin.get(c1) - distanceFromOrigin.get(c2));
		    })
		    .get();
	}

}
