package test.service.impl.clubservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import javastrava.api.v3.model.StravaActivity;
import javastrava.util.Paging;
import test.api.model.StravaActivityTest;
import test.issues.strava.Issue18;
import test.service.standardtests.PagingListMethodTest;
import test.service.standardtests.callbacks.ListCallback;
import test.service.standardtests.callbacks.PagingListCallback;
import test.service.standardtests.data.ClubDataUtils;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * <p>
 * Specific tests for listRecentClubActivities methods
 * </p>
 * 
 * @author Dan Shannon
 *
 */
public class ListRecentClubActivitiesTest extends PagingListMethodTest<StravaActivity, Integer> {
	@Override
	protected PagingListCallback<StravaActivity, Integer> pagingLister() {
		return ((strava, paging, id) -> strava.listRecentClubActivities(id, paging));
	}

	/**
	 * <p>
	 * Check that no activity flagged as private is returned
	 * </p>
	 *
	 * @throws Exception
	 *             if test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testListRecentClubActivities_checkPrivacy() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaActivity> activities = TestUtils.strava().listRecentClubActivities(ClubDataUtils.CLUB_PUBLIC_MEMBER_ID);
			for (final StravaActivity activity : activities) {
				if (activity.getPrivateActivity().equals(Boolean.TRUE)) {
					fail("List recent club activities returned an activity flagged as private!"); //$NON-NLS-1$
				}
			}
		});
	}

	/**
	 * <p>
	 * Check for StravaClub with > 200 activities (according to Strava, should only return a max of 200 results)
	 * </p>
	 *
	 * @throws Exception
	 *             if test fails in an unexpected way
	 */
	@Test
	public void testListRecentClubActivities_moreThan200() throws Exception {
		RateLimitedTestRunner.run(() -> {
			List<StravaActivity> activities = TestUtils.strava().listRecentClubActivities(ClubDataUtils.CLUB_VALID_ID,
					new Paging(2, 200));
			assertNotNull(activities);
			assertEquals(0, activities.size());
			activities = TestUtils.strava().listRecentClubActivities(ClubDataUtils.CLUB_VALID_ID, new Paging(1, 200));
			assertNotNull(activities);
			assertFalse(0 == activities.size());
			validateList(activities);
		});
	}

	/**
	 * <p>
	 * StravaClub the current authenticated athlete is NOT a member of (according to Strava should return 0 results)
	 * </p>
	 *
	 * @throws Exception
	 *             if test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testListRecentClubActivities_nonMember() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaActivity> activities = TestUtils.strava()
					.listRecentClubActivities(ClubDataUtils.CLUB_PUBLIC_NON_MEMBER_ID);

			assertTrue(activities.isEmpty());
		});
	}

	@Override
	@Test
	public void testPageNumberAndSize() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaActivity> bothPages = pagingLister().getList(TestUtils.strava(), new Paging(1, 2),
					ClubDataUtils.CLUB_VALID_ID);
			assertNotNull(bothPages);
			assertEquals(2, bothPages.size());
			validateList(bothPages);
			final List<StravaActivity> firstPage = pagingLister().getList(TestUtils.strava(), new Paging(1, 1),
					ClubDataUtils.CLUB_VALID_ID);
			assertNotNull(firstPage);
			assertEquals(1, firstPage.size());
			validateList(firstPage);
			final List<StravaActivity> secondPage = pagingLister().getList(TestUtils.strava(), new Paging(2, 1),
					ClubDataUtils.CLUB_VALID_ID);
			assertNotNull(secondPage);
			assertEquals(1, secondPage.size());
			validateList(secondPage);

			// This is a workaround for issue javastrava-api #18
			// (https://github.com/danshannon/javastravav3api/issues/18)
			if (!new Issue18().isIssue()) {
				// The first entry in bothPages should be the same as the first entry in firstPage
				assertEquals(bothPages.get(0), firstPage.get(0));

				// The second entry in bothPages should be the same as the first entry in secondPage
				assertEquals(bothPages.get(1), secondPage.get(0));
			}
		});
	}

	@Override
	protected void validate(final StravaActivity activity) {
		StravaActivityTest.validate(activity);

	}

	@Override
	protected ListCallback<StravaActivity, Integer> lister() {
		return ((strava, id) -> strava.listRecentClubActivities(id));
	}

	@Override
	protected Integer idPrivate() {
		return null;
	}

	@Override
	protected Integer idPrivateBelongsToOtherUser() {
		return null;
	}

	@Override
	protected Integer idValidWithEntries() {
		return ClubDataUtils.CLUB_VALID_ID;
	}

	@Override
	protected Integer idValidWithoutEntries() {
		return null;
	}

	@Override
	protected Integer idInvalid() {
		return ClubDataUtils.CLUB_INVALID_ID;
	}
}
