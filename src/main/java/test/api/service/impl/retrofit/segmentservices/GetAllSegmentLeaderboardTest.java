package test.api.service.impl.retrofit.segmentservices;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import javastrava.api.v3.model.StravaSegmentLeaderboard;
import javastrava.api.v3.model.StravaSegmentLeaderboardEntry;
import javastrava.api.v3.model.reference.StravaAgeGroup;
import javastrava.api.v3.model.reference.StravaGender;
import javastrava.api.v3.model.reference.StravaLeaderboardDateRange;
import javastrava.api.v3.model.reference.StravaWeightClass;
import javastrava.api.v3.service.SegmentServices;
import javastrava.api.v3.service.impl.retrofit.SegmentServicesImpl;

import org.junit.Test;

import test.api.model.StravaSegmentLeaderboardTest;
import test.utils.TestUtils;

public class GetAllSegmentLeaderboardTest {
	@Test
	public void testGetAllSegmentLeaderboard_validSegment() {
		final StravaSegmentLeaderboard leaderboard = service().getAllSegmentLeaderboard(TestUtils.SEGMENT_VALID_ID);
		assertNotNull(leaderboard);
		assertNotNull(leaderboard.getEntries());
		int lastPosition = 1;
		for (final StravaSegmentLeaderboardEntry entry : leaderboard.getEntries()) {
			assertTrue(entry.getRank() >= lastPosition);
			lastPosition = entry.getRank();
		}
		StravaSegmentLeaderboardTest.validate(leaderboard);
	}

	@Test
	public void testGetAllSegmentLeaderboard_invalidSegment() {
		final StravaSegmentLeaderboard leaderboard = service().getAllSegmentLeaderboard(TestUtils.SEGMENT_INVALID_ID);
		assertNull(leaderboard);
	}

	@Test
	public void testGetAllSegmentLeaderboard_privateSegment() {
		final StravaSegmentLeaderboard leaderboard = service().getAllSegmentLeaderboard(TestUtils.SEGMENT_PRIVATE_ID);
		assertNotNull(leaderboard);
		StravaSegmentLeaderboardTest.validate(leaderboard);

	}

	@Test
	public void testGetAllSegmentLeaderboard_filterByGender() {
		final StravaSegmentLeaderboard leaderboard = service().getAllSegmentLeaderboard(TestUtils.SEGMENT_VALID_ID, StravaGender.FEMALE, null, null, null, null, null);
		assertNotNull(leaderboard);
		StravaSegmentLeaderboardTest.validate(leaderboard);
		for (final StravaSegmentLeaderboardEntry entry : leaderboard.getEntries()) {
			assertEquals(StravaGender.FEMALE,entry.getAthleteGender());
		}
	}

	@Test
	public void testGetAllSegmentLeaderboard_filterByAgeGroup() {
		final StravaSegmentLeaderboard leaderboard = service().getAllSegmentLeaderboard(TestUtils.SEGMENT_VALID_ID, null, StravaAgeGroup.AGE65_PLUS, null, null, null, null);
		assertNotNull(leaderboard);
		StravaSegmentLeaderboardTest.validate(leaderboard);
	}

	@Test
	public void testGetAllSegmentLeaderboard_filterByWeightClass() {
		final StravaSegmentLeaderboard leaderboard = service().getAllSegmentLeaderboard(TestUtils.SEGMENT_VALID_ID, null, null, StravaWeightClass.KG95PLUS, null, null, null);
		assertNotNull(leaderboard);
		StravaSegmentLeaderboardTest.validate(leaderboard);
	}

	@Test
	public void testGetAllSegmentLeaderboard_filterByFollowing() {
		final StravaSegmentLeaderboard leaderboard = service().getAllSegmentLeaderboard(TestUtils.SEGMENT_VALID_ID, null, null, null, Boolean.TRUE, null, null);
		assertNotNull(leaderboard);
		StravaSegmentLeaderboardTest.validate(leaderboard);
	}

	@Test
	public void testGetAllSegmentLeaderboard_filterByClub() {
		final StravaSegmentLeaderboard leaderboard = service().getAllSegmentLeaderboard(TestUtils.SEGMENT_VALID_ID, null, null, null, null, TestUtils.CLUB_VALID_ID, null);
		assertNotNull(leaderboard);
		StravaSegmentLeaderboardTest.validate(leaderboard);
	}

	@Test
	public void testGetAllSegmentLeaderboard_filterByDateRange() {
		final StravaSegmentLeaderboard leaderboard = service().getAllSegmentLeaderboard(TestUtils.SEGMENT_VALID_ID, null, null, null, null, null, StravaLeaderboardDateRange.THIS_WEEK);
		assertNotNull(leaderboard);
		StravaSegmentLeaderboardTest.validate(leaderboard);
	}

	private SegmentServices service() {
		return SegmentServicesImpl.implementation(TestUtils.getValidToken());
	}

}
