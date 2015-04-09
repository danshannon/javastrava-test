package test.api.service.impl.segmentservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import org.junit.Test;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaSegment;
import javastrava.api.v3.model.StravaSegmentEffort;
import test.api.model.StravaSegmentEffortTest;
import test.api.service.StravaTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class ListAllSegmentEffortsTest extends StravaTest {
	@Test
	public void listAllSegmentEfforts_filterByAfterDate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final LocalDateTime afterDate = LocalDateTime.of(2015, Month.JANUARY, 1, 0, 0);
			final List<StravaSegmentEffort> efforts = strava().listAllSegmentEfforts(TestUtils.SEGMENT_VALID_ID, null,
					afterDate, null);
			assertNotNull(efforts);
			for (final StravaSegmentEffort effort : efforts) {
				assertTrue(effort.getStartDateLocal().isAfter(afterDate));
				StravaSegmentEffortTest.validateSegmentEffort(effort);
			}
		} );
	}

	@Test
	public void listAllSegmentEfforts_filterByBeforeDate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final LocalDateTime beforeDate = LocalDateTime.of(2013, Month.JANUARY, 1, 0, 0);
			final List<StravaSegmentEffort> efforts = strava().listAllSegmentEfforts(TestUtils.SEGMENT_VALID_ID, null,
					null, beforeDate);
			assertNotNull(efforts);
			for (final StravaSegmentEffort effort : efforts) {
				assertTrue(effort.getStartDateLocal().isBefore(beforeDate));
				StravaSegmentEffortTest.validateSegmentEffort(effort);
			}
		} );
	}

	@Test
	public void listAllSegmentEfforts_filterByDateRange() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final LocalDateTime afterDate = LocalDateTime.of(2013, Month.JANUARY, 1, 0, 0);
			final LocalDateTime beforeDate = LocalDateTime.of(2013, Month.DECEMBER, 1, 0, 0);
			final List<StravaSegmentEffort> efforts = strava().listAllSegmentEfforts(TestUtils.SEGMENT_VALID_ID, null,
					afterDate, beforeDate);
			assertNotNull(efforts);
			for (final StravaSegmentEffort effort : efforts) {
				assertTrue(effort.getStartDateLocal().isAfter(afterDate));
				assertTrue(effort.getStartDateLocal().isBefore(beforeDate));
				StravaSegmentEffortTest.validateSegmentEffort(effort);
			}
		} );
	}

	@Test
	public void listAllSegmentEfforts_filterByEverything() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final LocalDateTime afterDate = LocalDateTime.of(2013, Month.JANUARY, 1, 0, 0);
			final LocalDateTime beforeDate = LocalDateTime.of(2013, Month.DECEMBER, 1, 0, 0);
			final List<StravaSegmentEffort> efforts = strava().listAllSegmentEfforts(TestUtils.SEGMENT_VALID_ID,
					TestUtils.ATHLETE_AUTHENTICATED_ID, afterDate, beforeDate);
			assertNotNull(efforts);
			for (final StravaSegmentEffort effort : efforts) {
				assertTrue(effort.getStartDateLocal().isBefore(beforeDate));
				assertTrue(effort.getStartDateLocal().isAfter(afterDate));
				assertTrue(effort.getStartDateLocal().isBefore(beforeDate));
				StravaSegmentEffortTest.validateSegmentEffort(effort);
			}
		} );
	}

	@Test
	public void listAllSegmentEfforts_filterByInvalidAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegmentEffort> efforts = strava().listAllSegmentEfforts(TestUtils.SEGMENT_VALID_ID,
					TestUtils.ATHLETE_INVALID_ID, null, null);
			assertNull(efforts);
		} );
	}

	@Test
	public void listAllSegmentEfforts_filterByValidAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegmentEffort> efforts = strava().listAllSegmentEfforts(TestUtils.SEGMENT_VALID_ID,
					TestUtils.ATHLETE_AUTHENTICATED_ID, null, null);
			assertNotNull(efforts);
			for (final StravaSegmentEffort effort : efforts) {
				assertEquals(TestUtils.ATHLETE_AUTHENTICATED_ID, effort.getAthlete().getId());
				StravaSegmentEffortTest.validateSegmentEffort(effort);
			}
		} );
	}

	@Test
	public void listAllSegmentEfforts_hazardousSegment() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegmentEffort> efforts = strava().listAllSegmentEfforts(TestUtils.SEGMENT_HAZARDOUS_ID);
			assertNotNull(efforts);
			assertEquals(0, efforts.size());
		} );
	}

	@Test
	public void listAllSegmentEfforts_invalidSegment() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegmentEffort> efforts = strava().listAllSegmentEfforts(TestUtils.SEGMENT_INVALID_ID);
			assertNull(efforts);
		} );
	}

	@Test
	public void listAllSegmentEfforts_privateSegment() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegmentEffort> efforts = stravaWithViewPrivate()
					.listAllSegmentEfforts(TestUtils.SEGMENT_PRIVATE_ID);
			assertNotNull(efforts);
			for (final StravaSegmentEffort effort : efforts) {
				StravaSegmentEffortTest.validateSegmentEffort(effort);
			}
		} );
	}

	@Test
	public void listAllSegmentEfforts_privateSegmentNoViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegmentEffort> efforts = strava().listAllSegmentEfforts(TestUtils.SEGMENT_PRIVATE_ID);
			assertNotNull(efforts);
			assertTrue(efforts.isEmpty());
		} );
	}

	@Test
	public void listAllSegmentEfforts_privateSegmentOtherUser() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegmentEffort> efforts = stravaWithViewPrivate()
					.listAllSegmentEfforts(TestUtils.SEGMENT_OTHER_USER_PRIVATE_ID);
			assertNotNull(efforts);
			assertTrue(efforts.isEmpty());
		} );
	}

	@Test
	public void listAllSegmentEfforts_validSegment() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegmentEffort> efforts = strava().listAllSegmentEfforts(TestUtils.SEGMENT_VALID_ID);
			assertNotNull(efforts);
			for (final StravaSegmentEffort effort : efforts) {
				StravaSegmentEffortTest.validateSegmentEffort(effort);
			}
		} );
	}

	@Test
	public void testListAllSegmentEfforts_privateActivityWithoutViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentEffort segmentEffort = stravaWithViewPrivate()
					.getSegmentEffort(TestUtils.SEGMENT_EFFORT_PRIVATE_ACTIVITY_ID);
			final StravaSegment segment = segmentEffort.getSegment();
			final StravaActivity activity = segmentEffort.getActivity();
			final List<StravaSegmentEffort> efforts = strava().listAllSegmentEfforts(segment.getId());
			for (final StravaSegmentEffort effort : efforts) {
				if (effort.getActivity().getId().equals(activity.getId())) {
					fail("Returned segment effort for a private activity");
				}
			}
		} );
	}

	@Test
	public void testListAllSegmentEfforts_privateActivityWithViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentEffort segmentEffort = stravaWithViewPrivate()
					.getSegmentEffort(TestUtils.SEGMENT_EFFORT_PRIVATE_ACTIVITY_ID);
			final StravaSegment segment = segmentEffort.getSegment();
			final StravaActivity activity = segmentEffort.getActivity();
			final List<StravaSegmentEffort> efforts = stravaWithViewPrivate().listAllSegmentEfforts(segment.getId());
			boolean pass = false;
			for (final StravaSegmentEffort effort : efforts) {
				if (effort.getActivity().getId().equals(activity.getId())) {
					pass = true;
				}
			}
			assertTrue(pass);
		} );
	}

}
