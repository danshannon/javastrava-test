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
import test.api.model.StravaSegmentLeaderboardTest;
import test.service.standardtests.data.ClubDataUtils;
import test.service.standardtests.data.SegmentDataUtils;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class GetAllSegmentLeaderboardTest {
	@Test
	public void testGetAllSegmentLeaderboard_filterByAgeGroup() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = TestUtils.strava().getAllSegmentLeaderboard(SegmentDataUtils.SEGMENT_VALID_ID,
					null, StravaAgeGroup.AGE65_PLUS, null, null, null, null);
			assertNotNull(leaderboard);
			StravaSegmentLeaderboardTest.validate(leaderboard);
		});
	}

	@Test
	public void testGetAllSegmentLeaderboard_filterByClub() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = TestUtils.strava().getAllSegmentLeaderboard(SegmentDataUtils.SEGMENT_VALID_ID,
					null, null, null, null, ClubDataUtils.CLUB_VALID_ID, null);
			assertNotNull(leaderboard);
			StravaSegmentLeaderboardTest.validate(leaderboard);
		});
	}

	@Test
	public void testGetAllSegmentLeaderboard_filterByDateRange() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = TestUtils.strava().getAllSegmentLeaderboard(SegmentDataUtils.SEGMENT_VALID_ID,
					null, null, null, null, null, StravaLeaderboardDateRange.TODAY);
			assertNotNull(leaderboard);
			StravaSegmentLeaderboardTest.validate(leaderboard);
		});
	}

	@Test
	public void testGetAllSegmentLeaderboard_filterByFollowing() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = TestUtils.strava().getAllSegmentLeaderboard(SegmentDataUtils.SEGMENT_VALID_ID,
					null, null, null, Boolean.TRUE, null, null);
			assertNotNull(leaderboard);
			StravaSegmentLeaderboardTest.validate(leaderboard);
		});
	}

	@Test
	public void testGetAllSegmentLeaderboard_filterByGender() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = TestUtils.strava().getAllSegmentLeaderboard(SegmentDataUtils.SEGMENT_VALID_ID,
					StravaGender.FEMALE, null, null, null, null, null);
			assertNotNull(leaderboard);
			StravaSegmentLeaderboardTest.validate(leaderboard);
			for (final StravaSegmentLeaderboardEntry entry : leaderboard.getEntries()) {
				assertEquals(StravaGender.FEMALE, entry.getAthleteGender());
			}
		});
	}

	@Test
	public void testGetAllSegmentLeaderboard_filterByWeightClass() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = TestUtils.strava().getAllSegmentLeaderboard(SegmentDataUtils.SEGMENT_VALID_ID,
					null, null, StravaWeightClass.KG95PLUS, null, null, null);
			assertNotNull(leaderboard);
			StravaSegmentLeaderboardTest.validate(leaderboard);
		});
	}

	@Test
	public void testGetAllSegmentLeaderboard_invalidSegment() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = TestUtils.strava().getAllSegmentLeaderboard(SegmentDataUtils.SEGMENT_INVALID_ID);
			assertNull(leaderboard);
		});
	}

	@Test
	public void testGetAllSegmentLeaderboard_privateSegment() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = TestUtils.strava().getAllSegmentLeaderboard(SegmentDataUtils.SEGMENT_PRIVATE_ID);
			assertNotNull(leaderboard);
			StravaSegmentLeaderboardTest.validate(leaderboard);
		});
	}

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
			StravaSegmentLeaderboardTest.validate(leaderboard);
		});
	}

}
