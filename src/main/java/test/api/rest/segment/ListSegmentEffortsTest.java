package test.api.rest.segment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import org.junit.Test;

import javastrava.api.v3.model.StravaSegment;
import javastrava.api.v3.model.StravaSegmentEffort;
import javastrava.api.v3.service.exception.NotFoundException;
import test.api.model.StravaSegmentEffortTest;
import test.api.rest.APIPagingListTest;
import test.api.rest.callback.APIListCallback;
import test.api.rest.util.ArrayCallback;
import test.issues.strava.Issue33;
import test.issues.strava.Issue86;
import test.service.standardtests.data.AthleteDataUtils;
import test.service.standardtests.data.SegmentDataUtils;
import test.service.standardtests.data.SegmentEffortDataUtils;
import test.utils.RateLimitedTestRunner;

public class ListSegmentEffortsTest extends APIPagingListTest<StravaSegmentEffort, Integer> {
	/**
	 * @see test.api.rest.APIPagingListTest#pagingCallback()
	 */
	@Override
	protected ArrayCallback<StravaSegmentEffort> pagingCallback() {
		return paging -> api().listSegmentEfforts(validId(), null, null, null, paging.getPage(), paging.getPageSize());
	}

	/**
	 * @see test.api.rest.APIListTest#listCallback()
	 */
	@Override
	protected APIListCallback<StravaSegmentEffort, Integer> listCallback() {
		return (api, id) -> api.listSegmentEfforts(id, null, null, null, null, null);
	}

	/**
	 * @see test.api.rest.APIListTest#invalidId()
	 */
	@Override
	protected Integer invalidId() {
		return SegmentDataUtils.SEGMENT_INVALID_ID;
	}

	/**
	 * @see test.api.rest.APIListTest#privateId()
	 */
	@Override
	protected Integer privateId() {
		return SegmentDataUtils.SEGMENT_PRIVATE_ID;
	}

	/**
	 * @see test.api.rest.APIListTest#privateIdBelongsToOtherUser()
	 */
	@Override
	protected Integer privateIdBelongsToOtherUser() {
		return SegmentDataUtils.SEGMENT_OTHER_USER_PRIVATE_ID;
	}

	@Test
	public void testListSegmentEfforts_filterByAll() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final LocalDateTime startDate = LocalDateTime.of(2009, Month.JANUARY, 1, 0, 0, 0);
			final LocalDateTime endDate = LocalDateTime.of(2015, Month.JANUARY, 31, 23, 59, 59);

			final StravaSegmentEffort[] efforts = api().listSegmentEfforts(SegmentDataUtils.SEGMENT_VALID_ID,
					AthleteDataUtils.ATHLETE_AUTHENTICATED_ID, startDate.toString(), endDate.toString(), 1, 1);
			assertNotNull(efforts);
			assertFalse(efforts.length == 0);
			assertEquals(1, efforts.length);
			for (final StravaSegmentEffort effort : efforts) {
				assertTrue(effort.getStartDateLocal().isAfter(startDate));
				assertTrue(effort.getStartDateLocal().isBefore(endDate));
				assertEquals(AthleteDataUtils.ATHLETE_AUTHENTICATED_ID, effort.getAthlete().getId());
				assertEquals(SegmentDataUtils.SEGMENT_VALID_ID, effort.getSegment().getId());
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

			final StravaSegmentEffort[] efforts = api().listSegmentEfforts(SegmentDataUtils.SEGMENT_VALID_ID, null, startDate.toString(),
					endDate.toString(), null, null);
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
	@Test
	public void testListSegmentEfforts_filterByInvalidAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().listSegmentEfforts(SegmentDataUtils.SEGMENT_VALID_ID, AthleteDataUtils.ATHLETE_INVALID_ID, null, null, null, null);
			} catch (final NotFoundException e) {
				// expected
				return;
			}
			fail("Returned segment efforts for a non-existent athlete");
		});
	}

	// 3. Filter by valid athlete, valid segment
	@Test
	public void testListSegmentEfforts_filterByValidAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentEffort[] efforts = api().listSegmentEfforts(SegmentDataUtils.SEGMENT_VALID_ID,
					AthleteDataUtils.ATHLETE_AUTHENTICATED_ID, null, null, null, null);
			assertNotNull(efforts);
			assertFalse(0 == efforts.length);
			for (final StravaSegmentEffort effort : efforts) {
				validate(effort);
				assertEquals(AthleteDataUtils.ATHLETE_AUTHENTICATED_ID, effort.getAthlete().getId());
			}
		});
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

			final StravaSegmentEffort[] efforts = api().listSegmentEfforts(SegmentDataUtils.SEGMENT_HAZARDOUS_ID, null, null, null, null,
					null);
			assertNotNull(efforts);
			assertEquals(0, efforts.length);
		});
	}

	@Test
	public void testListSegmentEfforts_privateActivityWithoutViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegment segment = apiWithViewPrivate().getSegmentEffort(SegmentEffortDataUtils.SEGMENT_EFFORT_PRIVATE_ACTIVITY_ID)
					.getSegment();
			final StravaSegmentEffort[] efforts = api().listSegmentEfforts(segment.getId(), AthleteDataUtils.ATHLETE_AUTHENTICATED_ID,
					null, null, null, null);
			assertNotNull(efforts);
			assertEquals(0, efforts.length);
		});
	}

	@Test
	public void testListSegmentEfforts_privateActivityWithViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegment segment = apiWithViewPrivate().getSegmentEffort(SegmentEffortDataUtils.SEGMENT_EFFORT_PRIVATE_ACTIVITY_ID)
					.getSegment();
			final StravaSegmentEffort[] efforts = apiWithViewPrivate().listSegmentEfforts(segment.getId(),
					AthleteDataUtils.ATHLETE_AUTHENTICATED_ID, null, null, null, null);
			assertNotNull(efforts);
			assertFalse(efforts.length == 0);
		});
	}

	@Override
	public void list_privateWithoutViewPrivate() throws Exception {
		// TODO This is a workaround for issue javastravav3api#86
		if (new Issue86().isIssue()) {
			return;
		}
		// End of workaround

		super.list_privateWithoutViewPrivate();
	}

	@Override
	public void list_privateBelongsToOtherUser() throws Exception {
		// TODO This is a workaround for issue javastravav3api#86
		if (new Issue86().isIssue()) {
			return;
		}
		// End of workaround

		super.list_privateBelongsToOtherUser();
	}

	@Override
	protected void validate(final StravaSegmentEffort effort) {
		StravaSegmentEffortTest.validateSegmentEffort(effort);

	}

	/**
	 * @see test.api.rest.APIListTest#validateArray(java.lang.Object[])
	 */
	@Override
	protected void validateArray(final StravaSegmentEffort[] list) {
		StravaSegmentEffortTest.validateList(Arrays.asList(list));

	}

	/**
	 * @see test.api.rest.APIListTest#validId()
	 */
	@Override
	protected Integer validId() {
		return SegmentDataUtils.SEGMENT_VALID_ID;
	}

	/**
	 * @see test.api.rest.APIListTest#validIdBelongsToOtherUser()
	 */
	@Override
	protected Integer validIdBelongsToOtherUser() {
		return null;
	}

	/**
	 * @see test.api.rest.APIListTest#validIdNoChildren()
	 */
	@Override
	protected Integer validIdNoChildren() {
		return null;
	}

}
