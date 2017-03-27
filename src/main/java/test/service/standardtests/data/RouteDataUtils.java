package test.service.standardtests.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Random;

import org.jfairy.Fairy;
import org.jfairy.producer.text.TextProducer;

import javastrava.api.v3.model.StravaRoute;
import javastrava.api.v3.model.reference.StravaResourceState;
import test.utils.TestUtils;

/**
 * <p>
 * Test data utilities for routes
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class RouteDataUtils {
	private static Random		random	= new Random();
	private static TextProducer	fairy	= Fairy.create().textProducer();

	/**
	 * Identifier of a valid route that belongs to the authenticated user
	 */
	public final static Integer	ROUTE_VALID_ID		= TestUtils.integerProperty("test.routeId");		//$NON-NLS-1$ ;
	/**
	 * Invalid route identifier
	 */
	public final static Integer	ROUTE_INVALID_ID	= TestUtils.integerProperty("test.routeInvalidId");	//$NON-NLS-1$ ;

	/**
	 * Generate a random route with the required resource state
	 *
	 * @param resourceState
	 *            Resource state required
	 * @return The generated route
	 */
	@SuppressWarnings("boxing")
	public static StravaRoute testRoute(StravaResourceState resourceState) {
		if ((resourceState == StravaResourceState.UNKNOWN) || (resourceState == StravaResourceState.UPDATING)) {
			throw new IllegalArgumentException();
		}

		final StravaRoute route = new StravaRoute();

		// Set data which occurs for every resource state
		route.setResourceState(resourceState);
		route.setId(random.nextInt(2 ^ (31 - 1)));
		route.setName(fairy.word(5));

		// Return only the above data for META and PRIVATE clubs
		if ((resourceState == StravaResourceState.META) || (resourceState == StravaResourceState.PRIVATE)) {
			return route;
		}

		// Set data common to SUMMARY and DETAILED representations
		route.setDescription(fairy.paragraph());
		route.setAthlete(AthleteDataUtils.testAthlete(StravaResourceState.SUMMARY));
		route.setDistance(random.nextFloat());
		route.setElevationGain(random.nextFloat());
		route.setMap(MapDataUtils.testMap(StravaResourceState.SUMMARY));
		route.setType(RefDataUtils.randomRouteType());
		route.setSubType(RefDataUtils.randomRouteSubType());
		route.setIsPrivate(random.nextBoolean());
		route.setStarred(random.nextBoolean());
		route.setTimestamp(random.nextLong());

		// If this is a SUMMARY representation return it now
		if (resourceState == StravaResourceState.SUMMARY) {
			return route;
		}

		// Set the rest
		route.setSegments(SegmentDataUtils.testSegmentList(StravaResourceState.META, random.nextInt(1000)));

		// Return the route
		return route;

	}

	/**
	 * @param route
	 *            Route to be validated
	 */
	public static void validateRoute(StravaRoute route) {
		validateRoute(route, route.getId(), route.getResourceState());
	}

	/**
	 * @param route
	 *            Route to be validated
	 * @param id
	 *            Expected value of the identifier
	 * @param resourceState
	 *            Expected resource state
	 */
	public static void validateRoute(StravaRoute route, Integer id, StravaResourceState resourceState) {
		assertEquals(id, route.getId());
		assertEquals(resourceState, route.getResourceState());
		assertNotNull(route.getName());
	}

}
