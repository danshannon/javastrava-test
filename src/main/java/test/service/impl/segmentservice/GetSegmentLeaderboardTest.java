package test.service.impl.segmentservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
 * Specific tests and configuration for
 * {@link Strava#getSegmentLeaderboard(Integer, StravaGender, StravaAgeGroup, StravaWeightClass, Boolean, Integer, StravaLeaderboardDateRange, javastrava.util.Paging, Integer)}
 * </p>
 * 
 * @author Dan Shannon
 *
 */
public class GetSegmentLeaderboardTest {
	/**
	 * Filter by age group
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected manner
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetSegmentLeaderboard_filterByAgeGroup() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = TestUtils.strava().getSegmentLeaderboard(SegmentDataUtils.SEGMENT_VALID_ID, null, StravaAgeGroup.AGE35_44, null, null, null, null, null, null);
			assertNotNull(leaderboard);
			assertFalse(leaderboard.getEntries().isEmpty());
			SegmentDataUtils.validateSegmentLeaderboard(leaderboard);
		});
	}

	/**
	 * Filter by ALL options combined
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected manner
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetSegmentLeaderboard_filterByAllOptions() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = TestUtils.strava().getSegmentLeaderboard(SegmentDataUtils.SEGMENT_VALID_ID, StravaGender.MALE, StravaAgeGroup.AGE45_54,
					StravaWeightClass.KG85_94, Boolean.FALSE, ClubDataUtils.CLUB_VALID_ID, StravaLeaderboardDateRange.THIS_YEAR, null, null);
			assertNotNull(leaderboard);
			assertFalse(leaderboard.getEntries().isEmpty());
			SegmentDataUtils.validateSegmentLeaderboard(leaderboard);
		});
	}

	/**
	 * Filter by valid club
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected manner
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetSegmentLeaderboard_filterByClub() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = TestUtils.strava().getSegmentLeaderboard(SegmentDataUtils.SEGMENT_VALID_ID, null, null, null, null, ClubDataUtils.CLUB_VALID_ID, null, null,
					null);
			assertNotNull(leaderboard);
			assertFalse(leaderboard.getEntries().isEmpty());
			SegmentDataUtils.validateSegmentLeaderboard(leaderboard);
		});
	}

	/**
	 * Filter by athletes the authenticated user is following
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected manner
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetSegmentLeaderboard_filterByFollowing() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = TestUtils.strava().getSegmentLeaderboard(SegmentDataUtils.SEGMENT_VALID_ID, null, null, null, Boolean.TRUE, null, null, null, null);
			assertNotNull(leaderboard);
			assertFalse(leaderboard.getEntries().isEmpty());
			SegmentDataUtils.validateSegmentLeaderboard(leaderboard);
		});
	}

	/**
	 * Filter by gender
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected manner
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetSegmentLeaderboard_filterByGender() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = TestUtils.strava().getSegmentLeaderboard(SegmentDataUtils.SEGMENT_VALID_ID, StravaGender.FEMALE, null, null, null, null, null, null, null);
			assertNotNull(leaderboard);
			assertFalse(leaderboard.getEntries().isEmpty());
			for (final StravaSegmentLeaderboardEntry entry : leaderboard.getEntries()) {
				assertEquals(StravaGender.FEMALE, entry.getAthleteGender());
				validate(entry);
			}
		});
	}

	/**
	 * Filter by invalid club
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected manner
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetSegmentLeaderboard_filterByInvalidClub() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = TestUtils.strava().getSegmentLeaderboard(SegmentDataUtils.SEGMENT_VALID_ID, null, null, null, null, ClubDataUtils.CLUB_INVALID_ID, null, null,
					null);
			assertNull(leaderboard);
		});
	}

	/**
	 * Filter by leaderboard date range
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected manner
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetSegmentLeaderboard_filterByLeaderboardDateRange() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = TestUtils.strava().getSegmentLeaderboard(SegmentDataUtils.SEGMENT_VALID_ID, null, null, null, null, null, StravaLeaderboardDateRange.THIS_YEAR,
					null, null);
			assertNotNull(leaderboard);
			assertFalse(leaderboard.getEntries().isEmpty());
			SegmentDataUtils.validateSegmentLeaderboard(leaderboard);
		});
	}

	/**
	 * Filter by weight class
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected manner
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetSegmentLeaderboard_filterByWeightClass() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = TestUtils.strava().getSegmentLeaderboard(SegmentDataUtils.SEGMENT_VALID_ID, null, null, StravaWeightClass.KG75_84, null, null, null, null,
					null);
			assertNotNull(leaderboard);
			assertFalse(leaderboard.getEntries().isEmpty());
			SegmentDataUtils.validateSegmentLeaderboard(leaderboard);
		});
	}

	/**
	 * Test behaviour when asking for a leaderboard for a segment flagged as hazardous
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected manner
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetSegmentLeaderboard_hazardousSegment() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = TestUtils.strava().getSegmentLeaderboard(SegmentDataUtils.SEGMENT_HAZARDOUS_ID);
			assertNull(leaderboard);
		});
	}

	/**
	 * Invalid segment
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected manner
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetSegmentLeaderboard_invalidSegment() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = TestUtils.strava().getSegmentLeaderboard(SegmentDataUtils.SEGMENT_INVALID_ID);
			assertNull(leaderboard);
		});
	}

	/**
	 * Test behaviour when getting a leaderboard for a segment that belongs to the authenticated user and is flagged as private
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected manner
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetSegmentLeaderboard_privateSegment() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = TestUtils.strava().getSegmentLeaderboard(SegmentDataUtils.SEGMENT_PRIVATE_ID);
			// Should return an empty leaderboard
			assertNotNull(leaderboard);
			assertNotNull(leaderboard.getEntries());
			assertTrue(leaderboard.getEntries().isEmpty());
		});
	}

	/**
	 * Test behaviour when getting a leaderboard for a segment that belongs to someone other than the authenticated user and is flagged as private
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected manner
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetSegmentLeaderboard_privateSegmentOtherUser() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = TestUtils.strava().getSegmentLeaderboard(SegmentDataUtils.SEGMENT_OTHER_USER_PRIVATE_ID);
			assertNotNull(leaderboard);
			assertTrue(leaderboard.getEntries().isEmpty());
			assertTrue(leaderboard.getAthleteEntries().isEmpty());
		});
	}

	/**
	 * Test behaviour when getting a leaderboard for a segment that belongs to the authenticated user and is flagged as private, using a token that does not have VIEW_PRIVATE scope
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected manner
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetSegmentLeaderboard_privateWithViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = TestUtils.stravaWithViewPrivate().getSegmentLeaderboard(SegmentDataUtils.SEGMENT_PRIVATE_ID);
			assertNotNull(leaderboard);
			assertFalse(leaderboard.getEntries().isEmpty());
			SegmentDataUtils.validateSegmentLeaderboard(leaderboard);
		});
	}

	/**
	 * Valid segment, no filtering
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected manner
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetSegmentLeaderboard_validSegment() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = TestUtils.strava().getSegmentLeaderboard(SegmentDataUtils.SEGMENT_VALID_ID);
			SegmentDataUtils.validateSegmentLeaderboard(leaderboard);
		});
	}

	/**
	 * @param entry
	 *            Entry to be validated
	 */
	protected static void validate(final StravaSegmentLeaderboardEntry entry) {
		SegmentDataUtils.validateSegmentLeaderboardEntry(entry);

	}

}
