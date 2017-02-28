package test.service.impl.athleteservice;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import javastrava.api.v3.model.StravaSegmentEffort;
import javastrava.api.v3.service.exception.UnauthorizedException;
import test.api.model.StravaSegmentEffortTest;
import test.service.standardtests.PagingListMethodTest;
import test.service.standardtests.callbacks.ListCallback;
import test.service.standardtests.callbacks.PagingListCallback;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class ListAthleteKOMsTest extends PagingListMethodTest<StravaSegmentEffort, Integer> {
	@Override
	protected PagingListCallback<StravaSegmentEffort, Integer> pagingLister() {
		return ((strava, paging, id) -> strava.listAthleteKOMs(id, paging));
	}

	@Test
	public void testListAllAthleteKOMs_otherAthletePrivateActivities() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegmentEffort> efforts = TestUtils.strava().listAthleteKOMs(TestUtils.ATHLETE_VALID_ID);
			for (final StravaSegmentEffort effort : efforts) {
				try {
					TestUtils.strava().getActivity(effort.getActivity().getId());
				} catch (final UnauthorizedException e) {
					fail("Returned KOM's for a private activity!"); //$NON-NLS-1$
				}
			}
		});
	}

	@Test
	public void testListAthleteKOMs_authenticatedAthletePrivateActivitiesWithoutViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegmentEffort> efforts = TestUtils.strava().listAthleteKOMs(TestUtils.ATHLETE_AUTHENTICATED_ID);
			for (final StravaSegmentEffort effort : efforts) {
				try {
					TestUtils.strava().getActivity(effort.getActivity().getId());
				} catch (final UnauthorizedException e) {
					fail("Returned KOM's for a private activity!");
				}
			}
		});
	}

	@Test
	public void testListAthleteKOMs_authenticatedAthletePrivateActivitiesWithViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegmentEffort> efforts = TestUtils.stravaWithViewPrivate()
					.listAthleteKOMs(TestUtils.ATHLETE_AUTHENTICATED_ID);
			for (final StravaSegmentEffort effort : efforts) {
				try {
					TestUtils.strava().getActivity(effort.getActivity().getId());
				} catch (final UnauthorizedException e) {
					fail("Returned KOM's for a private activity!");
				}
			}
		});
	}

	@Test
	public void testListAthleteKOMs_authenticatedAthletePrivateSegmentsWithoutViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegmentEffort> efforts = TestUtils.strava().listAthleteKOMs(TestUtils.ATHLETE_AUTHENTICATED_ID);
			for (final StravaSegmentEffort effort : efforts) {
				try {
					TestUtils.strava().getSegment(effort.getSegment().getId());
				} catch (final UnauthorizedException e) {
					fail("Returned KOM's for a private segment!");
				}
			}
		});
	}

	@Test
	public void testListAthleteKOMs_authenticatedAthletePrivateSegmentsWithViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegmentEffort> efforts = TestUtils.stravaWithViewPrivate()
					.listAllAthleteKOMs(TestUtils.ATHLETE_AUTHENTICATED_ID);
			for (final StravaSegmentEffort effort : efforts) {
				try {
					TestUtils.strava().getSegment(effort.getSegment().getId());
				} catch (final UnauthorizedException e) {
					fail("Returned KOM's for a private segment!");
				}
			}
		});
	}

	// 3. Invalid athlete
	@Test
	public void testListAthleteKOMs_invalidAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			List<StravaSegmentEffort> koms = null;
			koms = TestUtils.strava().listAthleteKOMs(TestUtils.ATHLETE_INVALID_ID);

			assertNull(koms);
		});
	}

	@Test
	public void testListAthleteKOMs_otherAthletePrivateSegments() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegmentEffort> efforts = TestUtils.strava().listAthleteKOMs(TestUtils.ATHLETE_VALID_ID);
			for (final StravaSegmentEffort effort : efforts) {
				try {
					TestUtils.strava().getSegment(effort.getSegment().getId());
				} catch (final UnauthorizedException e) {
					fail("Returned KOM's for a private segment!");
				}
			}
		});
	}

	// Test cases
	// 1. Valid athlete with some KOM's
	@Test
	public void testListAthleteKOMs_withKOM() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegmentEffort> koms = TestUtils.strava().listAthleteKOMs(TestUtils.ATHLETE_AUTHENTICATED_ID);
			assertNotNull(koms);
			assertFalse(koms.size() == 0);
			for (final StravaSegmentEffort effort : koms) {
				StravaSegmentEffortTest.validateSegmentEffort(effort);
			}
		});
	}

	// 2. Valid athlete with no KOM's
	@Test
	public void testListAthleteKOMs_withoutKOM() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegmentEffort> koms = TestUtils.strava().listAthleteKOMs(TestUtils.ATHLETE_WITHOUT_KOMS);
			assertNotNull(koms);
			assertTrue(koms.size() == 0);
		});
	}

	@Override
	protected void validate(final StravaSegmentEffort effort) {
		StravaSegmentEffortTest.validateSegmentEffort(effort);
	}

	@Override
	protected ListCallback<StravaSegmentEffort, Integer> lister() {
		return ((strava, id) -> strava.listAthleteKOMs(id));
	}

	@Override
	protected Integer idPrivate() {
		return null;
	}

	@Override
	protected Integer idPrivateBelongsToOtherUser() {
		return TestUtils.ATHLETE_PRIVATE_ID;
	}

	@Override
	protected Integer idValidWithEntries() {
		return TestUtils.ATHLETE_AUTHENTICATED_ID;
	}

	@Override
	protected Integer idValidWithoutEntries() {
		return TestUtils.ATHLETE_WITHOUT_KOMS;
	}

	@Override
	protected Integer idInvalid() {
		return TestUtils.ATHLETE_INVALID_ID;
	}

}
