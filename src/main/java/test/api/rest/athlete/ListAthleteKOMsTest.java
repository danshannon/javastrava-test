package test.api.rest.athlete;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import javastrava.api.v3.model.StravaSegmentEffort;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.api.v3.service.exception.UnauthorizedException;

import org.junit.Test;

import test.api.model.StravaSegmentEffortTest;
import test.api.rest.util.ArrayCallback;
import test.api.rest.util.PagingArrayMethodTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class ListAthleteKOMsTest extends PagingArrayMethodTest<StravaSegmentEffort, Long> {
	@Override
	protected ArrayCallback<StravaSegmentEffort> callback() {
		return (paging -> api().listAthleteKOMs(TestUtils.ATHLETE_AUTHENTICATED_ID, paging.getPage(), paging.getPageSize()));
	}

	// 3. Invalid athlete
	@Test
	public void testListAthleteKOMs_invalidAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().listAthleteKOMs(TestUtils.ATHLETE_INVALID_ID, null, null);
			} catch (final NotFoundException e) {
				// Expected
				return;
			}
			fail("Returned KOMs for an invalid athlete!");
		});
	}

	// 4. Private athlete
	public void testListAthleteKOMs_privateAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().listAthleteKOMs(TestUtils.ATHLETE_PRIVATE_ID, null, null);
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}
		});
	}

	// Test cases
	// 1. Valid athlete with some KOM's
	@Test
	public void testListAthleteKOMs_withKOM() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentEffort[] koms = api().listAthleteKOMs(TestUtils.ATHLETE_AUTHENTICATED_ID, null, null);
			assertNotNull(koms);
			assertFalse(koms.length == 0);
			for (final StravaSegmentEffort effort : koms) {
				StravaSegmentEffortTest.validateSegmentEffort(effort);
			}
		});
	}

	// 2. Valid athlete with no KOM's
	@Test
	public void testListAthleteKOMs_withoutKOM() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentEffort[] koms = api().listAthleteKOMs(TestUtils.ATHLETE_WITHOUT_KOMS, null, null);
			assertNotNull(koms);
			assertTrue(koms.length == 0);
		});
	}

	@Test
	public void testListAthleteKOMs_authenticatedAthletePrivateSegmentsWithViewPrivate() throws Exception {
		// TODO Not yet implemented
		fail("Not yet implemented");
	}

	@Test
	public void testListAthleteKOMs_authenticatedAthletePrivateSegmentsWithoutViewPrivate() throws Exception {
		// TODO Not yet implemented
		fail("Not yet implemented");
	}

	@Test
	public void testListAthleteKOMs_authenticatedAthletePrivateActivitiesWithViewPrivate() throws Exception {
		// TODO Not yet implemented
		fail("Not yet implemented");
	}

	@Test
	public void testListAthleteKOMs_authenticatedAthletePrivateActivitiesWithoutViewPrivate() throws Exception {
		// TODO Not yet implemented
		fail("Not yet implemented");
	}

	@Override
	protected void validate(final StravaSegmentEffort effort) {
		validate(effort, effort.getId(), effort.getResourceState());

	}

	@Override
	protected void validate(final StravaSegmentEffort effort, final Long id, final StravaResourceState state) {
		StravaSegmentEffortTest.validateSegmentEffort(effort, id, state);

	}

}
