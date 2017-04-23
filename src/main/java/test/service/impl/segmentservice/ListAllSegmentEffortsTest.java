package test.service.impl.segmentservice;

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
import javastrava.api.v3.model.reference.StravaResourceState;
import test.service.standardtests.ListMethodTest;
import test.service.standardtests.callbacks.ListCallback;
import test.service.standardtests.data.AthleteDataUtils;
import test.service.standardtests.data.SegmentDataUtils;
import test.service.standardtests.data.SegmentEffortDataUtils;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * <p>
 * Specific tests for listAllSegmentEfforts methods
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListAllSegmentEffortsTest extends ListMethodTest<StravaSegmentEffort, Integer> {
	@Override
	protected Class<StravaSegmentEffort> classUnderTest() {
		return StravaSegmentEffort.class;
	}

	@Override
	protected Integer idInvalid() {
		return SegmentDataUtils.SEGMENT_INVALID_ID;
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

	/**
	 * <p>
	 * Test filter efforts after a given date
	 * </p>
	 *
	 * @throws Exception
	 *             if test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void listAllSegmentEfforts_filterByAfterDate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final LocalDateTime afterDate = LocalDateTime.of(2015, Month.JANUARY, 1, 0, 0);
			final List<StravaSegmentEffort> efforts = TestUtils.strava().listAllSegmentEfforts(SegmentDataUtils.SEGMENT_VALID_ID, null, afterDate, null);
			assertNotNull(efforts);
			for (final StravaSegmentEffort effort : efforts) {
				assertTrue(effort.getStartDateLocal().isAfter(afterDate));
				SegmentEffortDataUtils.validateSegmentEffort(effort);
			}
		});
	}

	/**
	 * <p>
	 * Test filter efforts before a given date
	 * </p>
	 *
	 * @throws Exception
	 *             if test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void listAllSegmentEfforts_filterByBeforeDate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final LocalDateTime beforeDate = LocalDateTime.of(2013, Month.JANUARY, 1, 0, 0);
			final List<StravaSegmentEffort> efforts = TestUtils.strava().listAllSegmentEfforts(SegmentDataUtils.SEGMENT_VALID_ID, null, null, beforeDate);
			assertNotNull(efforts);
			for (final StravaSegmentEffort effort : efforts) {
				assertTrue(effort.getStartDateLocal().isBefore(beforeDate));
				SegmentEffortDataUtils.validateSegmentEffort(effort);
			}
		});
	}

	/**
	 * <p>
	 * Test filter efforts in a given date range
	 * </p>
	 *
	 * @throws Exception
	 *             if test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void listAllSegmentEfforts_filterByDateRange() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final LocalDateTime afterDate = LocalDateTime.of(2013, Month.JANUARY, 1, 0, 0);
			final LocalDateTime beforeDate = LocalDateTime.of(2013, Month.DECEMBER, 1, 0, 0);
			final List<StravaSegmentEffort> efforts = TestUtils.strava().listAllSegmentEfforts(SegmentDataUtils.SEGMENT_VALID_ID, null, afterDate, beforeDate);
			assertNotNull(efforts);
			for (final StravaSegmentEffort effort : efforts) {
				assertTrue(effort.getStartDateLocal().isAfter(afterDate));
				assertTrue(effort.getStartDateLocal().isBefore(beforeDate));
				SegmentEffortDataUtils.validateSegmentEffort(effort);
			}
		});
	}

	/**
	 * <p>
	 * Test filter efforts using all options
	 * </p>
	 *
	 * @throws Exception
	 *             if test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void listAllSegmentEfforts_filterByEverything() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final LocalDateTime afterDate = LocalDateTime.of(2013, Month.JANUARY, 1, 0, 0);
			final LocalDateTime beforeDate = LocalDateTime.of(2013, Month.DECEMBER, 1, 0, 0);
			final List<StravaSegmentEffort> efforts = TestUtils.strava().listAllSegmentEfforts(SegmentDataUtils.SEGMENT_VALID_ID, AthleteDataUtils.ATHLETE_AUTHENTICATED_ID, afterDate, beforeDate);
			assertNotNull(efforts);
			for (final StravaSegmentEffort effort : efforts) {
				assertTrue(effort.getStartDateLocal().isBefore(beforeDate));
				assertTrue(effort.getStartDateLocal().isAfter(afterDate));
				assertTrue(effort.getStartDateLocal().isBefore(beforeDate));
				SegmentEffortDataUtils.validateSegmentEffort(effort);
			}
		});
	}

	/**
	 * <p>
	 * Test filter efforts for an invalid athlete
	 * </p>
	 *
	 * @throws Exception
	 *             if test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void listAllSegmentEfforts_filterByInvalidAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegmentEffort> efforts = TestUtils.strava().listAllSegmentEfforts(SegmentDataUtils.SEGMENT_VALID_ID, AthleteDataUtils.ATHLETE_INVALID_ID, null, null);
			assertNull(efforts);
		});
	}

	/**
	 * <p>
	 * Test filter efforts for a valid athlete
	 * </p>
	 *
	 * @throws Exception
	 *             if test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void listAllSegmentEfforts_filterByValidAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegmentEffort> efforts = TestUtils.strava().listAllSegmentEfforts(SegmentDataUtils.SEGMENT_VALID_ID, AthleteDataUtils.ATHLETE_VALID_ID, null, null);
			assertNotNull(efforts);
			for (final StravaSegmentEffort effort : efforts) {
				assertEquals(AthleteDataUtils.ATHLETE_AUTHENTICATED_ID, effort.getAthlete().getId());
				SegmentEffortDataUtils.validateSegmentEffort(effort);
			}
		});
	}

	/**
	 * <p>
	 * Test list efforts for a segment flagged as hazardous
	 * </p>
	 *
	 * @throws Exception
	 *             if test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void listAllSegmentEfforts_hazardousSegment() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegmentEffort> efforts = TestUtils.strava().listAllSegmentEfforts(SegmentDataUtils.SEGMENT_HAZARDOUS_ID);
			assertNotNull(efforts);
			assertEquals(0, efforts.size());
		});
	}

	@Override
	protected ListCallback<StravaSegmentEffort, Integer> lister() {
		return ((strava, id) -> strava.listAllSegmentEfforts(id));
	}

	/**
	 * <p>
	 * Test list efforts for a private activity, without view_private in token scope
	 * </p>
	 *
	 * @throws Exception
	 *             if test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testListAllSegmentEfforts_privateActivityWithoutViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentEffort segmentEffort = TestUtils.stravaWithViewPrivate().getSegmentEffort(SegmentEffortDataUtils.SEGMENT_EFFORT_PRIVATE_ACTIVITY_ID);
			final StravaSegment segment = segmentEffort.getSegment();
			final StravaActivity activity = segmentEffort.getActivity();
			final List<StravaSegmentEffort> efforts = TestUtils.strava().listAllSegmentEfforts(segment.getId());
			for (final StravaSegmentEffort effort : efforts) {
				if (effort.getActivity().getId().equals(activity.getId())) {
					fail("Returned segment effort for a private activity"); //$NON-NLS-1$
				}
			}
		});
	}

	/**
	 * <p>
	 * Test list efforts for a private activity, with view_private in token scope
	 * </p>
	 *
	 * @throws Exception
	 *             if test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testListAllSegmentEfforts_privateActivityWithViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentEffort segmentEffort = TestUtils.stravaWithViewPrivate().getSegmentEffort(SegmentEffortDataUtils.SEGMENT_EFFORT_PRIVATE_ACTIVITY_ID);
			final StravaSegment segment = segmentEffort.getSegment();
			final StravaActivity activity = TestUtils.strava().getActivity(segmentEffort.getActivity().getId());
			assertEquals("Activity " + SegmentEffortDataUtils.SEGMENT_EFFORT_PRIVATE_ACTIVITY_ID + " is not flagged as private!", StravaResourceState.PRIVATE, activity.getResourceState()); //$NON-NLS-1$ //$NON-NLS-2$
			final List<StravaSegmentEffort> efforts = TestUtils.stravaWithViewPrivate().listAllSegmentEfforts(segment.getId());
			assertNotNull(efforts);
		});
	}

	@Override
	protected void validate(StravaSegmentEffort object) {
		SegmentEffortDataUtils.validateSegmentEffort(object);
	}

}
