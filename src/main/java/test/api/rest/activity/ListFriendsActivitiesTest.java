package test.api.rest.activity;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.time.ZonedDateTime;
import java.util.Arrays;

import org.junit.Test;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.service.exception.UnauthorizedException;
import javastrava.config.StravaConfig;
import test.api.model.StravaActivityTest;
import test.api.rest.APIPagingListTest;
import test.api.rest.TestListArrayCallback;
import test.api.rest.util.ArrayCallback;
import test.issues.strava.Issue18;
import test.issues.strava.Issue96;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class ListFriendsActivitiesTest extends APIPagingListTest<StravaActivity, Integer> {
	@Override
	protected ArrayCallback<StravaActivity> pagingCallback() {
		return (paging -> api().listFriendsActivities(paging.getPage(), paging.getPageSize()));
	}

	@Override
	protected TestListArrayCallback<StravaActivity, Integer> listCallback() {
		return ((api, id) -> api.listFriendsActivities(null, null));
	}

	/**
	 * @see test.api.rest.APIListTest#invalidId()
	 */
	@Override
	protected Integer invalidId() {
		return null;
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

	@Test
	public void testListFriendsActivities_checkPrivateFlagAuthenticatedUser() throws Exception {
		RateLimitedTestRunner.run(() -> {
			if (new Issue96().isIssue()) {
				return;
			}
			final StravaActivity[] activities = api().listFriendsActivities(1, StravaConfig.MAX_PAGE_SIZE);
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
			final StravaActivity[] activities = api().listFriendsActivities(1, StravaConfig.MAX_PAGE_SIZE);
			for (final StravaActivity activity : activities) {
				if (!(activity.getAthlete().getId().equals(TestUtils.ATHLETE_AUTHENTICATED_ID)) && activity.getPrivateActivity()) {
					fail("Returned private activities belonging to other users!");
				}
			}
		});
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
			final StravaActivity[] activities = api().listFriendsActivities(1, StravaConfig.MAX_PAGE_SIZE);

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
		});
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

	@Override
	protected void validate(final StravaActivity activity) {
		StravaActivityTest.validateActivity(activity);

	}

	/**
	 * @see test.api.rest.APIListTest#validateArray(java.lang.Object[])
	 */
	@Override
	protected void validateArray(final StravaActivity[] athletes) {
		StravaActivityTest.validateList(Arrays.asList(athletes));
	}

	/**
	 * @see test.api.rest.APIListTest#validId()
	 */
	@Override
	protected Integer validId() {
		return TestUtils.ATHLETE_AUTHENTICATED_ID;
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
