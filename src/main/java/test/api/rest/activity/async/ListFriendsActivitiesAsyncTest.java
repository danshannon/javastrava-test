package test.api.rest.activity.async;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.time.ZonedDateTime;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.service.exception.UnauthorizedException;
import javastrava.config.StravaConfig;

import org.junit.Test;

import test.api.model.StravaActivityTest;
import test.api.rest.util.ArrayCallback;
import test.api.rest.util.PagingArrayMethodAsyncTest;
import test.issues.strava.Issue18;
import test.issues.strava.Issue96;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class ListFriendsActivitiesAsyncTest extends PagingArrayMethodAsyncTest<StravaActivity, Integer> {

	@Override
	protected ArrayCallback<StravaActivity> callback() {
		return (paging -> api().listFriendsActivities(paging.getPage(), paging.getPageSize()).get());
	}

	/**
	 * <p>
	 * List latest {@link StravaActivity activities} for {@link StravaAthlete athletes} the currently authorised user is following
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
	@Test
	public void testListFriendsActivities_hasFriends() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaActivity[] activities = api().listFriendsActivities(1, StravaConfig.MAX_PAGE_SIZE).get();

			assertNotNull("Returned null array for latest friends' activities", activities);

			// Check that the activities are returned in descending order of start date
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
			});
	}

	@Test
	public void testListFriendsActivities_checkPrivateFlagAuthenticatedUser() throws Exception {
		RateLimitedTestRunner.run(() -> {
			if (new Issue96().isIssue()) {
				return;
			}
			final StravaActivity[] activities = api().listFriendsActivities(1, StravaConfig.MAX_PAGE_SIZE).get();
			for (final StravaActivity activity : activities) {
				if (activity.getAthlete().getId().equals(TestUtils.ATHLETE_AUTHENTICATED_ID) && activity.getPrivateActivity()) {
					fail("Returned private activities belonging to the authenticated user");
				}
			}
		});
	}

	@Test
	public void testListFriendsActivities_checkPrivateFlagOtherUsers() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaActivity[] activities = api().listFriendsActivities(1, StravaConfig.MAX_PAGE_SIZE).get();
			for (final StravaActivity activity : activities) {
				if (!(activity.getAthlete().getId().equals(TestUtils.ATHLETE_AUTHENTICATED_ID)) && activity.getPrivateActivity()) {
					fail("Returned private activities belonging to other users!");
				}
			}
		});
	}

	@Override
	protected void validate(final StravaActivity activity) {
		validate(activity, activity.getId(), activity.getResourceState());

	}

	@Override
	protected void validate(final StravaActivity activity, final Integer id, final StravaResourceState state) {
		StravaActivityTest.validateActivity(activity, id, state);

	}

	@Override
	public void testPageNumberAndSize() throws Exception {
		// TODO This is a workaround for issue javastravav3api#18
		// When the issue is fixed, remove this method altogether
		if (new Issue18().isIssue()) {
			return;
		}
		// End of workaround
		super.testPageNumberAndSize();
	}

}