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
import test.api.model.StravaSegmentLeaderboardEntryTest;
import test.api.model.StravaSegmentLeaderboardTest;
import test.service.standardtests.data.ClubDataUtils;
import test.service.standardtests.data.SegmentDataUtils;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class GetSegmentLeaderboardTest {
	// 4. Filter by age group
	@Test
	public void testGetSegmentLeaderboard_filterByAgeGroup() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = TestUtils.strava().getSegmentLeaderboard(SegmentDataUtils.SEGMENT_VALID_ID, null, StravaAgeGroup.AGE35_44, null, null, null, null, null, null);
			assertNotNull(leaderboard);
			assertFalse(leaderboard.getEntries().isEmpty());
			StravaSegmentLeaderboardTest.validate(leaderboard);
		});
	}

	// 10. Filter by ALL options combined
	@Test
	public void testGetSegmentLeaderboard_filterByAllOptions() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = TestUtils.strava().getSegmentLeaderboard(SegmentDataUtils.SEGMENT_VALID_ID, StravaGender.MALE, StravaAgeGroup.AGE45_54,
					StravaWeightClass.KG85_94, Boolean.FALSE, ClubDataUtils.CLUB_VALID_ID, StravaLeaderboardDateRange.THIS_YEAR, null, null);
			assertNotNull(leaderboard);
			assertFalse(leaderboard.getEntries().isEmpty());
			StravaSegmentLeaderboardTest.validate(leaderboard);
		});
	}

	// 7. Filter by valid club
	@Test
	public void testGetSegmentLeaderboard_filterByClub() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = TestUtils.strava().getSegmentLeaderboard(SegmentDataUtils.SEGMENT_VALID_ID, null, null, null, null, ClubDataUtils.CLUB_VALID_ID, null, null,
					null);
			assertNotNull(leaderboard);
			assertFalse(leaderboard.getEntries().isEmpty());
			StravaSegmentLeaderboardTest.validate(leaderboard);
		});
	}

	// 6. Filter by athletes the authenticated user is following
	@Test
	public void testGetSegmentLeaderboard_filterByFollowing() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = TestUtils.strava().getSegmentLeaderboard(SegmentDataUtils.SEGMENT_VALID_ID, null, null, null, Boolean.TRUE, null, null, null, null);
			assertNotNull(leaderboard);
			assertFalse(leaderboard.getEntries().isEmpty());
			StravaSegmentLeaderboardTest.validate(leaderboard);
		});
	}

	// 3. Filter by gender
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

	// 8. Filter by invalid club
	@Test
	public void testGetSegmentLeaderboard_filterByInvalidClub() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = TestUtils.strava().getSegmentLeaderboard(SegmentDataUtils.SEGMENT_VALID_ID, null, null, null, null, ClubDataUtils.CLUB_INVALID_ID, null, null,
					null);
			assertNull(leaderboard);
		});
	}

	// 9. Filter by leaderboard date range
	@Test
	public void testGetSegmentLeaderboard_filterByLeaderboardDateRange() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = TestUtils.strava().getSegmentLeaderboard(SegmentDataUtils.SEGMENT_VALID_ID, null, null, null, null, null, StravaLeaderboardDateRange.THIS_YEAR,
					null, null);
			assertNotNull(leaderboard);
			assertFalse(leaderboard.getEntries().isEmpty());
			StravaSegmentLeaderboardTest.validate(leaderboard);
		});
	}

	// 5. Filter by weight class
	@Test
	public void testGetSegmentLeaderboard_filterByWeightClass() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = TestUtils.strava().getSegmentLeaderboard(SegmentDataUtils.SEGMENT_VALID_ID, null, null, StravaWeightClass.KG75_84, null, null, null, null,
					null);
			assertNotNull(leaderboard);
			assertFalse(leaderboard.getEntries().isEmpty());
			StravaSegmentLeaderboardTest.validate(leaderboard);
		});
	}

	@Test
	public void testGetSegmentLeaderboard_hazardousSegment() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = TestUtils.strava().getSegmentLeaderboard(SegmentDataUtils.SEGMENT_HAZARDOUS_ID);
			assertNull(leaderboard);
		});
	}

	// 2. Invalid segment
	@Test
	public void testGetSegmentLeaderboard_invalidSegment() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = TestUtils.strava().getSegmentLeaderboard(SegmentDataUtils.SEGMENT_INVALID_ID);
			assertNull(leaderboard);
		});
	}

	@Test
	public void testGetSegmentLeaderboard_privateSegment() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = TestUtils.strava().getSegmentLeaderboard(SegmentDataUtils.SEGMENT_PRIVATE_ID);
			// Should return an empty leaderboard
			assertNotNull(leaderboard);
			assertNotNull(leaderboard.getEntries());
			// Workaround for issue javastrava-api #71 - see https://github.com/danshannon/javastravav3api/issues/71
			// assertTrue(leaderboard.getEntries().isEmpty());
			// End of workaround
		});
	}

	@Test
	public void testGetSegmentLeaderboard_privateSegmentOtherUser() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = TestUtils.strava().getSegmentLeaderboard(SegmentDataUtils.SEGMENT_OTHER_USER_PRIVATE_ID);
			assertNotNull(leaderboard);
			assertTrue(leaderboard.getEntries().isEmpty());
			assertTrue(leaderboard.getAthleteEntries().isEmpty());
		});
	}

	@Test
	public void testGetSegmentLeaderboard_privateWithViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = TestUtils.stravaWithViewPrivate().getSegmentLeaderboard(SegmentDataUtils.SEGMENT_PRIVATE_ID);
			assertNotNull(leaderboard);
			assertFalse(leaderboard.getEntries().isEmpty());
			StravaSegmentLeaderboardTest.validate(leaderboard);
		});
	}

	// Test cases
	// 1. Valid segment, no filtering
	@Test
	public void testGetSegmentLeaderboard_validSegment() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = TestUtils.strava().getSegmentLeaderboard(SegmentDataUtils.SEGMENT_VALID_ID);
			StravaSegmentLeaderboardTest.validate(leaderboard);
		});
	}

	@SuppressWarnings("static-method")
	protected void validate(final StravaSegmentLeaderboardEntry entry) {
		StravaSegmentLeaderboardEntryTest.validate(entry);

	}

}
