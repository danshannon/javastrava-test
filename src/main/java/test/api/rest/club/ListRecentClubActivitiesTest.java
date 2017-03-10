package test.api.rest.club;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.service.exception.UnauthorizedException;
import javastrava.util.Paging;
import test.api.model.StravaActivityTest;
import test.api.rest.APIPagingListTest;
import test.api.rest.callback.APIListCallback;
import test.api.rest.util.ArrayCallback;
import test.issues.strava.Issue94;
import test.service.standardtests.data.ClubDataUtils;
import test.utils.RateLimitedTestRunner;

public class ListRecentClubActivitiesTest extends APIPagingListTest<StravaActivity, Integer> {

	/**
	 * @see test.api.rest.APIListTest#invalidId()
	 */
	@Override
	protected Integer invalidId() {
		return ClubDataUtils.CLUB_INVALID_ID;
	}

	/**
	 * @see test.api.rest.APIListTest#listCallback()
	 */
	@Override
	protected APIListCallback<StravaActivity, Integer> listCallback() {
		return (api, id) -> api.listRecentClubActivities(id, null, null);
	}

	/**
	 * @see test.api.rest.APIPagingListTest#pagingCallback()
	 */
	@Override
	protected ArrayCallback<StravaActivity> pagingCallback() {
		return paging -> api().listRecentClubActivities(validId(), paging.getPage(), paging.getPageSize());
	}

	/**
	 * @see test.api.rest.APIListTest#privateId()
	 */
	@Override
	protected Integer privateId() {
		return null;
	}

	/**
	 * @see test.api.rest.APIListTest#privateIdBelongsToOtherUser()
	 */
	@Override
	protected Integer privateIdBelongsToOtherUser() {
		return null;
	}

	/**
	 * Check that no activity flagged as private is returned
	 */
	@Test
	public void testListRecentClubActivities_checkPrivacy() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaActivity[] activities = api().listRecentClubActivities(ClubDataUtils.CLUB_PUBLIC_MEMBER_ID, null, null);
			for (final StravaActivity activity : activities) {
				if (activity.getPrivateActivity().equals(Boolean.TRUE)) {
					fail("List recent club activities returned an activity flagged as private!");
				}
			}
		});
	}

	// 4. StravaClub with > 200 activities (according to Strava, should only
	// return a max of 200 results)
	@Test
	public void testListRecentClubActivities_moreThan200() throws Exception {
		RateLimitedTestRunner.run(() -> {
			StravaActivity[] activities = api().listRecentClubActivities(ClubDataUtils.CLUB_VALID_ID, 2, 200);
			assertNotNull(activities);
			assertEquals(0, activities.length);
			activities = api().listRecentClubActivities(ClubDataUtils.CLUB_VALID_ID, 1, 200);
			assertNotNull(activities);
			assertFalse(0 == activities.length);
			validateArray(activities);
		});
	}

	// 3. StravaClub the current authenticated athlete is NOT a member of
	// (according to Strava should return 0 results)
	@Test
	public void testListRecentClubActivities_nonMember() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().listRecentClubActivities(ClubDataUtils.CLUB_PUBLIC_NON_MEMBER_ID, null, null);
			} catch (final UnauthorizedException e) {
				// expected
				return;
			}
			fail("Got list of recent activities for a club the authenticated user is not a member of");
		});
	}

	// This is a workaround for issue javastrava-api #18
	// (https://github.com/danshannon/javastravav3api/issues/18)
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

	@Override
	protected void validate(final StravaActivity activity) {
		StravaActivityTest.validate(activity);

	}

	/**
	 * @see test.api.rest.APIListTest#validateArray(java.lang.Object[])
	 */
	@Override
	protected void validateArray(final StravaActivity[] activities) {
		for (final StravaActivity activity : activities) {
			StravaActivityTest.validate(activity);
		}
	}

	/**
	 * @see test.api.rest.APIListTest#validId()
	 */
	@Override
	protected Integer validId() {
		return ClubDataUtils.CLUB_VALID_ID;
	}

	/**
	 * @see test.api.rest.APIListTest#validIdBelongsToOtherUser()
	 */
	@Override
	protected Integer validIdBelongsToOtherUser() {
		return null;
	}

	/**
	 * @see test.api.rest.APIListTest#validIdNoChildren()
	 */
	@Override
	protected Integer validIdNoChildren() {
		return null;
	}

}
