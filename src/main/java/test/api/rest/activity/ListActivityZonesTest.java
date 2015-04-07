package test.api.rest.activity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaActivityZone;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.api.v3.service.exception.UnauthorizedException;

import org.junit.Test;

import test.api.model.StravaActivityZoneTest;
import test.api.rest.APITest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class ListActivityZonesTest extends APITest {
	/**
	 * <p>
	 * Attempt to list {@link StravaActivityZone zones} for an {@link StravaActivity} which doesn't have any
	 * </p>
	 *
	 * <p>
	 * Should return an empty array
	 * </p>
	 *
	 * @throws Exception
	 *
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testListActivityZones_hasNoZones() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaActivityZone[] zones = api().listActivityZones(TestUtils.ACTIVITY_WITHOUT_ZONES);

			assertNotNull("Returned null activity zones for an activity without zones (should return an empty array)", zones);
			assertEquals("Returned an non-empty array of activity zones for an activity without zones", 0, zones.length);
		});
	}

	/**
	 * <p>
	 * List {@link StravaActivityZone activity zones} for an {@link StravaActivity} which has them
	 * </p>
	 *
	 * @throws Exception
	 *
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testListActivityZones_hasZones() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaActivityZone[] zones = api().listActivityZones(TestUtils.ACTIVITY_WITH_ZONES);

			assertNotNull("Returned null activity zones for an activity with zones", zones);
			assertNotEquals("Returned an empty array of activity zones for an activity with zones", 0, zones.length);
			for (final StravaActivityZone zone : zones) {
				StravaActivityZoneTest.validateActivityZone(zone, zone.getResourceState());
			}
		});
	}

	/**
	 * <p>
	 * Attempt to list {@link StravaActivityZone zones} for an {@link StravaActivity} which doesn't exist
	 * </p>
	 *
	 * <p>
	 * Should return <code>null</code>
	 * </p>
	 *
	 * @throws Exception
	 *
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testListActivityZones_invalidActivity() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().listActivityZones(TestUtils.ACTIVITY_INVALID);
			} catch (final NotFoundException e) {
				// Expected
				return;
			}
			fail("Returned activity zones for a non-existent activity!");
		});
	}

	@Test
	public void testListActivityZones_privateActivity() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().listActivityZones(TestUtils.ACTIVITY_PRIVATE_OTHER_USER);
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}
			fail("Returned activity zones for a private activity belonging to another user!");
		});
	}

	@Test
	public void testListActivityZones_privateActivityWithViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaActivityZone[] zones = apiWithViewPrivate().listActivityZones(TestUtils.ACTIVITY_PRIVATE_WITH_PHOTOS);
			assertNotNull(zones);
			assertFalse(zones.length == 0);
		});
	}

	@Test
	public void testListActivityZones_privateActivityWithoutViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().listActivityZones(TestUtils.ACTIVITY_PRIVATE_WITH_PHOTOS);
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}
			fail("Returned activity zones for a private activity, but don't have view_private scope!");
		});
	}
}
