package test.api.rest.activity.async;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.time.ZonedDateTime;

import org.junit.Test;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.service.exception.UnauthorizedException;
import javastrava.config.StravaConfig;
import test.api.model.StravaActivityTest;
import test.api.rest.activity.ListFriendsActivitiesTest;
import test.issues.strava.Issue96;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class ListFriendsActivitiesAsyncTest extends ListFriendsActivitiesTest {
	/**
	 *
	 */
	public ListFriendsActivitiesAsyncTest() {
		this.listCallback = (api, id) -> api.listFriendsActivitiesAsync(null, null).get();
		this.pagingCallback = paging -> api().listFriendsActivitiesAsync(paging.getPage(), paging.getPageSize()).get();
	}

	@Override
	@Test
	public void testListFriendsActivities_checkPrivateFlagAuthenticatedUser() throws Exception {
		RateLimitedTestRunner.run(() -> {
			if (new Issue96().isIssue()) {
				return;
			}
			final StravaActivity[] activities = api().listFriendsActivitiesAsync(1, StravaConfig.MAX_PAGE_SIZE).get();
			for (final StravaActivity activity : activities) {
				if (activity.getAthlete().getId().equals(TestUtils.ATHLETE_AUTHENTICATED_ID)
						&& activity.getPrivateActivity()) {
					fail("Returned private activities belonging to the authenticated user");
				}
			}
		} );
	}

	@Override
	@Test
	public void testListFriendsActivities_checkPrivateFlagOtherUsers() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaActivity[] activities = api().listFriendsActivitiesAsync(1, StravaConfig.MAX_PAGE_SIZE).get();
			for (final StravaActivity activity : activities) {
				if (!(activity.getAthlete().getId().equals(TestUtils.ATHLETE_AUTHENTICATED_ID))
						&& activity.getPrivateActivity()) {
					fail("Returned private activities belonging to other users!");
				}
			}
		} );
	}

	/**
	 * <p>
	 * List latest {@link StravaActivity activities} for {@link StravaAthlete
	 * athletes} the currently authorised user is following
	 * </p>
	 *
	 * <p>
	 * Should return a list of rides in descending order of start date
	 * </p>
	 *
	 * @throws Exception
	 *
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Override
	@Test
	public void testListFriendsActivities_hasFriends() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaActivity[] activities = api().listFriendsActivitiesAsync(1, StravaConfig.MAX_PAGE_SIZE).get();

			assertNotNull("Returned null array for latest friends' activities", activities);

			// Check that the activities are returned in descending order of
			// start date
			ZonedDateTime lastStartDate = null;
			for (final StravaActivity activity : activities) {
				if (lastStartDate == null) {
					lastStartDate = activity.getStartDate();
				} else {
					if (activity.getStartDate().isAfter(lastStartDate)) {
						fail("Activities not returned in descending start date order");
					}
				}
				StravaActivityTest.validateActivity(activity);
			}
		} );
	}

}
