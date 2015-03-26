package test.api.service.impl.activityservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.reference.StravaResourceState;

import org.junit.Test;

import test.api.model.StravaActivityTest;
import test.api.service.impl.util.ListCallback;
import test.api.service.impl.util.PagingListMethodTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class ListRelatedActivitiesTest extends PagingListMethodTest<StravaActivity, Integer> {
	@Override
	protected ListCallback<StravaActivity> callback() {
		return (paging -> strava().listRelatedActivities(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER, paging));
	}

	@Test
	public void testListRelatedActivities_invalidActivity() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaActivity> activities = strava().listRelatedActivities(TestUtils.ACTIVITY_INVALID);
			assertNull(activities);
		});
	}

	@Test
	public void testListRelatedActivities_privateActivity() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaActivity> activities = strava().listRelatedActivities(TestUtils.ACTIVITY_PRIVATE_OTHER_USER);
			assertNotNull(activities);
			assertEquals(0, activities.size());
		});
	}

	@Test
	public void testListRelatedActivities_validActivity() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaActivity> activities = strava().listRelatedActivities(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER);
			assertNotNull(activities);
			for (final StravaActivity activity : activities) {
				StravaActivityTest.validateActivity(activity, activity.getId(), activity.getResourceState());
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

}
