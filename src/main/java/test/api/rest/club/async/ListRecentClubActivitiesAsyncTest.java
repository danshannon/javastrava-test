package test.api.rest.club.async;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.service.exception.UnauthorizedException;
import javastrava.util.Paging;
import test.api.rest.callback.APIListCallback;
import test.api.rest.club.ListRecentClubActivitiesTest;
import test.api.rest.util.ArrayCallback;
import test.issues.strava.Issue94;
import test.service.standardtests.data.ClubDataUtils;
import test.utils.RateLimitedTestRunner;

public class ListRecentClubActivitiesAsyncTest extends ListRecentClubActivitiesTest {

	/**
	 * @see test.api.rest.club.ListRecentClubActivitiesTest#listCallback()
	 */
	@Override
	protected APIListCallback<StravaActivity, Integer> listCallback() {
		return (api, id) -> api.listRecentClubActivitiesAsync(id, null, null).get();
	}

	/**
	 * @see test.api.rest.club.ListRecentClubActivitiesTest#pagingCallback()
	 */
	@Override
	protected ArrayCallback<StravaActivity> pagingCallback() {
		return paging -> api().listRecentClubActivitiesAsync(validId(), paging.getPage(), paging.getPageSize()).get();
	}

	/**
	 * Check that no activity flagged as private is returned
	 */
	@Override
	public void testListRecentClubActivities_checkPrivacy() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaActivity[] activities = api().listRecentClubActivitiesAsync(ClubDataUtils.CLUB_PUBLIC_MEMBER_ID, null, null).get();
			for (final StravaActivity activity : activities) {
				if (activity.getPrivateActivity().equals(Boolean.TRUE)) {
					fail("List recent club activities returned an activity flagged as private!"); //$NON-NLS-1$
				}
			}
		});
	}

	// 4. StravaClub with > 200 activities (according to Strava, should only
	// return a max of 200 results)
	@SuppressWarnings("boxing")
	@Override
	public void testListRecentClubActivities_moreThan200() throws Exception {
		RateLimitedTestRunner.run(() -> {
			StravaActivity[] activities = api().listRecentClubActivitiesAsync(ClubDataUtils.CLUB_VALID_ID, 2, 200).get();
			assertNotNull(activities);
			assertEquals(0, activities.length);
			activities = api().listRecentClubActivitiesAsync(ClubDataUtils.CLUB_VALID_ID, 1, 200).get();
			assertNotNull(activities);
			assertFalse(0 == activities.length);
			validateArray(activities);
		});
	}

	// 3. StravaClub the current authenticated athlete is NOT a member of
	// (according to Strava should return 0 results)
	@Override
	public void testListRecentClubActivities_nonMember() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().listRecentClubActivitiesAsync(ClubDataUtils.CLUB_PUBLIC_NON_MEMBER_ID, null, null).get();
			} catch (final UnauthorizedException e) {
				// expected
				return;
			}
			fail("Got list of recent activities for a club the authenticated user is not a member of"); //$NON-NLS-1$
		});
	}

	@SuppressWarnings("boxing")
	@Override
	public void testPageNumberAndSize() throws Exception {
		RateLimitedTestRunner.run(() -> {
			if (new Issue94().isIssue()) {
				return;
			}
			final StravaActivity[] bothPages = this.pagingCallback().getArray(new Paging(1, 2));
			assertNotNull(bothPages);
			assertEquals(2, bothPages.length);
			validateArray(bothPages);
			final StravaActivity[] firstPage = this.pagingCallback().getArray(new Paging(1, 1));
			assertNotNull(firstPage);
			assertEquals(1, firstPage.length);
			validateArray(firstPage);
			final StravaActivity[] secondPage = this.pagingCallback().getArray(new Paging(2, 1));
			assertNotNull(secondPage);
			assertEquals(1, secondPage.length);
			validateArray(secondPage);

			// The first entry in bothPages should be the same as the first
			// entry in firstPage
			assertEquals(bothPages[0], firstPage[0]);

			// The second entry in bothPages should be the same as the first
			// entry in secondPage
			assertEquals(bothPages[1], secondPage[0]);
		});
	}

}
