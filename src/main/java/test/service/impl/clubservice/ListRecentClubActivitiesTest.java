package test.service.impl.clubservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeFalse;

import java.util.List;

import org.junit.Test;

import javastrava.model.StravaActivity;
import javastrava.util.Paging;
import test.issues.strava.Issue166;
import test.service.standardtests.PagingListMethodTest;
import test.service.standardtests.callbacks.ListCallback;
import test.service.standardtests.callbacks.PagingListCallback;
import test.service.standardtests.data.ActivityDataUtils;
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
	protected Class<StravaActivity> classUnderTest() {
		return StravaActivity.class;

	}

	@Override
	protected Integer idInvalid() {
		return ClubDataUtils.CLUB_INVALID_ID;
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
	protected ListCallback<StravaActivity, Integer> lister() {
		return ((strava, id) -> strava.listRecentClubActivities(id));
	}

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
	@SuppressWarnings("boxing")
	@Test
	public void testListRecentClubActivities_moreThan200() throws Exception {
		RateLimitedTestRunner.run(() -> {
			List<StravaActivity> activities = TestUtils.strava().listRecentClubActivities(ClubDataUtils.CLUB_VALID_ID, new Paging(2, 200));
			assertNotNull(activities);
			assertEquals(0, activities.size());
			activities = TestUtils.strava().listRecentClubActivities(ClubDataUtils.CLUB_VALID_ID, new Paging(1, 200));
			assertNotNull(activities);
			assertFalse(0 == activities.size());
			validateList(activities);
		});
	}

	@SuppressWarnings("boxing")
	@Override
	@Test
	public void testPageNumberAndSize() throws Exception {
		// Don't bother if issue 166 is still current
		assumeFalse(Issue166.issue);

		RateLimitedTestRunner.run(() -> {
			final List<StravaActivity> bothPages = pagingLister().getList(TestUtils.strava(), new Paging(1, 2), ClubDataUtils.CLUB_VALID_ID);
			assertNotNull(bothPages);
			assertEquals("List recent club activities for club" + ClubDataUtils.CLUB_VALID_ID + ", page 1 of size 2, returned " + bothPages.size() + " entries!", 2, bothPages.size()); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			validateList(bothPages);
			final List<StravaActivity> firstPage = pagingLister().getList(TestUtils.strava(), new Paging(1, 1), ClubDataUtils.CLUB_VALID_ID);
			assertNotNull(firstPage);
			assertEquals(1, firstPage.size());
			validateList(firstPage);
			final List<StravaActivity> secondPage = pagingLister().getList(TestUtils.strava(), new Paging(2, 1), ClubDataUtils.CLUB_VALID_ID);
			assertNotNull(secondPage);
			assertEquals(1, secondPage.size());
			validateList(secondPage);

			// The first entry in bothPages should be the same as the first entry in firstPage
			assertEquals(bothPages.get(0), firstPage.get(0));

			// The second entry in bothPages should be the same as the first entry in secondPage
			assertEquals(bothPages.get(1), secondPage.get(0));
		});
	}

	@Override
	public void testPageSize() throws Exception {
		assumeFalse(Issue166.issue);

		super.testPageSize();
	}

	@Override
	public void testPagingIgnoreFirstN() throws Exception {
		assumeFalse(Issue166.issue);

		super.testPagingIgnoreFirstN();
	}

	@Override
	public void testPagingIgnoreLastN() throws Exception {
		assumeFalse(Issue166.issue);

		super.testPagingIgnoreLastN();
	}

	@Override
	protected void validate(final StravaActivity activity) {
		ActivityDataUtils.validate(activity);

	}
}
