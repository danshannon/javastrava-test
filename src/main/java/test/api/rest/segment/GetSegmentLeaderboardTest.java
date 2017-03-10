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
import javastrava.api.v3.model.reference.StravaWeightClass;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.util.Paging;
import test.api.model.StravaSegmentLeaderboardEntryTest;
import test.api.model.StravaSegmentLeaderboardTest;
import test.api.rest.APIGetTest;
import test.api.rest.callback.APIGetCallback;
import test.api.rest.util.ArrayCallback;
import test.issues.strava.Issue73;
import test.service.standardtests.data.ClubDataUtils;
import test.service.standardtests.data.SegmentDataUtils;
import test.utils.RateLimitedTestRunner;

public class GetSegmentLeaderboardTest extends APIGetTest<StravaSegmentLeaderboard, Integer> implements ArrayCallback<StravaSegmentLeaderboardEntry> {
	/**
	 * @see test.api.rest.APIGetTest#get_privateWithoutViewPrivate()
	 */
	@Override
	public void get_privateWithoutViewPrivate() throws Exception {
		if (new Issue73().isIssue()) {
			return;
		}
		super.get_privateWithoutViewPrivate();
	}

	/**
	 * @see test.api.rest.util.ArrayCallback#getArray(javastrava.util.Paging)
	 */
	@Override
	public StravaSegmentLeaderboardEntry[] getArray(final Paging paging) throws Exception {
		final List<StravaSegmentLeaderboardEntry> list = api().getSegmentLeaderboard(validId(), null, null, null, null, null, null, paging.getPage(), paging.getPageSize(), 0).getEntries();
		final StravaSegmentLeaderboardEntry[] array = new StravaSegmentLeaderboardEntry[list.size()];
		int i = 0;
		for (final StravaSegmentLeaderboardEntry entry : list) {
			array[i] = entry;
			i++;
		}
		return array;
	}

	@Override
	protected APIGetCallback<StravaSegmentLeaderboard, Integer> getter() {
		return ((api, id) -> api.getSegmentLeaderboard(id, null, null, null, null, null, null, null, null, null));
	}

	/**
	 * @see test.api.rest.APIGetTest#invalidId()
	 */
	@Override
	protected Integer invalidId() {
		return SegmentDataUtils.SEGMENT_INVALID_ID;
	}

	/**
	 * @see test.api.rest.APIGetTest#privateId()
	 */
	@Override
	protected Integer privateId() {
		return SegmentDataUtils.SEGMENT_PRIVATE_ID;
	}

	/**
	 * @see test.api.rest.APIGetTest#privateIdBelongsToOtherUser()
	 */
	@Override
	protected Integer privateIdBelongsToOtherUser() {
		return SegmentDataUtils.SEGMENT_OTHER_USER_PRIVATE_ID;
	}

