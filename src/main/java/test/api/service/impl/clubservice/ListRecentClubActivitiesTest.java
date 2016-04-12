package test.api.service.impl.clubservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.util.Paging;
import test.api.model.StravaActivityTest;
import test.api.service.standardtests.PagingListMethodTest;
import test.api.service.standardtests.callbacks.PagingListCallback;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class ListRecentClubActivitiesTest extends PagingListMethodTest<StravaActivity, Integer> {
	@Override
	protected PagingListCallback<StravaActivity> callback() {
		return (paging -> strava().listRecentClubActivities(TestUtils.CLUB_VALID_ID, paging));
	}

	/**
	 * Check that no activity flagged as private is returned
	 */
	@Test
	public void testListRecentClubActivities_checkPrivacy() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaActivity> activities = strava().listRecentClubActivities(TestUtils.CLUB_PUBLIC_MEMBER_ID);
			for (final StravaActivity activity : activities) {
				if (activity.getPrivateActivity().equals(Boolean.TRUE)) {
					fail("List recent club activities returned an activity flagged as private!");
				}
			}
		} );
	}

	// 2. Invalid club
	@Test
	public void testListRecentClubActivities_invalidClub() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaActivity> activities = strava().listRecentClubActivities(TestUtils.CLUB_INVALID_ID);

			assertNotNull(activities);
			assertEquals(0, activities.size());
			validateList(activities);
		} );
	}

	// 4. StravaClub with > 200 activities (according to Strava, should only
	// return a max of 200 results)
	@Test
	public void testListRecentClubActivities_moreThan200() throws Exception {
		RateLimitedTestRunner.run(() -> {
			List<StravaActivity> activities = strava().listRecentClubActivities(TestUtils.CLUB_VALID_ID,
					new Paging(2, 200));
			assertNotNull(activities);
			assertEquals(0, activities.size());
			activities = strava().listRecentClubActivities(TestUtils.CLUB_VALID_ID, new Paging(1, 200));
			assertNotNull(activities);
			assertFalse(0 == activities.size());
			validateList(activities);
		} );
	}

	// 3. StravaClub the current authenticated athlete is NOT a member of
	// (according to Strava should return 0 results)
	@Test
	public void testListRecentClubActivities_nonMember() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaActivity> activities = strava()
					.listRecentClubActivities(TestUtils.CLUB_PUBLIC_NON_MEMBER_ID);

			assertTrue(activities.isEmpty());
		} );
	}

	// Test cases
	// 1. Valid club
	@Test
	public void testListRecentClubActivities_validClub() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaActivity> activities = strava().listRecentClubActivities(TestUtils.CLUB_VALID_ID);

			assertNotNull(activities);
		} );
	}

	// This is a workaround for issue javastrava-api #18
	// (https://github.com/danshannon/javastravav3api/issues/18)
	@Override
	@Test
	public void testPageNumberAndSize() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaActivity> bothPages = callback().getList(new Paging(1, 2));
			assertNotNull(bothPages);
			assertEquals(2, bothPages.size());
			validateList(bothPages);
			final List<StravaActivity> firstPage = callback().getList(new Paging(1, 1));
			assertNotNull(firstPage);
			assertEquals(1, firstPage.size());
			validateList(firstPage);
			final List<StravaActivity> secondPage = callback().getList(new Paging(2, 1));
			assertNotNull(secondPage);
			assertEquals(1, secondPage.size());
			validateList(secondPage);

			// // The first entry in bothPages should be the same as the first
			// entry in firstPage
			// assertEquals(bothPages.get(0),firstPage.get(0));
			//
			// // The second entry in bothPages should be the same as the first
			// entry in secondPage
			// assertEquals(bothPages.get(1),secondPage.get(0));
		} );
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
