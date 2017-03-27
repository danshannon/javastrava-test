package test.service.impl.segmentservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import javastrava.api.v3.model.StravaSegmentLeaderboard;
import javastrava.api.v3.model.StravaSegmentLeaderboardEntry;
import javastrava.api.v3.model.reference.StravaAgeGroup;
import javastrava.api.v3.model.reference.StravaGender;
import javastrava.api.v3.model.reference.StravaLeaderboardDateRange;
import javastrava.api.v3.model.reference.StravaWeightClass;
import javastrava.api.v3.service.Strava;
import test.service.standardtests.data.ClubDataUtils;
import test.service.standardtests.data.SegmentDataUtils;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * <p>
 * Tests for {@link Strava#getAllSegmentLeaderboard(Integer, StravaGender, StravaAgeGroup, StravaWeightClass, Boolean, Integer, StravaLeaderboardDateRange)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class GetAllSegmentLeaderboardTest {
	/**
	 * Test filtering by age group
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetAllSegmentLeaderboard_filterByAgeGroup() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = TestUtils.strava().getAllSegmentLeaderboard(SegmentDataUtils.SEGMENT_VALID_ID, null, StravaAgeGroup.AGE65_PLUS, null, null, null, null);
			assertNotNull(leaderboard);
			SegmentDataUtils.validateSegmentLeaderboard(leaderboard);
		});
	}

	/**
	 * Test filtering by club
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetAllSegmentLeaderboard_filterByClub() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = TestUtils.strava().getAllSegmentLeaderboard(SegmentDataUtils.SEGMENT_VALID_ID, null, null, null, null, ClubDataUtils.CLUB_VALID_ID, null);
			assertNotNull(leaderboard);
			SegmentDataUtils.validateSegmentLeaderboard(leaderboard);
		});
	}

	/**
	 * Test filtering by date range
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetAllSegmentLeaderboard_filterByDateRange() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = TestUtils.strava().getAllSegmentLeaderboard(SegmentDataUtils.SEGMENT_VALID_ID, null, null, null, null, null, StravaLeaderboardDateRange.TODAY);
			assertNotNull(leaderboard);
			SegmentDataUtils.validateSegmentLeaderboard(leaderboard);
		});
	}

	/**
	 * Test filtering by athletes the authenticated user is following
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetAllSegmentLeaderboard_filterByFollowing() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = TestUtils.strava().getAllSegmentLeaderboard(SegmentDataUtils.SEGMENT_VALID_ID, null, null, null, Boolean.TRUE, null, null);
			assertNotNull(leaderboard);
			SegmentDataUtils.validateSegmentLeaderboard(leaderboard);
		});
	}

	/**
	 * Test filtering by gender
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetAllSegmentLeaderboard_filterByGender() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = TestUtils.strava().getAllSegmentLeaderboard(SegmentDataUtils.SEGMENT_VALID_ID, StravaGender.FEMALE, null, null, null, null, null);
			assertNotNull(leaderboard);
			SegmentDataUtils.validateSegmentLeaderboard(leaderboard);
			for (final StravaSegmentLeaderboardEntry entry : leaderboard.getEntries()) {
				assertEquals(StravaGender.FEMALE, entry.getAthleteGender());
			}
		});
	}

	/**
	 * Test filtering by weight class
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetAllSegmentLeaderboard_filterByWeightClass() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = TestUtils.strava().getAllSegmentLeaderboard(SegmentDataUtils.SEGMENT_VALID_ID, null, null, StravaWeightClass.KG95PLUS, null, null, null);
			assertNotNull(leaderboard);
			SegmentDataUtils.validateSegmentLeaderboard(leaderboard);
		});
	}

	/**
	 * Test getting leaderboard for a segment that does not exist
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetAllSegmentLeaderboard_invalidSegment() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = TestUtils.strava().getAllSegmentLeaderboard(SegmentDataUtils.SEGMENT_INVALID_ID);
			assertNull(leaderboard);
		});
	}

	/**
	 * Test getting leaderboard for a private segment
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetAllSegmentLeaderboard_privateSegment() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = TestUtils.strava().getAllSegmentLeaderboard(SegmentDataUtils.SEGMENT_PRIVATE_ID);
			assertNotNull(leaderboard);
			SegmentDataUtils.validateSegmentLeaderboard(leaderboard);
		});
	}

	/**
	 * Test getting leaderboard for a valid segment
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings({ "static-method", "boxing" })
	@Test
	public void testGetAllSegmentLeaderboard_validSegment() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = TestUtils.strava().getAllSegmentLeaderboard(SegmentDataUtils.SEGMENT_VALID_ID);
			assertNotNull(leaderboard);
			assertNotNull(leaderboard.getEntries());
			int lastPosition = 1;
			for (final StravaSegmentLeaderboardEntry entry : leaderboard.getEntries()) {
				assertTrue(entry.getRank() >= lastPosition);
				lastPosition = entry.getRank();
			}
			SegmentDataUtils.validateSegmentLeaderboard(leaderboard);
		});
	}

}
