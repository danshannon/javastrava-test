package test.service.standardtests.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Random;

import org.jfairy.Fairy;
import org.jfairy.producer.text.TextProducer;

import javastrava.api.v3.model.StravaActivity;
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

	/**
	 * Validate the structure and content of a map point
	 * 
	 * @param point
	 *            The map point to be validated
	 */
	@SuppressWarnings("boxing")
	public static void validate(final StravaMapPoint point) {
		assertNotNull(point);
		assertNotNull(point.getLatitude());
		assertTrue((point.getLatitude() <= 90) && (point.getLatitude() >= -90));
		assertNotNull(point.getLongitude());
		assertTrue((point.getLongitude() <= 180) && (point.getLongitude() >= -180));
	
	}

	/**
	 * @param map
	 *            The map to be validated
	 * @param id
	 *            The expected id of the map
	 * @param state
	 *            The expected resource state of the map
	 * @param activity
	 *            The activitity the map is expected to belong to
	 */
	@SuppressWarnings("boxing")
	public static void validateMap(final StravaMap map, final String id, final StravaResourceState state, final StravaActivity activity) {
		assertNotNull(map);
		assertEquals(id, map.getId());
		assertEquals(state, map.getResourceState());
	
		if ((activity != null) && (activity.getManual() || activity.getTrainer())) {
			return;
		}
	
		if (state == StravaResourceState.DETAILED) {
			assertNotNull(map.getPolyline());
			// Optional ßassertNotNull(map.getSummaryPolyline());
		}
		if (state == StravaResourceState.SUMMARY) {
			assertNull(map.getPolyline());
			// Optional assertNotNull(map.getSummaryPolyline());
		}
		if (state == StravaResourceState.META) {
			assertNotNull(map.getPolyline());
			assertNotNull(map.getSummaryPolyline());
		}
		if ((state == StravaResourceState.UNKNOWN) || (state == StravaResourceState.UPDATING)) {
			fail("Unexpected state " + state + " for map " + map); //$NON-NLS-1$ //$NON-NLS-2$
		}
	
	}

}
