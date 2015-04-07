package test.api.rest.club;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.api.v3.service.exception.UnauthorizedException;
import javastrava.util.Paging;

import org.junit.Test;

import test.api.model.StravaActivityTest;
import test.api.rest.util.ArrayCallback;
import test.api.rest.util.PagingArrayMethodTest;
import test.issues.strava.Issue94;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class ListRecentClubActivitiesTest extends PagingArrayMethodTest<StravaActivity, Integer> {
	@Override
	protected ArrayCallback<StravaActivity> callback() {
		return (paging -> api().listRecentClubActivities(TestUtils.CLUB_VALID_ID, paging.getPage(), paging.getPageSize()));
	}

	// 2. Invalid club
	@Test
	public void testListRecentClubActivities_invalidClub() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().listRecentClubActivities(TestUtils.CLUB_INVALID_ID, null, null);
			} catch (final NotFoundException e) {
				// expected
				return;
			}
			fail("Returned recent activities for a non-existent club");
		});
	}

	// 4. StravaClub with > 200 activities (according to Strava, should only return a max of 200 results)
	@Test
	public void testListRecentClubActivities_moreThan200() throws Exception {
		RateLimitedTestRunner.run(() -> {
			StravaActivity[] activities = api().listRecentClubActivities(TestUtils.CLUB_VALID_ID, 2, 200);
			assertNotNull(activities);
			assertEquals(0, activities.length);
			activities = api().listRecentClubActivities(TestUtils.CLUB_VALID_ID, 1, 200);
			assertNotNull(activities);
			assertFalse(0 == activities.length);
			validateList(activities);
		});
	}

	// 3. StravaClub the current authenticated athlete is NOT a member of (according to Strava should return 0 results)
	@Test
	public void testListRecentClubActivities_nonMember() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().listRecentClubActivities(TestUtils.CLUB_PUBLIC_NON_MEMBER_ID, null, null);
			} catch (final UnauthorizedException e) {
				// expected
				return;
			}
			fail("Got list of recent activities for a club the authenticated user is not a member of");
		});
	}

	// Test cases
	// 1. Valid club
	@Test
	public void testListRecentClubActivities_validClub() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaActivity[] activities = api().listRecentClubActivities(TestUtils.CLUB_VALID_ID, null, null);

			assertNotNull(activities);
		});
	}

	// This is a workaround for issue javastrava-api #18 (https://github.com/danshannon/javastravav3api/issues/18)
	@Override
	@Test
	public void testPageNumberAndSize() throws Exception {
		RateLimitedTestRunner.run(() -> {
			if (new Issue94().isIssue()) {
				return;
			}
			final StravaActivity[] bothPages = callback().getArray(new Paging(1, 2));
			assertNotNull(bothPages);
			assertEquals(2, bothPages.length);
			validateList(bothPages);
			final StravaActivity[] firstPage = callback().getArray(new Paging(1, 1));
			assertNotNull(firstPage);
			assertEquals(1, firstPage.length);
			validateList(firstPage);
			final StravaActivity[] secondPage = callback().getArray(new Paging(2, 1));
			assertNotNull(secondPage);
			assertEquals(1, secondPage.length);
			validateList(secondPage);

			// The first entry in bothPages should be the same as the first entry in firstPage
			assertEquals(bothPages[0], firstPage[0]);

			// The second entry in bothPages should be the same as the first entry in secondPage
			assertEquals(bothPages[1], secondPage[0]);
			});
	}

	/**
	 * Check that no activity flagged as private is returned
	 */
	@Test
	public void testListRecentClubActivities_checkPrivacyAuthenticatedAthlete() {
		// TODO Not yet implemented
		fail("Not yet implemented!");
	}

	/**
	 * Check that no activity flagged as private is returned
	 */
	@Test
	public void testListRecentClubActivities_checkPrivacyOtherAthletes() {
		// TODO Not yet implemented
		fail("Not yet implemented!");
	}

	@Override
	protected void validate(final StravaActivity activity) {
		StravaActivityTest.validateActivity(activity);

	}

	@Override
	protected void validate(final StravaActivity activity, final Integer id, final StravaResourceState state) {
		StravaActivityTest.validateActivity(activity, id, state);

	}

}
