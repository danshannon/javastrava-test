package test.api.service.impl.athleteservice;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import javastrava.api.v3.model.StravaSegmentEffort;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.service.exception.UnauthorizedException;
import test.api.model.StravaSegmentEffortTest;
import test.api.service.impl.util.ListCallback;
import test.api.service.impl.util.PagingListMethodTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class ListAthleteKOMsTest extends PagingListMethodTest<StravaSegmentEffort, Long> {
	@Override
	protected ListCallback<StravaSegmentEffort> callback() {
		return (paging -> strava().listAthleteKOMs(TestUtils.ATHLETE_AUTHENTICATED_ID, paging));
	}

	@Test
	public void testListAllAthleteKOMs_otherAthletePrivateActivities() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegmentEffort> efforts = strava().listAthleteKOMs(TestUtils.ATHLETE_VALID_ID);
			for (final StravaSegmentEffort effort : efforts) {
				try {
					strava().getActivity(effort.getActivity().getId());
				} catch (final UnauthorizedException e) {
					fail("Returned KOM's for a private activity!");
				}
			}
		} );
	}

	@Test
	public void testListAthleteKOMs_authenticatedAthletePrivateActivitiesWithoutViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegmentEffort> efforts = strava().listAthleteKOMs(TestUtils.ATHLETE_AUTHENTICATED_ID);
			for (final StravaSegmentEffort effort : efforts) {
				try {
					strava().getActivity(effort.getActivity().getId());
				} catch (final UnauthorizedException e) {
					fail("Returned KOM's for a private activity!");
				}
			}
		} );
	}

	@Test
	public void testListAthleteKOMs_authenticatedAthletePrivateActivitiesWithViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegmentEffort> efforts = stravaWithViewPrivate()
					.listAthleteKOMs(TestUtils.ATHLETE_AUTHENTICATED_ID);
			for (final StravaSegmentEffort effort : efforts) {
				try {
					strava().getActivity(effort.getActivity().getId());
				} catch (final UnauthorizedException e) {
					fail("Returned KOM's for a private activity!");
				}
			}
		} );
	}

	@Test
	public void testListAthleteKOMs_authenticatedAthletePrivateSegmentsWithoutViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegmentEffort> efforts = strava().listAthleteKOMs(TestUtils.ATHLETE_AUTHENTICATED_ID);
			for (final StravaSegmentEffort effort : efforts) {
				try {
					strava().getSegment(effort.getSegment().getId());
				} catch (final UnauthorizedException e) {
					fail("Returned KOM's for a private segment!");
				}
			}
		} );
	}

	@Test
	public void testListAthleteKOMs_authenticatedAthletePrivateSegmentsWithViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegmentEffort> efforts = stravaWithViewPrivate()
					.listAllAthleteKOMs(TestUtils.ATHLETE_AUTHENTICATED_ID);
			for (final StravaSegmentEffort effort : efforts) {
				try {
					strava().getSegment(effort.getSegment().getId());
				} catch (final UnauthorizedException e) {
					fail("Returned KOM's for a private segment!");
				}
			}
		} );
	}

	// 3. Invalid athlete
	@Test
	public void testListAthleteKOMs_invalidAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			List<StravaSegmentEffort> koms = null;
			koms = strava().listAthleteKOMs(TestUtils.ATHLETE_INVALID_ID);

			assertNull(koms);
		} );
	}

	@Test
	public void testListAthleteKOMs_otherAthletePrivateSegments() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegmentEffort> efforts = strava().listAthleteKOMs(TestUtils.ATHLETE_VALID_ID);
			for (final StravaSegmentEffort effort : efforts) {
				try {
					strava().getSegment(effort.getSegment().getId());
				} catch (final UnauthorizedException e) {
					fail("Returned KOM's for a private segment!");
				}
			}
		} );
	}

	// 4. Private athlete
	public void testListAthleteKOMs_privateAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegmentEffort> koms = strava().listAthleteKOMs(TestUtils.ATHLETE_PRIVATE_ID);
			assertNotNull(koms);
			assertTrue(koms.isEmpty());
		} );
	}

	// Test cases
	// 1. Valid athlete with some KOM's
	@Test
	public void testListAthleteKOMs_withKOM() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegmentEffort> koms = strava().listAthleteKOMs(TestUtils.ATHLETE_AUTHENTICATED_ID);
			assertNotNull(koms);
			assertFalse(koms.size() == 0);
			for (final StravaSegmentEffort effort : koms) {
				StravaSegmentEffortTest.validateSegmentEffort(effort);
			}
		} );
	}

	// 2. Valid athlete with no KOM's
	@Test
	public void testListAthleteKOMs_withoutKOM() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegmentEffort> koms = strava().listAthleteKOMs(TestUtils.ATHLETE_WITHOUT_KOMS);
			assertNotNull(koms);
			assertTrue(koms.size() == 0);
		} );
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
