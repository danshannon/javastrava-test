package test.api.rest.activity;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.time.ZonedDateTime;

import org.junit.Test;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.rest.API;
import javastrava.config.StravaConfig;
import test.api.model.StravaActivityTest;
import test.api.rest.APIPagingListTest;
import test.api.rest.callback.APIListCallback;
import test.api.rest.util.ArrayCallback;
import test.issues.strava.Issue18;
import test.service.standardtests.data.AthleteDataUtils;
import test.utils.RateLimitedTestRunner;

/**
 * <p>
 * Specific tests for {@link API#listFriendsActivities(Integer, Integer)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListFriendsActivitiesTest extends APIPagingListTest<StravaActivity, Integer> {
	@Override
	protected ArrayCallback<StravaActivity> pagingCallback() {
		return (paging -> api().listFriendsActivities(paging.getPage(), paging.getPageSize()));
	}

	@Override
	protected APIListCallback<StravaActivity, Integer> listCallback() {
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

	/**
	 * <p>
	 * Check that activities flagged as private by the authenticated user aren't returned by default
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails for unexpected reasons
	 */
	@SuppressWarnings({ "static-method", "boxing" })
	@Test
	public void testListFriendsActivities_checkPrivateFlagAuthenticatedUser() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaActivity[] activities = api().listFriendsActivities(1, StravaConfig.MAX_PAGE_SIZE);
			for (final StravaActivity activity : activities) {
				if (activity.getAthlete().getId().equals(AthleteDataUtils.ATHLETE_AUTHENTICATED_ID) && activity.getPrivateActivity().booleanValue()) {
					fail("Returned private activities belonging to the authenticated user"); //$NON-NLS-1$
				}
			}
		});
	}

	/**
	 * <p>
	 * Check that activities flagged as private by other users aren't returned by default
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails for unexpected reasons
	 */
	@SuppressWarnings({ "boxing", "static-method" })
	@Test
	public void testListFriendsActivities_checkPrivateFlagOtherUsers() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaActivity[] activities = api().listFriendsActivities(1, StravaConfig.MAX_PAGE_SIZE);
			for (final StravaActivity activity : activities) {
				if (!(activity.getAthlete().getId().equals(AthleteDataUtils.ATHLETE_AUTHENTICATED_ID)) && activity.getPrivateActivity().booleanValue()) {
					fail("Returned private activities belonging to other users!"); //$NON-NLS-1$
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
	 *             if the test fails for unexpected reasons
	 */
	@SuppressWarnings({ "static-method", "boxing" })
	@Test
	public void testListFriendsActivities_hasFriends() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaActivity[] activities = api().listFriendsActivities(1, StravaConfig.MAX_PAGE_SIZE);

			assertNotNull("Returned null array for latest friends' activities", activities); //$NON-NLS-1$

			// Check that the activities are returned in descending order of
			// start date
			ZonedDateTime lastStartDate = null;
			for (final StravaActivity activity : activities) {
				if (lastStartDate == null) {
					lastStartDate = activity.getStartDate();
				} else {
					if (activity.getStartDate().isAfter(lastStartDate)) {
						fail("Activities not returned in descending start date order"); //$NON-NLS-1$
					}
				}
				StravaActivityTest.validate(activity);
			}
		});
	}

	@Override
	public void testPageNumberAndSize() throws Exception {
		if (new Issue18().isIssue()) {
			return;
		}
		super.testPageNumberAndSize();
	}

	@Override
	protected void validate(final StravaActivity activity) {
		StravaActivityTest.validate(activity);

	}

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
		return AthleteDataUtils.ATHLETE_AUTHENTICATED_ID;
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
