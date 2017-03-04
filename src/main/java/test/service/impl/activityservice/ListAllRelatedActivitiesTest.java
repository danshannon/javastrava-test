package test.service.impl.activityservice;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import javastrava.api.v3.model.StravaActivity;
import test.api.model.StravaActivityTest;
import test.service.standardtests.ListMethodTest;
import test.service.standardtests.callbacks.ListCallback;
import test.service.standardtests.data.ActivityDataUtils;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * <p>
 * Specific tests for list all related activities methods
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListAllRelatedActivitiesTest extends ListMethodTest<StravaActivity, Long> {
	/**
	 * <p>
	 * Test that it works for activities belonging to someone other than the authenticated user
	 * </p>
	 *
	 * @throws Exception
	 *             if test fails in some unexpected way
	 */
	@Test
	public void testListAllRelatedActivities_otherUserActivity() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaActivity> activities = lister().getList(TestUtils.strava(),
					ActivityDataUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER);
			assertNotNull(activities);
			for (final StravaActivity activity : activities) {
				StravaActivityTest.validate(activity);
			}

		});
	}

	@Override
	protected ListCallback<StravaActivity, Long> lister() {
		return ((strava, id) -> strava.listAllRelatedActivities(id));
	}

	@Override
	protected Long idPrivate() {
		return ActivityDataUtils.ACTIVITY_PRIVATE_WITH_RELATED_ACTIVITIES;
	}

	@Override
	protected Long idPrivateBelongsToOtherUser() {
		return ActivityDataUtils.ACTIVITY_PRIVATE_OTHER_USER;
	}

	@Override
	protected Long idValidWithEntries() {
		return ActivityDataUtils.ACTIVITY_WITH_RELATED_ACTIVITIES;
	}

	@Override
	protected Long idValidWithoutEntries() {
		return ActivityDataUtils.ACTIVITY_WITHOUT_RELATED_ACTIVITIES;
	}

	@Override
	protected Long idInvalid() {
		return ActivityDataUtils.ACTIVITY_INVALID;
	}

	@Override
	protected void validate(StravaActivity object) {
		StravaActivityTest.validate(object);

	}

}