	// 4. Filter by age group
	@Test
	public void testGetSegmentLeaderboard_filterByAgeGroup() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = api().getSegmentLeaderboard(SegmentDataUtils.SEGMENT_VALID_ID, null, StravaAgeGroup.AGE35_44, null, null, null, null, null, null, null);
			assertNotNull(leaderboard);
			assertFalse(leaderboard.getEntries().isEmpty());
			StravaSegmentLeaderboardTest.validate(leaderboard);
		});
	}

	// 10. Filter by ALL options combined
	@Test
	public void testGetSegmentLeaderboard_filterByAllOptions() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = api().getSegmentLeaderboard(SegmentDataUtils.SEGMENT_VALID_ID, StravaGender.MALE, StravaAgeGroup.AGE45_54, StravaWeightClass.KG85_94,
					Boolean.FALSE, ClubDataUtils.CLUB_VALID_ID, StravaLeaderboardDateRange.THIS_YEAR, null, null, null);
			assertNotNull(leaderboard);
			assertFalse(leaderboard.getEntries().isEmpty());
			StravaSegmentLeaderboardTest.validate(leaderboard);
		});
	}

	// 7. Filter by valid club
	@Test
	public void testGetSegmentLeaderboard_filterByClub() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = api().getSegmentLeaderboard(SegmentDataUtils.SEGMENT_VALID_ID, null, null, null, null, ClubDataUtils.CLUB_VALID_ID, null, null, null, null);
			assertNotNull(leaderboard);
			assertFalse(leaderboard.getEntries().isEmpty());
			StravaSegmentLeaderboardTest.validate(leaderboard);
		});
	}

	// 6. Filter by athletes the authenticated user is following
	@Test
	public void testGetSegmentLeaderboard_filterByFollowing() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = api().getSegmentLeaderboard(SegmentDataUtils.SEGMENT_VALID_ID, null, null, null, Boolean.TRUE, null, null, null, null, null);
			assertNotNull(leaderboard);
			assertFalse(leaderboard.getEntries().isEmpty());
			StravaSegmentLeaderboardTest.validate(leaderboard);
		});
	}

	// 3. Filter by gender
	@Test
	public void testGetSegmentLeaderboard_filterByGender() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = api().getSegmentLeaderboard(SegmentDataUtils.SEGMENT_VALID_ID, StravaGender.FEMALE, null, null, null, null, null, null, null, null);
			assertNotNull(leaderboard);
			assertFalse(leaderboard.getEntries().isEmpty());
			for (final StravaSegmentLeaderboardEntry entry : leaderboard.getEntries()) {
				assertEquals(StravaGender.FEMALE, entry.getAthleteGender());
				StravaSegmentLeaderboardEntryTest.validate(entry);
			}
		});
	}

	// 8. Filter by invalid club
	@Test
	public void testGetSegmentLeaderboard_filterByInvalidClub() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getSegmentLeaderboard(SegmentDataUtils.SEGMENT_VALID_ID, null, null, null, null, ClubDataUtils.CLUB_INVALID_ID, null, null, null, null);
			} catch (final NotFoundException e) {
				// expected
				return;
			}
			fail("Got leaderboard for an invalid club!");
		});
	}

	// 9. Filter by leaderboard date range
	@Test
	public void testGetSegmentLeaderboard_filterByLeaderboardDateRange() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = api().getSegmentLeaderboard(SegmentDataUtils.SEGMENT_VALID_ID, null, null, null, null, null, StravaLeaderboardDateRange.THIS_YEAR, null, null,
					null);
			assertNotNull(leaderboard);
			assertFalse(leaderboard.getEntries().isEmpty());
			StravaSegmentLeaderboardTest.validate(leaderboard);
		});
	}

	// 5. Filter by weight class
	@Test
	public void testGetSegmentLeaderboard_filterByWeightClass() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = api().getSegmentLeaderboard(SegmentDataUtils.SEGMENT_VALID_ID, null, null, StravaWeightClass.KG75_84, null, null, null, null, null, null);
			assertNotNull(leaderboard);
			assertFalse(leaderboard.getEntries().isEmpty());
			StravaSegmentLeaderboardTest.validate(leaderboard);
		});
	}

	@Test
	public void testGetSegmentLeaderboard_hazardousSegment() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getSegmentLeaderboard(SegmentDataUtils.SEGMENT_HAZARDOUS_ID, null, null, null, null, null, null, null, null, null);
			} catch (final NotFoundException e) {
				// expected
				return;
			}
			fail("Returned leaderboard for a segment flagged as hazardous");
		});
	}

	@Test
	public void testPageNumberAndSize() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboardEntry[] bothPages = getArray(new Paging(1, 2));
			assertNotNull(bothPages);
			assertEquals(3, bothPages.length);
			validateArray(bothPages);
			final StravaSegmentLeaderboardEntry[] firstPage = getArray(new Paging(1, 1));
			assertNotNull(firstPage);
			assertEquals(2, firstPage.length);
			validateArray(firstPage);
			final StravaSegmentLeaderboardEntry[] secondPage = getArray(new Paging(2, 1));
			assertNotNull(secondPage);
			assertEquals(2, secondPage.length);
			validateArray(secondPage);

			// The first entry in bothPages should be the same as the first
			// entry in firstPage
			assertEquals(bothPages[0], firstPage[0]);

			// The second entry in bothPages should be the same as the first
			// entry in secondPage
			assertEquals(bothPages[1], secondPage[0]);

		});
	}

	@Test
	public void testPageSize() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Get a list with only one entry
			final StravaSegmentLeaderboardEntry[] list = getArray(new Paging(1, 1));
			assertNotNull(list);
			assertEquals(2, list.length);

			// Validate all the entries in the list
			validateArray(list);
		});
	}

	@Test
	public void testPagingOutOfRangeHigh() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Get the 200,000,000th entry in the list - this is pretty unlikely
			// to return anything!
			final StravaSegmentLeaderboardEntry[] list = getArray(new Paging(1000000, 200));

			assertNotNull(list);
			assertEquals(1, list.length);
		});
	}

	/**
	 * @see test.api.rest.APITest#validate(java.lang.Object)
	 */
	@Override
	protected void validate(final StravaSegmentLeaderboard leaderboard) throws Exception {
		StravaSegmentLeaderboardTest.validate(leaderboard);

	}

	/**
	 * @param entries
	 */
	@SuppressWarnings("static-method")
	protected void validateArray(final StravaSegmentLeaderboardEntry[] entries) {
		for (final StravaSegmentLeaderboardEntry entry : entries) {
			StravaSegmentLeaderboardEntryTest.validate(entry);
		}

	}

	/**
	 * @see test.api.rest.APIGetTest#validId()
	 */
	@Override
	protected Integer validId() {
		return SegmentDataUtils.SEGMENT_VALID_ID;
	}

	/**
	 * @see test.api.rest.APIGetTest#validIdBelongsToOtherUser()
	 */
	@Override
	protected Integer validIdBelongsToOtherUser() {
		return null;
	}

}
