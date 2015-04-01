package test.api.rest.segment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.Month;

import javastrava.api.v3.model.StravaSegmentEffort;
import javastrava.api.v3.model.reference.StravaResourceState;

import org.junit.Test;

import test.api.model.StravaSegmentEffortTest;
import test.api.rest.util.ArrayCallback;
import test.api.rest.util.PagingArrayMethodTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class ListSegmentEffortsTest extends PagingArrayMethodTest<StravaSegmentEffort, Long> {
	@Override
	protected ArrayCallback<StravaSegmentEffort> callback() {
		return (paging -> api().listSegmentEfforts(TestUtils.SEGMENT_VALID_ID, null, null, null, paging.getPage(), paging.getPageSize()));
	}

	@Test
	public void testListSegmentEfforts_filterByAll() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final LocalDateTime startDate = LocalDateTime.of(2009, Month.JANUARY, 1, 0, 0, 0);
			final LocalDateTime endDate = LocalDateTime.of(2015, Month.JANUARY, 31, 23, 59, 59);

			final StravaSegmentEffort[] efforts = api().listSegmentEfforts(TestUtils.SEGMENT_VALID_ID, TestUtils.ATHLETE_AUTHENTICATED_ID,
					startDate.toString(), endDate.toString(), 1, 1);
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
		});
	}

	// 7. Filter by date range, valid segment
	@Test
	public void testListSegmentEfforts_filterByDateRange() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final LocalDateTime startDate = LocalDateTime.of(2014, Month.JANUARY, 1, 0, 0, 0);
			final LocalDateTime endDate = LocalDateTime.of(2014, Month.JANUARY, 31, 23, 59, 59);

			final StravaSegmentEffort[] efforts = api().listSegmentEfforts(TestUtils.SEGMENT_VALID_ID, null, startDate.toString(), endDate.toString(), null,
					null);
			assertNotNull(efforts);
			assertFalse(0 == efforts.length);
			for (final StravaSegmentEffort effort : efforts) {
				assertNotNull(effort.getStartDateLocal());
				assertTrue(effort.getStartDateLocal().isAfter(startDate));
				assertTrue(effort.getStartDateLocal().isBefore(endDate));
				validate(effort);
			}
		});
	}

	// 6. Filter by end date, valid segment
	@Test
	public void testListSegmentEfforts_filterByEndDate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final LocalDateTime endDate = LocalDateTime.of(2013, Month.DECEMBER, 1, 23, 59, 59);

			final StravaSegmentEffort[] efforts = api().listSegmentEfforts(TestUtils.SEGMENT_VALID_ID, null, null, endDate.toString(), null, null);
			assertNotNull(efforts);
			assertFalse(0 == efforts.length);
			for (final StravaSegmentEffort effort : efforts) {
				assertNotNull(effort.getStartDateLocal());
				assertTrue(effort.getStartDateLocal().isBefore(endDate));
				validate(effort);
			}
		});
	}

	// 4. Filter by invalid athlete, valid segment
	@Test
	public void testListSegmentEfforts_filterByInvalidAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentEffort[] efforts = api().listSegmentEfforts(TestUtils.SEGMENT_VALID_ID, TestUtils.ATHLETE_INVALID_ID, null, null, null, null);
			assertNull(efforts);
		});
	}

	// 5. Filter by start date, valid segment
	@Test
	public void testListSegmentEfforts_filterByStartDate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final LocalDateTime startDate = LocalDateTime.of(2014, Month.JANUARY, 1, 0, 0);

			final StravaSegmentEffort[] efforts = api().listSegmentEfforts(TestUtils.SEGMENT_VALID_ID, null, startDate.toString(), null, null, null);
			assertNotNull(efforts);
			assertFalse(0 == efforts.length);
			for (final StravaSegmentEffort effort : efforts) {
				assertNotNull(effort.getStartDateLocal());
				assertTrue(effort.getStartDateLocal().isAfter(startDate));
				validate(effort);
			}
		});
	}

	// 3. Filter by valid athlete, valid segment
	@Test
	public void testListSegmentEfforts_filterByValidAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentEffort[] efforts = api().listSegmentEfforts(TestUtils.SEGMENT_VALID_ID, TestUtils.ATHLETE_AUTHENTICATED_ID, null, null, null,
					null);
			assertNotNull(efforts);
			assertFalse(0 == efforts.length);
			for (final StravaSegmentEffort effort : efforts) {
				validate(effort);
				assertEquals(TestUtils.ATHLETE_AUTHENTICATED_ID, effort.getAthlete().getId());
			}
		});
	}

	@Test
	public void testListSegmentEfforts_hazardousSegment() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentEffort[] efforts = api().listSegmentEfforts(TestUtils.SEGMENT_HAZARDOUS_ID, null, null, null, null, null);
			assertNotNull(efforts);
			assertEquals(0, efforts.length);
		});
	}

	// 2. No filtering, invalid segment
	@Test
	public void testListSegmentEfforts_invalidSegment() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentEffort[] efforts = api().listSegmentEfforts(TestUtils.SEGMENT_INVALID_ID, null, null, null, null, null);
			assertNull(efforts);
		});
	}

	// Test cases
	// 1. No filtering, valid segment
	@Test
	public void testListSegmentEfforts_validSegment() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentEffort[] efforts = api().listSegmentEfforts(TestUtils.SEGMENT_VALID_ID, null, null, null, null, null);
			assertNotNull(efforts);
			assertFalse(efforts.length == 0);
			validateList(efforts);
		});
	}

	@Test
	public void testListSegmentEfforts_privateSegmentWithoutViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentEffort[] efforts = api().listSegmentEfforts(TestUtils.SEGMENT_PRIVATE_ID, null, null, null, null, null);
			// Should return an empty list
			assertNotNull(efforts);
			assertEquals(0, efforts.length);
		});
	}

	@Test
	public void testListSegmentEfforts_privateSegmentWithViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentEffort[] efforts = apiWithViewPrivate().listSegmentEfforts(TestUtils.SEGMENT_PRIVATE_ID, null, null, null, null, null);
			// Should not return an empty list
			assertNotNull(efforts);
			assertFalse(efforts.length == 0);
		});
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
