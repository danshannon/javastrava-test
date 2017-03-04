package test.service.impl.segmentservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import org.junit.Test;

import javastrava.api.v3.model.StravaSegmentEffort;
import javastrava.api.v3.service.Strava;
import javastrava.util.Paging;
import test.api.model.StravaSegmentEffortTest;
import test.service.standardtests.PagingListMethodTest;
import test.service.standardtests.callbacks.ListCallback;
import test.service.standardtests.callbacks.PagingListCallback;
import test.service.standardtests.data.AthleteDataUtils;
import test.service.standardtests.data.SegmentDataUtils;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * <p>
 * Specific tests for {@link Strava#listSegmentEfforts(Integer)} methods
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListSegmentEffortsTest extends PagingListMethodTest<StravaSegmentEffort, Integer> {
	@Override
	protected PagingListCallback<StravaSegmentEffort, Integer> pagingLister() {
		return ((strava, paging, id) -> strava.listSegmentEfforts(id, paging));
	}

	@Override
	protected ListCallback<StravaSegmentEffort, Integer> lister() {
		return ((strava, id) -> strava.listSegmentEfforts(id));
	}

	/**
	 * <p>
	 * Filter by all available filter fields
	 * </p>
	 * 
	 * @throws Exception
	 *             if the test fails in some unexpected way
	 */
	@Test
	public void testListSegmentEfforts_filterByAll() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final LocalDateTime startDate = LocalDateTime.of(2009, Month.JANUARY, 1, 0, 0, 0);
			final LocalDateTime endDate = LocalDateTime.of(2015, Month.JANUARY, 31, 23, 59, 59);

			final List<StravaSegmentEffort> efforts = TestUtils.strava().listSegmentEfforts(SegmentDataUtils.SEGMENT_VALID_ID,
					AthleteDataUtils.ATHLETE_AUTHENTICATED_ID, startDate, endDate, new Paging(1, 1));
			assertNotNull(efforts);
			assertFalse(efforts.isEmpty());
			assertEquals(1, efforts.size());
			for (final StravaSegmentEffort effort : efforts) {
				assertTrue(effort.getStartDateLocal().isAfter(startDate));
				assertTrue(effort.getStartDateLocal().isBefore(endDate));
				assertEquals(AthleteDataUtils.ATHLETE_AUTHENTICATED_ID, effort.getAthlete().getId());
				assertEquals(SegmentDataUtils.SEGMENT_VALID_ID, effort.getSegment().getId());
				validate(effort);
			}
		});
	}

	/**
	 * <p>
	 * Filter by date range
	 * </p>
	 * 
	 * @throws Exception
	 *             if the test fails in some unexpected way
	 */
	@Test
	public void testListSegmentEfforts_filterByDateRange() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final LocalDateTime startDate = LocalDateTime.of(2014, Month.JANUARY, 1, 0, 0, 0);
			final LocalDateTime endDate = LocalDateTime.of(2014, Month.JANUARY, 31, 23, 59, 59);

			final List<StravaSegmentEffort> efforts = TestUtils.strava().listSegmentEfforts(SegmentDataUtils.SEGMENT_VALID_ID, null,
					startDate, endDate);
			assertNotNull(efforts);
			assertFalse(0 == efforts.size());
			for (final StravaSegmentEffort effort : efforts) {
				assertNotNull(effort.getStartDateLocal());
				assertTrue(effort.getStartDateLocal().isAfter(startDate));
				assertTrue(effort.getStartDateLocal().isBefore(endDate));
				validate(effort);
			}
		});
	}

	/**
	 * <p>
	 * Filter by end date
	 * </p>
	 * 
	 * @throws Exception
	 *             if the test fails in some unexpected way
	 */
	@Test
	public void testListSegmentEfforts_filterByEndDate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final LocalDateTime endDate = LocalDateTime.of(2013, Month.DECEMBER, 1, 23, 59, 59);

			final List<StravaSegmentEffort> efforts = TestUtils.strava().listSegmentEfforts(SegmentDataUtils.SEGMENT_VALID_ID, null, null,
					endDate);
			assertNotNull(efforts);
			assertFalse(0 == efforts.size());
			for (final StravaSegmentEffort effort : efforts) {
				assertNotNull(effort.getStartDateLocal());
				assertTrue(effort.getStartDateLocal().isBefore(endDate));
				validate(effort);
			}
		});
	}

	/**
	 * <p>
	 * Filter by invalid athlete
	 * </p>
	 * 
	 * @throws Exception
	 *             if the test fails in some unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testListSegmentEfforts_filterByInvalidAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegmentEffort> efforts = TestUtils.strava().listSegmentEfforts(SegmentDataUtils.SEGMENT_VALID_ID,
					AthleteDataUtils.ATHLETE_INVALID_ID, null, null);
			assertNull(efforts);
		});
	}

	/**
	 * <p>
	 * Filter by start date
	 * </p>
	 * 
	 * @throws Exception
	 *             if the test fails in some unexpected way
	 */
	@Test
	public void testListSegmentEfforts_filterByStartDate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final LocalDateTime startDate = LocalDateTime.of(2014, Month.JANUARY, 1, 0, 0);

			final List<StravaSegmentEffort> efforts = TestUtils.strava().listSegmentEfforts(SegmentDataUtils.SEGMENT_VALID_ID, null,
					startDate, null);
			assertNotNull(efforts);
			assertFalse(0 == efforts.size());
			for (final StravaSegmentEffort effort : efforts) {
				assertNotNull(effort.getStartDateLocal());
				assertTrue(effort.getStartDateLocal().isAfter(startDate));
				validate(effort);
			}
		});
	}

	/**
	 * <p>
	 * Filter by valid athlete
	 * </p>
	 * 
	 * @throws Exception
	 *             if the test fails in some unexpected way
	 */
	@Test
	public void testListSegmentEfforts_filterByValidAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegmentEffort> efforts = TestUtils.strava().listSegmentEfforts(SegmentDataUtils.SEGMENT_VALID_ID,
					AthleteDataUtils.ATHLETE_AUTHENTICATED_ID, null, null);
			assertNotNull(efforts);
			assertFalse(0 == efforts.size());
			for (final StravaSegmentEffort effort : efforts) {
				validate(effort);
				assertEquals(AthleteDataUtils.ATHLETE_AUTHENTICATED_ID, effort.getAthlete().getId());
			}
		});
	}

	/**
	 * <p>
	 * Test behaviour for hazardous segment
	 * </p>
	 * 
	 * @throws Exception
	 *             if the test fails in some unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testListSegmentEfforts_hazardousSegment() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegmentEffort> efforts = TestUtils.strava().listSegmentEfforts(SegmentDataUtils.SEGMENT_HAZARDOUS_ID);
			assertNotNull(efforts);
			assertEquals(0, efforts.size());
		});
	}

	@Override
	protected void validate(final StravaSegmentEffort effort) {
		StravaSegmentEffortTest.validateSegmentEffort(effort);
	}

	@Override
	protected Integer idPrivate() {
		return SegmentDataUtils.SEGMENT_PRIVATE_ID;
	}

	@Override
	protected Integer idPrivateBelongsToOtherUser() {
		return SegmentDataUtils.SEGMENT_OTHER_USER_PRIVATE_ID;
	}

	@Override
	protected Integer idValidWithEntries() {
		return SegmentDataUtils.SEGMENT_VALID_ID;
	}

	@Override
	protected Integer idValidWithoutEntries() {
		return null;
	}

	@Override
	protected Integer idInvalid() {
		return SegmentDataUtils.SEGMENT_INVALID_ID;
	}

}
