package test.api.rest.segment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javastrava.api.v3.model.StravaSegmentLeaderboard;
import javastrava.api.v3.model.StravaSegmentLeaderboardEntry;
import javastrava.api.v3.model.reference.StravaAgeGroup;
import javastrava.api.v3.model.reference.StravaGender;
import javastrava.api.v3.model.reference.StravaLeaderboardDateRange;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.model.reference.StravaWeightClass;

import org.junit.Test;

import test.api.model.StravaSegmentLeaderboardEntryTest;
import test.api.model.StravaSegmentLeaderboardTest;
import test.api.rest.util.ArrayCallback;
import test.api.rest.util.PagingArrayMethodTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class GetSegmentLeaderboardTest extends PagingArrayMethodTest<StravaSegmentLeaderboardEntry, Integer> {
	@Override
	protected ArrayCallback<StravaSegmentLeaderboardEntry> callback() {
		return (paging -> {
			final List<StravaSegmentLeaderboardEntry> list = api().getSegmentLeaderboard(TestUtils.SEGMENT_VALID_ID, null, null, null, null, null, null,
					paging.getPage(), paging.getPageSize(), null).getEntries();
			return (StravaSegmentLeaderboardEntry[]) list.toArray();
		});
	}

	// 4. Filter by age group
	@Test
	public void testGetSegmentLeaderboard_filterByAgeGroup() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = api().getSegmentLeaderboard(TestUtils.SEGMENT_VALID_ID, null, StravaAgeGroup.AGE35_44, null, null,
					null, null, null, null, null);
			assertNotNull(leaderboard);
			assertFalse(leaderboard.getEntries().isEmpty());
			StravaSegmentLeaderboardTest.validate(leaderboard);
		});
	}

	// 10. Filter by ALL options combined
	@Test
	public void testGetSegmentLeaderboard_filterByAllOptions() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = api().getSegmentLeaderboard(TestUtils.SEGMENT_VALID_ID, StravaGender.MALE, StravaAgeGroup.AGE45_54,
					StravaWeightClass.KG85_94, Boolean.FALSE, TestUtils.CLUB_VALID_ID, StravaLeaderboardDateRange.THIS_YEAR, null, null, null);
			assertNotNull(leaderboard);
			assertFalse(leaderboard.getEntries().isEmpty());
			StravaSegmentLeaderboardTest.validate(leaderboard);
		});
	}

	// 7. Filter by valid club
	@Test
	public void testGetSegmentLeaderboard_filterByClub() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = api().getSegmentLeaderboard(TestUtils.SEGMENT_VALID_ID, null, null, null, null,
					TestUtils.CLUB_VALID_ID, null, null, null, null);
			assertNotNull(leaderboard);
			assertFalse(leaderboard.getEntries().isEmpty());
			StravaSegmentLeaderboardTest.validate(leaderboard);
		});
	}

	// 6. Filter by athletes the authenticated user is following
	@Test
	public void testGetSegmentLeaderboard_filterByFollowing() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = api().getSegmentLeaderboard(TestUtils.SEGMENT_VALID_ID, null, null, null, Boolean.TRUE, null, null,
					null, null, null);
			assertNotNull(leaderboard);
			assertFalse(leaderboard.getEntries().isEmpty());
			StravaSegmentLeaderboardTest.validate(leaderboard);
		});
	}

	// 3. Filter by gender
	@Test
	public void testGetSegmentLeaderboard_filterByGender() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = api().getSegmentLeaderboard(TestUtils.SEGMENT_VALID_ID, StravaGender.FEMALE, null, null, null, null,
					null, null, null, null);
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
			final StravaSegmentLeaderboard leaderboard = api().getSegmentLeaderboard(TestUtils.SEGMENT_VALID_ID, null, null, null, null,
					TestUtils.CLUB_INVALID_ID, null, null, null, null);
			assertNull(leaderboard);
		});
	}

	// 9. Filter by leaderboard date range
	@Test
	public void testGetSegmentLeaderboard_filterByLeaderboardDateRange() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = api().getSegmentLeaderboard(TestUtils.SEGMENT_VALID_ID, null, null, null, null, null,
					StravaLeaderboardDateRange.THIS_YEAR, null, null, null);
			assertNotNull(leaderboard);
			assertFalse(leaderboard.getEntries().isEmpty());
			StravaSegmentLeaderboardTest.validate(leaderboard);
		});
	}

	// 5. Filter by weight class
	@Test
	public void testGetSegmentLeaderboard_filterByWeightClass() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = api().getSegmentLeaderboard(TestUtils.SEGMENT_VALID_ID, null, null, StravaWeightClass.KG75_84, null,
					null, null, null, null, null);
			assertNotNull(leaderboard);
			assertFalse(leaderboard.getEntries().isEmpty());
			StravaSegmentLeaderboardTest.validate(leaderboard);
		});
	}

	@Test
	public void testGetSegmentLeaderboard_hazardousSegment() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = api().getSegmentLeaderboard(TestUtils.SEGMENT_HAZARDOUS_ID, null, null, null, null, null, null, null,
					null, null);
			assertNull(leaderboard);
		});
	}

	// 2. Invalid segment
	@Test
	public void testGetSegmentLeaderboard_invalidSegment() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = api().getSegmentLeaderboard(TestUtils.SEGMENT_INVALID_ID, null, null, null, null, null, null, null,
					null, null);
			assertNull(leaderboard);
		});
	}

	@Test
	public void testGetSegmentLeaderboard_privateSegment() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = api().getSegmentLeaderboard(TestUtils.SEGMENT_PRIVATE_ID, null, null, null, null, null, null, null,
					null, null);
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
			final StravaSegmentLeaderboard leaderboard = api().getSegmentLeaderboard(TestUtils.SEGMENT_OTHER_USER_PRIVATE_ID, null, null, null, null, null,
					null, null, null, null);
			assertNotNull(leaderboard);
			assertTrue(leaderboard.getEntries().isEmpty());
			assertTrue(leaderboard.getAthleteEntries().isEmpty());
		});
	}

	@Test
	public void testGetSegmentLeaderboard_privateWithViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = apiWithViewPrivate().getSegmentLeaderboard(TestUtils.SEGMENT_PRIVATE_ID, null, null, null, null, null,
					null, null, null, null);
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
			final StravaSegmentLeaderboard leaderboard = api().getSegmentLeaderboard(TestUtils.SEGMENT_VALID_ID, null, null, null, null, null, null, null,
					null, null);
			StravaSegmentLeaderboardTest.validate(leaderboard);
		});
	}

	@Override
	protected void validate(final StravaSegmentLeaderboardEntry entry) {
		StravaSegmentLeaderboardEntryTest.validate(entry);

	}

	@Override
	protected void validate(final StravaSegmentLeaderboardEntry entry, final Integer id, final StravaResourceState state) {
		StravaSegmentLeaderboardEntryTest.validate(entry);

	}

}
