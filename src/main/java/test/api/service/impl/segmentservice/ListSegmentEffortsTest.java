package test.api.service.impl.segmentservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import org.junit.Test;

import javastrava.api.v3.model.StravaSegment;
import javastrava.api.v3.model.StravaSegmentEffort;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.util.Paging;
import test.api.model.StravaSegmentEffortTest;
import test.api.service.impl.util.ListCallback;
import test.api.service.impl.util.PagingListMethodTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class ListSegmentEffortsTest extends PagingListMethodTest<StravaSegmentEffort, Long> {
	@Override
	protected ListCallback<StravaSegmentEffort> callback() {
		return (paging -> strava().listSegmentEfforts(TestUtils.SEGMENT_VALID_ID, paging));
	}

	@Test
	public void testListSegmentEfforts_filterByAll() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final LocalDateTime startDate = LocalDateTime.of(2009, Month.JANUARY, 1, 0, 0, 0);
			final LocalDateTime endDate = LocalDateTime.of(2015, Month.JANUARY, 31, 23, 59, 59);

			final List<StravaSegmentEffort> efforts = strava().listSegmentEfforts(TestUtils.SEGMENT_VALID_ID,
					TestUtils.ATHLETE_AUTHENTICATED_ID, startDate, endDate, new Paging(1, 1));
			assertNotNull(efforts);
			assertFalse(efforts.isEmpty());
			assertEquals(1, efforts.size());
			for (final StravaSegmentEffort effort : efforts) {
				assertTrue(effort.getStartDateLocal().isAfter(startDate));
				assertTrue(effort.getStartDateLocal().isBefore(endDate));
				assertEquals(TestUtils.ATHLETE_AUTHENTICATED_ID, effort.getAthlete().getId());
				assertEquals(TestUtils.SEGMENT_VALID_ID, effort.getSegment().getId());
				validate(effort);
			}
		} );
	}

	// 7. Filter by date range, valid segment
	@Test
	public void testListSegmentEfforts_filterByDateRange() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final LocalDateTime startDate = LocalDateTime.of(2014, Month.JANUARY, 1, 0, 0, 0);
			final LocalDateTime endDate = LocalDateTime.of(2014, Month.JANUARY, 31, 23, 59, 59);

			final List<StravaSegmentEffort> efforts = strava().listSegmentEfforts(TestUtils.SEGMENT_VALID_ID, null,
					startDate, endDate);
			assertNotNull(efforts);
			assertFalse(0 == efforts.size());
			for (final StravaSegmentEffort effort : efforts) {
				assertNotNull(effort.getStartDateLocal());
				assertTrue(effort.getStartDateLocal().isAfter(startDate));
				assertTrue(effort.getStartDateLocal().isBefore(endDate));
				validate(effort);
			}
		} );
	}

	// 6. Filter by end date, valid segment
	@Test
	public void testListSegmentEfforts_filterByEndDate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final LocalDateTime endDate = LocalDateTime.of(2013, Month.DECEMBER, 1, 23, 59, 59);

			final List<StravaSegmentEffort> efforts = strava().listSegmentEfforts(TestUtils.SEGMENT_VALID_ID, null,
					null, endDate);
			assertNotNull(efforts);
			assertFalse(0 == efforts.size());
			for (final StravaSegmentEffort effort : efforts) {
				assertNotNull(effort.getStartDateLocal());
				assertTrue(effort.getStartDateLocal().isBefore(endDate));
				validate(effort);
			}
		} );
	}

	// 4. Filter by invalid athlete, valid segment
	@Test
	public void testListSegmentEfforts_filterByInvalidAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegmentEffort> efforts = strava().listSegmentEfforts(TestUtils.SEGMENT_VALID_ID,
					TestUtils.ATHLETE_INVALID_ID, null, null);
			assertNull(efforts);
		} );
	}

	// 5. Filter by start date, valid segment
	@Test
	public void testListSegmentEfforts_filterByStartDate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final LocalDateTime startDate = LocalDateTime.of(2014, Month.JANUARY, 1, 0, 0);

			final List<StravaSegmentEffort> efforts = strava().listSegmentEfforts(TestUtils.SEGMENT_VALID_ID, null,
					startDate, null);
			assertNotNull(efforts);
			assertFalse(0 == efforts.size());
			for (final StravaSegmentEffort effort : efforts) {
				assertNotNull(effort.getStartDateLocal());
				assertTrue(effort.getStartDateLocal().isAfter(startDate));
				validate(effort);
			}
		} );
	}

	// 3. Filter by valid athlete, valid segment
	@Test
	public void testListSegmentEfforts_filterByValidAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegmentEffort> efforts = strava().listSegmentEfforts(TestUtils.SEGMENT_VALID_ID,
					TestUtils.ATHLETE_AUTHENTICATED_ID, null, null);
			assertNotNull(efforts);
			assertFalse(0 == efforts.size());
			for (final StravaSegmentEffort effort : efforts) {
				validate(effort);
				assertEquals(TestUtils.ATHLETE_AUTHENTICATED_ID, effort.getAthlete().getId());
			}
		} );
	}

	@Test
	public void testListSegmentEfforts_hazardousSegment() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegmentEffort> efforts = strava().listSegmentEfforts(TestUtils.SEGMENT_HAZARDOUS_ID);
			assertNotNull(efforts);
			assertEquals(0, efforts.size());
		} );
	}

	// 2. No filtering, invalid segment
	@Test
	public void testListSegmentEfforts_invalidSegment() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegmentEffort> efforts = strava().listSegmentEfforts(TestUtils.SEGMENT_INVALID_ID);
			assertNull(efforts);
		} );
	}

	@Test
	public void testListSegmentEfforts_privateActivityWithoutViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegment segment = stravaWithViewPrivate()
					.getSegmentEffort(TestUtils.SEGMENT_EFFORT_PRIVATE_ACTIVITY_ID).getSegment();
			final List<StravaSegmentEffort> efforts = strava().listSegmentEfforts(segment.getId(),
					TestUtils.ATHLETE_AUTHENTICATED_ID, null, null);
			assertNotNull(efforts);
			assertEquals(0, efforts.size());
		} );
	}

	@Test
	public void testListSegmentEfforts_privateActivityWithViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegment segment = stravaWithViewPrivate()
					.getSegmentEffort(TestUtils.SEGMENT_EFFORT_PRIVATE_ACTIVITY_ID).getSegment();
			final List<StravaSegmentEffort> efforts = stravaWithViewPrivate().listSegmentEfforts(segment.getId(),
					TestUtils.ATHLETE_AUTHENTICATED_ID, null, null);
			assertNotNull(efforts);
			assertFalse(efforts.size() == 0);
		} );
	}

	@Test
	public void testListSegmentEfforts_privateSegmentWithoutViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegmentEffort> efforts = strava().listSegmentEfforts(TestUtils.SEGMENT_PRIVATE_ID);
			// Should return an empty list
			assertNotNull(efforts);
			assertEquals(0, efforts.size());
		} );
	}

	@Test
	public void testListSegmentEfforts_privateSegmentWithViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegmentEffort> efforts = stravaWithViewPrivate()
					.listSegmentEfforts(TestUtils.SEGMENT_PRIVATE_ID);
			// Should not return an empty list
			assertNotNull(efforts);
			assertFalse(efforts.isEmpty());
		} );
	}

	// Test cases
	// 1. No filtering, valid segment
	@Test
	public void testListSegmentEfforts_validSegment() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegmentEffort> efforts = strava().listSegmentEfforts(TestUtils.SEGMENT_VALID_ID);
			assertNotNull(efforts);
			assertFalse(efforts.size() == 0);
			validateList(efforts);
		} );
	}

	@Override
	protected void validate(final StravaSegmentEffort effort) {
		StravaSegmentEffortTest.validateSegmentEffort(effort);

	}

	@Override
	protected void validate(final StravaSegmentEffort effort, final Long id, final StravaResourceState state) {
		StravaSegmentEffortTest.validateSegmentEffort(effort, id, state);

	}

}
