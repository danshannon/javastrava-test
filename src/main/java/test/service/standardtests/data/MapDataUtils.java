package test.service.standardtests.data;

import java.util.Random;

import org.jfairy.Fairy;
import org.jfairy.producer.text.TextProducer;

import javastrava.api.v3.model.StravaMap;
import javastrava.api.v3.model.StravaMapPoint;
import javastrava.api.v3.model.reference.StravaResourceState;

/**
 * Test data generation utilities for map data
 *
 * @author Dan Shannon
 *
 */
public class MapDataUtils {
	private static Random random = new Random();

	private static TextProducer text = Fairy.create().textProducer();

	/**
	 * Generate a random map point
	 *
	 * @param resourceState
	 *            The required resource state of the map point
	 * @return The generated map point
	 */
	@SuppressWarnings("boxing")
	public static StravaMapPoint testMapPoint(StravaResourceState resourceState) {
		final StravaMapPoint point = new StravaMapPoint();

		point.setLatitude((random.nextFloat() * 180) - 90);
		point.setLongitude((random.nextFloat() * 360) - 180);

		return point;
	}

	/**
	 * Generate a random map
	 *
	 * @param resourceState
	 *            The required resource state of the map
	 * @return The generated map
	 */
	public static StravaMap testMap(StravaResourceState resourceState) {
		final StravaMap map = new StravaMap();

		map.setId(text.randomString(20));
		map.setPolyline(text.randomString(100));
		map.setResourceState(resourceState);
		map.setSummaryPolyline(text.randomString(100));

		return map;
	}

}
