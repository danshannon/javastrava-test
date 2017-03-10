package test.service.impl.activityservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import javastrava.api.v3.model.StravaActivity;
import javastrava.util.Paging;
import test.api.model.StravaActivityTest;
import test.issues.strava.Issue18;
import test.service.standardtests.PagingListMethodTest;
import test.service.standardtests.callbacks.ListCallback;
import test.service.standardtests.callbacks.PagingListCallback;
import test.service.standardtests.data.AthleteDataUtils;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * <p>
 * Specific tests for list friends activities methods
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListFriendsActivitiesTest extends PagingListMethodTest<StravaActivity, Integer> {
	@Override
	protected Integer idInvalid() {
		return null;
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
		return AthleteDataUtils.ATHLETE_AUTHENTICATED_ID;
	}

	@Override
	protected Integer idValidWithoutEntries() {
		return null;
	}

	@Override
	protected ListCallback<StravaActivity, Integer> lister() {
		return ((strava, id) -> strava.listFriendsActivities());
	}

	@Override
	protected PagingListCallback<StravaActivity, Integer> pagingLister() {
		return ((strava, paging, id) -> strava.listFriendsActivities(paging));
	}

	@SuppressWarnings("boxing")
	@Override
	@Test
	// TODO This is only here as a TEST workaround for issue #18 (https://github.com/danshannon/javastravav3api/issues/18). When the
	// issue is fixed, remove this
	// override altogether!
	public void testPageNumberAndSize() throws Exception {
		if (new Issue18().isIssue()) {
			RateLimitedTestRunner.run(() -> {
				final List<StravaActivity> bothPages = pagingLister().getList(TestUtils.strava(), new Paging(1, 3), AthleteDataUtils.ATHLETE_AUTHENTICATED_ID);
				assertNotNull(bothPages);
				assertEquals(3, bothPages.size());
				validateList(bothPages);
				final List<StravaActivity> firstPage = pagingLister().getList(TestUtils.strava(), new Paging(1, 1), AthleteDataUtils.ATHLETE_AUTHENTICATED_ID);
				assertNotNull(firstPage);
				assertEquals(1, firstPage.size());
				validateList(firstPage);
				final List<StravaActivity> secondPage = pagingLister().getList(TestUtils.strava(), new Paging(2, 1), AthleteDataUtils.ATHLETE_AUTHENTICATED_ID);
				assertNotNull(secondPage);
				assertEquals(1, secondPage.size());
				validateList(secondPage);
				final List<StravaActivity> thirdPage = pagingLister().getList(TestUtils.strava(), new Paging(3, 1), AthleteDataUtils.ATHLETE_AUTHENTICATED_ID);
				assertNotNull(thirdPage);
				assertEquals(1, thirdPage.size());

				// assertTrue(firstPage.get(0).getStartDate().after(secondPage.get(0).getStartDate()));
				// assertTrue(secondPage.get(0).getStartDate().after(thirdPage.get(0).getStartDate()));

				// The first entry in bothPages should be the same as the first entry in firstPage
				// assertEquals(bothPages.get(0),firstPage.get(0));

				// The second entry in bothPages should be the same as the first entry in secondPage
				// assertEquals(bothPages.get(1),secondPage.get(0));
			});
		} else {
			super.testPageNumberAndSize();
		}
	}

	@Override
	protected void validate(final StravaActivity activity) {
		StravaActivityTest.validate(activity);
	}

}
