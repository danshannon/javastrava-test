package test.api.rest.segment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import javastrava.api.v3.model.StravaSegmentLeaderboard;
import javastrava.api.v3.model.StravaSegmentLeaderboardEntry;
import javastrava.api.v3.model.reference.StravaAgeGroup;
import javastrava.api.v3.model.reference.StravaGender;
import javastrava.api.v3.model.reference.StravaLeaderboardDateRange;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.model.reference.StravaWeightClass;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.api.v3.service.exception.UnauthorizedException;
import javastrava.util.Paging;
import test.api.model.StravaSegmentLeaderboardEntryTest;
import test.api.model.StravaSegmentLeaderboardTest;
import test.api.rest.util.ArrayCallback;
import test.api.rest.util.PagingArrayMethodTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class GetSegmentLeaderboardTest extends PagingArrayMethodTest<StravaSegmentLeaderboardEntry, Integer> {
	@Override
	protected ArrayCallback<StravaSegmentLeaderboardEntry> pagingCallback() {
		return (paging -> {
			final List<StravaSegmentLeaderboardEntry> list = api().getSegmentLeaderboard(TestUtils.SEGMENT_VALID_ID,
					null, null, null, null, null, null, paging.getPage(), paging.getPageSize(), 0).getEntries();
			final StravaSegmentLeaderboardEntry[] entries = new StravaSegmentLeaderboardEntry[list.size()];
			int i = 0;
			for (final StravaSegmentLeaderboardEntry entry : list) {
				entries[i] = entry;
				i++;
			}
			return entries;
		} );
	}

	// 4. Filter by age group
	@Test
	public void testGetSegmentLeaderboard_filterByAgeGroup() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = api().getSegmentLeaderboard(TestUtils.SEGMENT_VALID_ID, null,
					StravaAgeGroup.AGE35_44, null, null, null, null, null, null, null);
			assertNotNull(leaderboard);
			assertFalse(leaderboard.getEntries().isEmpty());
			StravaSegmentLeaderboardTest.validate(leaderboard);
		} );
	}

	// 10. Filter by ALL options combined
	@Test
	public void testGetSegmentLeaderboard_filterByAllOptions() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = api().getSegmentLeaderboard(TestUtils.SEGMENT_VALID_ID,
					StravaGender.MALE, StravaAgeGroup.AGE45_54, StravaWeightClass.KG85_94, Boolean.FALSE,
					TestUtils.CLUB_VALID_ID, StravaLeaderboardDateRange.THIS_YEAR, null, null, null);
			assertNotNull(leaderboard);
			assertFalse(leaderboard.getEntries().isEmpty());
			StravaSegmentLeaderboardTest.validate(leaderboard);
		} );
	}

	// 7. Filter by valid club
	@Test
	public void testGetSegmentLeaderboard_filterByClub() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = api().getSegmentLeaderboard(TestUtils.SEGMENT_VALID_ID, null,
					null, null, null, TestUtils.CLUB_VALID_ID, null, null, null, null);
			assertNotNull(leaderboard);
			assertFalse(leaderboard.getEntries().isEmpty());
			StravaSegmentLeaderboardTest.validate(leaderboard);
		} );
	}

	// 6. Filter by athletes the authenticated user is following
	@Test
	public void testGetSegmentLeaderboard_filterByFollowing() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = api().getSegmentLeaderboard(TestUtils.SEGMENT_VALID_ID, null,
					null, null, Boolean.TRUE, null, null, null, null, null);
			assertNotNull(leaderboard);
			assertFalse(leaderboard.getEntries().isEmpty());
			StravaSegmentLeaderboardTest.validate(leaderboard);
		} );
	}

	// 3. Filter by gender
	@Test
	public void testGetSegmentLeaderboard_filterByGender() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = api().getSegmentLeaderboard(TestUtils.SEGMENT_VALID_ID,
					StravaGender.FEMALE, null, null, null, null, null, null, null, null);
			assertNotNull(leaderboard);
			assertFalse(leaderboard.getEntries().isEmpty());
			for (final StravaSegmentLeaderboardEntry entry : leaderboard.getEntries()) {
				assertEquals(StravaGender.FEMALE, entry.getAthleteGender());
				validate(entry);
			}
		} );
	}

	// 8. Filter by invalid club
	@Test
	public void testGetSegmentLeaderboard_filterByInvalidClub() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getSegmentLeaderboard(TestUtils.SEGMENT_VALID_ID, null, null, null, null,
						TestUtils.CLUB_INVALID_ID, null, null, null, null);
			} catch (final NotFoundException e) {
				// expected
				return;
			}
			fail("Got leaderboard for an invalid club!");
		} );
	}

	// 9. Filter by leaderboard date range
	@Test
	public void testGetSegmentLeaderboard_filterByLeaderboardDateRange() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = api().getSegmentLeaderboard(TestUtils.SEGMENT_VALID_ID, null,
					null, null, null, null, StravaLeaderboardDateRange.THIS_YEAR, null, null, null);
			assertNotNull(leaderboard);
			assertFalse(leaderboard.getEntries().isEmpty());
			StravaSegmentLeaderboardTest.validate(leaderboard);
		} );
	}

	// 5. Filter by weight class
	@Test
	public void testGetSegmentLeaderboard_filterByWeightClass() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = api().getSegmentLeaderboard(TestUtils.SEGMENT_VALID_ID, null,
					null, StravaWeightClass.KG75_84, null, null, null, null, null, null);
			assertNotNull(leaderboard);
			assertFalse(leaderboard.getEntries().isEmpty());
			StravaSegmentLeaderboardTest.validate(leaderboard);
		} );
	}

	@Test
	public void testGetSegmentLeaderboard_hazardousSegment() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getSegmentLeaderboard(TestUtils.SEGMENT_HAZARDOUS_ID, null, null, null, null, null, null, null,
						null, null);
			} catch (final NotFoundException e) {
				// expected
				return;
			}
			fail("Returned leaderboard for a segment flagged as hazardous");
		} );
	}

	// 2. Invalid segment
	@Test
	public void testGetSegmentLeaderboard_invalidSegment() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getSegmentLeaderboard(TestUtils.SEGMENT_INVALID_ID, null, null, null, null, null, null, null,
						null, null);
			} catch (final NotFoundException e) {
				// expected
				return;
			}
			fail("Got leaderboard for an invalid segment");
		} );
	}

	@Test
	public void testGetSegmentLeaderboard_privateSegmentOtherUser() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getSegmentLeaderboard(TestUtils.SEGMENT_OTHER_USER_PRIVATE_ID, null, null, null, null, null, null,
						null, null, null);
			} catch (final UnauthorizedException e) {
				// expected
				return;
			}
			fail("Got segment leaderboard for a private segment belonging to another user");
		} );
	}

	@Test
	public void testGetSegmentLeaderboard_privateSegmentWithoutViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = api().getSegmentLeaderboard(TestUtils.SEGMENT_PRIVATE_ID, null,
					null, null, null, null, null, null, null, null);
			// Should return an empty leaderboard
			assertNotNull(leaderboard);
			assertNotNull(leaderboard.getEntries());
			// Workaround for issue javastrava-api #71 - see
			// https://github.com/danshannon/javastravav3api/issues/71
			// assertTrue(leaderboard.getEntries().isEmpty());
			// End of workaround
		} );
	}

	@Test
	public void testGetSegmentLeaderboard_privateWithViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = apiWithViewPrivate().getSegmentLeaderboard(
					TestUtils.SEGMENT_PRIVATE_ID, null, null, null, null, null, null, null, null, null);
			assertNotNull(leaderboard);
			assertFalse(leaderboard.getEntries().isEmpty());
			StravaSegmentLeaderboardTest.validate(leaderboard);
		} );
	}

	// Test cases
	// 1. Valid segment, no filtering
	@Test
	public void testGetSegmentLeaderboard_validSegment() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = api().getSegmentLeaderboard(TestUtils.SEGMENT_VALID_ID, null,
					null, null, null, null, null, null, null, null);
			StravaSegmentLeaderboardTest.validate(leaderboard);
		} );
	}

	@Override
	@Test
	public void testPageNumberAndSize() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboardEntry[] bothPages = pagingCallback().getArray(new Paging(1, 2));
			assertNotNull(bothPages);
			assertEquals(3, bothPages.length);
			validateList(bothPages);
			final StravaSegmentLeaderboardEntry[] firstPage = pagingCallback().getArray(new Paging(1, 1));
			assertNotNull(firstPage);
			assertEquals(2, firstPage.length);
			validateList(firstPage);
			final StravaSegmentLeaderboardEntry[] secondPage = pagingCallback().getArray(new Paging(2, 1));
			assertNotNull(secondPage);
			assertEquals(2, secondPage.length);
			validateList(secondPage);

			// The first entry in bothPages should be the same as the first
			// entry in firstPage
			assertEquals(bothPages[0], firstPage[0]);

			// The second entry in bothPages should be the same as the first
			// entry in secondPage
			assertEquals(bothPages[1], secondPage[0]);

		} );
	}

	@Override
	@Test
	public void testPageSize() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Get a list with only one entry
			final StravaSegmentLeaderboardEntry[] list = pagingCallback().getArray(new Paging(1, 1));
			assertNotNull(list);
			assertEquals(2, list.length);

			// Validate all the entries in the list
			validateList(list);
		} );
	}

	@Override
	@Test
	public void testPagingOutOfRangeHigh() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Get the 200,000,000th entry in the list - this is pretty unlikely
			// to return anything!
			final StravaSegmentLeaderboardEntry[] list = pagingCallback().getArray(new Paging(1000000, 200));

			assertNotNull(list);
			assertEquals(1, list.length);
		} );
	}

	@Override
	protected void validate(final StravaSegmentLeaderboardEntry entry) {
		StravaSegmentLeaderboardEntryTest.validate(entry);

	}

	@Override
	protected void validate(final StravaSegmentLeaderboardEntry entry, final Integer id,
			final StravaResourceState state) {
		StravaSegmentLeaderboardEntryTest.validate(entry);

	}

}
