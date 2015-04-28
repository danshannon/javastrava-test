package test.api.rest.segment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalDateTime;
import java.time.Month;

import org.junit.Test;

import javastrava.api.v3.model.StravaSegment;
import javastrava.api.v3.model.StravaSegmentEffort;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.api.v3.service.exception.UnauthorizedException;
import test.api.model.StravaSegmentEffortTest;
import test.api.rest.util.ArrayCallback;
import test.api.rest.util.PagingArrayMethodTest;
import test.issues.strava.Issue33;
import test.issues.strava.Issue86;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class ListSegmentEffortsTest extends PagingArrayMethodTest<StravaSegmentEffort, Long> {
	@Override
	protected ArrayCallback<StravaSegmentEffort> pagingCallback() {
		return (paging -> api().listSegmentEfforts(TestUtils.SEGMENT_VALID_ID, null, null, null, paging.getPage(),
				paging.getPageSize()));
	}

	@Test
	public void testListSegmentEfforts_filterByAll() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final LocalDateTime startDate = LocalDateTime.of(2009, Month.JANUARY, 1, 0, 0, 0);
			final LocalDateTime endDate = LocalDateTime.of(2015, Month.JANUARY, 31, 23, 59, 59);

			final StravaSegmentEffort[] efforts = api().listSegmentEfforts(TestUtils.SEGMENT_VALID_ID,
					TestUtils.ATHLETE_AUTHENTICATED_ID, startDate.toString(), endDate.toString(), 1, 1);
			assertNotNull(efforts);
			assertFalse(efforts.length == 0);
			assertEquals(1, efforts.length);
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

			final StravaSegmentEffort[] efforts = api().listSegmentEfforts(TestUtils.SEGMENT_VALID_ID, null,
					startDate.toString(), endDate.toString(), null, null);
			assertNotNull(efforts);
			assertFalse(0 == efforts.length);
			for (final StravaSegmentEffort effort : efforts) {
				assertNotNull(effort.getStartDateLocal());
				assertTrue(effort.getStartDateLocal().isAfter(startDate));
				assertTrue(effort.getStartDateLocal().isBefore(endDate));
				validate(effort);
			}
		} );
	}

	// 4. Filter by invalid athlete, valid segment
	@Test
	public void testListSegmentEfforts_filterByInvalidAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().listSegmentEfforts(TestUtils.SEGMENT_VALID_ID, TestUtils.ATHLETE_INVALID_ID, null, null, null,
						null);
			} catch (final NotFoundException e) {
				// expected
				return;
			}
			fail("Returned segment efforts for a non-existent athlete");
		} );
	}

	// 3. Filter by valid athlete, valid segment
	@Test
	public void testListSegmentEfforts_filterByValidAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentEffort[] efforts = api().listSegmentEfforts(TestUtils.SEGMENT_VALID_ID,
					TestUtils.ATHLETE_AUTHENTICATED_ID, null, null, null, null);
			assertNotNull(efforts);
			assertFalse(0 == efforts.length);
			for (final StravaSegmentEffort effort : efforts) {
				validate(effort);
				assertEquals(TestUtils.ATHLETE_AUTHENTICATED_ID, effort.getAthlete().getId());
			}
		} );
	}

	@Test
	public void testListSegmentEfforts_hazardousSegment() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// TODO This is a workaround for issue javastravav3api#33
			final Issue33 issue33 = new Issue33();
			if (issue33.isIssue()) {
				return;
			}
			// End of workaround

			final StravaSegmentEffort[] efforts = api().listSegmentEfforts(TestUtils.SEGMENT_HAZARDOUS_ID, null, null,
					null, null, null);
			assertNotNull(efforts);
			assertEquals(0, efforts.length);
		} );
	}

	// 2. No filtering, invalid segment
	@Test
	public void testListSegmentEfforts_invalidSegment() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().listSegmentEfforts(TestUtils.SEGMENT_INVALID_ID, null, null, null, null, null);
			} catch (final NotFoundException e) {
				// expected
				return;
			}
			fail("Returned segment efforts for a non-existent segment");
		} );
	}

	@Test
	public void testListSegmentEfforts_privateActivityWithoutViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegment segment = apiWithViewPrivate()
					.getSegmentEffort(TestUtils.SEGMENT_EFFORT_PRIVATE_ACTIVITY_ID).getSegment();
			final StravaSegmentEffort[] efforts = api().listSegmentEfforts(segment.getId(),
					TestUtils.ATHLETE_AUTHENTICATED_ID, null, null, null, null);
			assertNotNull(efforts);
			assertEquals(0, efforts.length);
		} );
	}

	@Test
	public void testListSegmentEfforts_privateActivityWithViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegment segment = apiWithViewPrivate()
					.getSegmentEffort(TestUtils.SEGMENT_EFFORT_PRIVATE_ACTIVITY_ID).getSegment();
			final StravaSegmentEffort[] efforts = apiWithViewPrivate().listSegmentEfforts(segment.getId(),
					TestUtils.ATHLETE_AUTHENTICATED_ID, null, null, null, null);
			assertNotNull(efforts);
			assertFalse(efforts.length == 0);
		} );
	}

	@Test
	public void testListSegmentEfforts_privateSegmentWithoutViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// TODO This is a workaround for issue javastravav3api#86
			if (new Issue86().isIssue()) {
				return;
			}
			// End of workaround

			try {
				api().listSegmentEfforts(TestUtils.SEGMENT_PRIVATE_ID, null, null, null, null, null);
			} catch (final UnauthorizedException e) {
				// expected
				return;
			}
			fail("Returned segment efforts for a private segment");
		} );
	}

	@Test
	public void testListSegmentEfforts_privateSegmentWithViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentEffort[] efforts = apiWithViewPrivate().listSegmentEfforts(TestUtils.SEGMENT_PRIVATE_ID,
					null, null, null, null, null);
			// Should not return an empty list
			assertNotNull(efforts);
			assertFalse(efforts.length == 0);
		} );
	}

	// Test cases
	// 1. No filtering, valid segment
	@Test
	public void testListSegmentEfforts_validSegment() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentEffort[] efforts = api().listSegmentEfforts(TestUtils.SEGMENT_VALID_ID, null, null, null,
					null, null);
			assertNotNull(efforts);
			assertFalse(efforts.length == 0);
			validateArray(efforts);
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
