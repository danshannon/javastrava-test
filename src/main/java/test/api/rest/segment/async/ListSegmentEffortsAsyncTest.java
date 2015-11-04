/**
 *
 */
package test.api.rest.segment.async;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalDateTime;
import java.time.Month;

import javastrava.api.v3.model.StravaSegment;
import javastrava.api.v3.model.StravaSegmentEffort;
import javastrava.api.v3.service.exception.NotFoundException;

import org.junit.Test;

import test.api.rest.segment.ListSegmentEffortsTest;
import test.issues.strava.Issue33;
import test.issues.strava.Issue86;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * @author danshannon
 *
 */
public class ListSegmentEffortsAsyncTest extends ListSegmentEffortsTest {
	/**
	 * No-args constructor provides the callbacks
	 */
	public ListSegmentEffortsAsyncTest() {
		this.listCallback = (api, id) -> api.listSegmentEffortsAsync(id, null, null, null, null, null).get();
		this.pagingCallback = paging -> api().listSegmentEffortsAsync(validId(), null, null, null, paging.getPage(), paging.getPageSize()).get();
	}

	@Override
	@Test
	public void testListSegmentEfforts_filterByAll() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final LocalDateTime startDate = LocalDateTime.of(2009, Month.JANUARY, 1, 0, 0, 0);
			final LocalDateTime endDate = LocalDateTime.of(2015, Month.JANUARY, 31, 23, 59, 59);

			final StravaSegmentEffort[] efforts = api().listSegmentEffortsAsync(TestUtils.SEGMENT_VALID_ID, TestUtils.ATHLETE_AUTHENTICATED_ID,
					startDate.toString(), endDate.toString(), 1, 1).get();
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
	@Override
	@Test
	public void testListSegmentEfforts_filterByDateRange() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final LocalDateTime startDate = LocalDateTime.of(2014, Month.JANUARY, 1, 0, 0, 0);
			final LocalDateTime endDate = LocalDateTime.of(2014, Month.JANUARY, 31, 23, 59, 59);

			final StravaSegmentEffort[] efforts = api().listSegmentEffortsAsync(TestUtils.SEGMENT_VALID_ID, null, startDate.toString(), endDate.toString(),
					null, null).get();
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

	// 4. Filter by invalid athlete, valid segment
	@Override
	@Test
	public void testListSegmentEfforts_filterByInvalidAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().listSegmentEffortsAsync(TestUtils.SEGMENT_VALID_ID, TestUtils.ATHLETE_INVALID_ID, null, null, null, null).get();
			} catch (final NotFoundException e) {
				// expected
				return;
			}
			fail("Returned segment efforts for a non-existent athlete");
		});
	}

	// 3. Filter by valid athlete, valid segment
	@Override
	@Test
	public void testListSegmentEfforts_filterByValidAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentEffort[] efforts = api().listSegmentEffortsAsync(TestUtils.SEGMENT_VALID_ID, TestUtils.ATHLETE_AUTHENTICATED_ID, null, null,
					null, null).get();
			assertNotNull(efforts);
			assertFalse(0 == efforts.length);
			for (final StravaSegmentEffort effort : efforts) {
				validate(effort);
				assertEquals(TestUtils.ATHLETE_AUTHENTICATED_ID, effort.getAthlete().getId());
			}
		});
	}

	@Override
	@Test
	public void testListSegmentEfforts_hazardousSegment() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// TODO This is a workaround for issue javastravav3api#33
			final Issue33 issue33 = new Issue33();
			if (issue33.isIssue()) {
				return;
			}
			// End of workaround

			final StravaSegmentEffort[] efforts = api().listSegmentEffortsAsync(TestUtils.SEGMENT_HAZARDOUS_ID, null, null, null, null, null).get();
			assertNotNull(efforts);
			assertEquals(0, efforts.length);
		});
	}

	@Override
	@Test
	public void testListSegmentEfforts_privateActivityWithoutViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegment segment = apiWithViewPrivate().getSegmentEffort(TestUtils.SEGMENT_EFFORT_PRIVATE_ACTIVITY_ID).getSegment();
			final StravaSegmentEffort[] efforts = api().listSegmentEffortsAsync(segment.getId(), TestUtils.ATHLETE_AUTHENTICATED_ID, null, null, null, null)
					.get();
			assertNotNull(efforts);
			assertEquals(0, efforts.length);
		});
	}

	@Override
	@Test
	public void testListSegmentEfforts_privateActivityWithViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegment segment = apiWithViewPrivate().getSegmentEffort(TestUtils.SEGMENT_EFFORT_PRIVATE_ACTIVITY_ID).getSegment();
			final StravaSegmentEffort[] efforts = apiWithViewPrivate().listSegmentEffortsAsync(segment.getId(), TestUtils.ATHLETE_AUTHENTICATED_ID, null, null,
					null, null).get();
			assertNotNull(efforts);
			assertFalse(efforts.length == 0);
		});
	}

	/**
	 * @see test.api.rest.segment.ListSegmentEffortsTest#list_privateBelongsToOtherUser()
	 */
	@Override
	public void list_privateBelongsToOtherUser() throws Exception {
		// Workaround for issue 86
		if (new Issue86().isIssue()) {
			return;
		}
		// End of workaround

		super.list_privateBelongsToOtherUser();
	}
}
