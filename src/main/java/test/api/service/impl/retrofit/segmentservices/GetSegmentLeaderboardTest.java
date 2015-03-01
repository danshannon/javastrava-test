package test.api.service.impl.retrofit.segmentservices;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import javastrava.api.v3.model.StravaSegmentLeaderboard;
import javastrava.api.v3.model.StravaSegmentLeaderboardEntry;
import javastrava.api.v3.model.reference.StravaAgeGroup;
import javastrava.api.v3.model.reference.StravaGender;
import javastrava.api.v3.model.reference.StravaLeaderboardDateRange;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.model.reference.StravaWeightClass;
import javastrava.api.v3.service.SegmentServices;
import javastrava.api.v3.service.impl.retrofit.SegmentServicesImpl;
import javastrava.util.Paging;

import org.junit.Test;

import test.api.model.StravaSegmentLeaderboardEntryTest;
import test.api.model.StravaSegmentLeaderboardTest;
import test.api.service.impl.ListCallback;
import test.api.service.impl.PagingListMethodTest;
import test.utils.TestUtils;

public class GetSegmentLeaderboardTest extends PagingListMethodTest<StravaSegmentLeaderboardEntry, Integer>{
	// Test cases
	// 1. Valid segment, no filtering
	@Test
	public void testGetSegmentLeaderboard_validSegment() {
		final SegmentServices service = service();
		final StravaSegmentLeaderboard leaderboard = service.getSegmentLeaderboard(TestUtils.SEGMENT_VALID_ID);
		StravaSegmentLeaderboardTest.validate(leaderboard);
	}

	// 2. Invalid segment
	@Test
	public void testGetSegmentLeaderboard_invalidSegment() {
		final SegmentServices service = service();
		final StravaSegmentLeaderboard leaderboard = service.getSegmentLeaderboard(TestUtils.SEGMENT_INVALID_ID);
		assertNull(leaderboard);
	}

	// 3. Filter by gender
	@Test
	public void testGetSegmentLeaderboard_filterByGender() {
		final SegmentServices service = service();
		final StravaSegmentLeaderboard leaderboard = service.getSegmentLeaderboard(TestUtils.SEGMENT_VALID_ID, StravaGender.FEMALE, null, null, null, null, null,
				null, null);
		assertNotNull(leaderboard);
		assertFalse(leaderboard.getEntries().isEmpty());
		for (final StravaSegmentLeaderboardEntry entry : leaderboard.getEntries()) {
			assertEquals(StravaGender.FEMALE, entry.getAthleteGender());
			validate(entry);
		}
	}

	// 4. Filter by age group
	@Test
	public void testGetSegmentLeaderboard_filterByAgeGroup() {
		final SegmentServices service = service();
		final StravaSegmentLeaderboard leaderboard = service.getSegmentLeaderboard(TestUtils.SEGMENT_VALID_ID, null, StravaAgeGroup.AGE35_44, null, null, null, null,
				null, null);
		assertNotNull(leaderboard);
		assertFalse(leaderboard.getEntries().isEmpty());
		StravaSegmentLeaderboardTest.validate(leaderboard);
	}

	// 5. Filter by weight class
	@Test
	public void testGetSegmentLeaderboard_filterByWeightClass() {
		final SegmentServices service = service();
		final StravaSegmentLeaderboard leaderboard = service.getSegmentLeaderboard(TestUtils.SEGMENT_VALID_ID, null, null, StravaWeightClass.KG75_84, null, null,
				null, null, null);
		assertNotNull(leaderboard);
		assertFalse(leaderboard.getEntries().isEmpty());
		StravaSegmentLeaderboardTest.validate(leaderboard);
	}

	// 6. Filter by athletes the authenticated user is following
	@Test
	public void testGetSegmentLeaderboard_filterByFollowing() {
		final SegmentServices service = service();
		final StravaSegmentLeaderboard leaderboard = service.getSegmentLeaderboard(TestUtils.SEGMENT_VALID_ID, null, null, null, Boolean.TRUE, null, null, null, null);
		assertNotNull(leaderboard);
		assertFalse(leaderboard.getEntries().isEmpty());
		StravaSegmentLeaderboardTest.validate(leaderboard);
	}

	// 7. Filter by valid club
	@Test
	public void testGetSegmentLeaderboard_filterByClub() {
		final SegmentServices service = service();
		final StravaSegmentLeaderboard leaderboard = service.getSegmentLeaderboard(TestUtils.SEGMENT_VALID_ID, null, null, null, null, TestUtils.CLUB_VALID_ID, null,
				null, null);
		assertNotNull(leaderboard);
		assertFalse(leaderboard.getEntries().isEmpty());
		StravaSegmentLeaderboardTest.validate(leaderboard);
	}

	// 8. Filter by invalid club
	@Test
	public void testGetSegmentLeaderboard_filterByInvalidClub() {
		final SegmentServices service = service();
		final StravaSegmentLeaderboard leaderboard = service.getSegmentLeaderboard(TestUtils.SEGMENT_VALID_ID, null, null, null, null, TestUtils.CLUB_INVALID_ID,
				null, null, null);
		// TODO There's a Strava bug here - returns the full leaderboard
		assertNull(leaderboard);
	}

	// 9. Filter by leaderboard date range
	@Test
	public void testGetSegmentLeaderboard_filterByLeaderboardDateRange() {
		final SegmentServices service = service();
		final StravaSegmentLeaderboard leaderboard = service.getSegmentLeaderboard(TestUtils.SEGMENT_VALID_ID, null, null, null, null, null,
				StravaLeaderboardDateRange.THIS_YEAR, null, null);
		assertNotNull(leaderboard);
		assertFalse(leaderboard.getEntries().isEmpty());
		StravaSegmentLeaderboardTest.validate(leaderboard);
	}

	// 10. Filter by ALL options combined
	@Test
	public void testGetSegmentLeaderboard_filterByAllOptions() {
		final SegmentServices service = service();
		final StravaSegmentLeaderboard leaderboard = service.getSegmentLeaderboard(TestUtils.SEGMENT_VALID_ID, StravaGender.MALE, StravaAgeGroup.AGE45_54,
				StravaWeightClass.KG85_94, Boolean.FALSE, TestUtils.CLUB_VALID_ID, StravaLeaderboardDateRange.THIS_YEAR, null, null);
		assertNotNull(leaderboard);
		assertFalse(leaderboard.getEntries().isEmpty());
	}

	private SegmentServices service() {
		return SegmentServicesImpl.implementation(TestUtils.getValidToken());
	}

	@Override
	protected void validate(final StravaSegmentLeaderboardEntry entry, final Integer id, final StravaResourceState state) {
		StravaSegmentLeaderboardEntryTest.validate(entry);

	}

	@Override
	protected void validate(final StravaSegmentLeaderboardEntry entry) {
		StravaSegmentLeaderboardEntryTest.validate(entry);

	}

	@Override
	protected ListCallback<StravaSegmentLeaderboardEntry> callback() {
		return (new ListCallback<StravaSegmentLeaderboardEntry>() {

			@Override
			public List<StravaSegmentLeaderboardEntry> getList(final Paging paging) {
				return service().getSegmentLeaderboard(TestUtils.SEGMENT_VALID_ID, paging).getEntries();
			}

		});
	}


}
