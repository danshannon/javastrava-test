package test.api.service.impl.activityservice;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import javastrava.api.v3.model.StravaActivity;

import org.junit.Test;

import test.api.model.StravaActivityTest;
import test.api.service.StravaTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestCallback;
import test.utils.TestUtils;

public class ListAllRelatedActivitiesTest extends StravaTest {
	@Test
	public void testListAllRelatedActivities_validActivity() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				final List<StravaActivity> activities = strava().listAllRelatedActivities(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER);
				assertNotNull(activities);
				for (final StravaActivity activity : activities) {
					StravaActivityTest.validateActivity(activity);
				}
			}
		});
	}

	@Test
	public void testListAllRelatedActivities_invalidActivity() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				final List<StravaActivity> activities = strava().listAllRelatedActivities(TestUtils.ACTIVITY_INVALID);
				assertNull(activities);
			}
		});
	}

	@Test
	public void testListAllRelatedActivities_otherUserActivity() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				final List<StravaActivity> activities = strava().listAllRelatedActivities(TestUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER);
				assertNotNull(activities);
				for (final StravaActivity activity : activities) {
					StravaActivityTest.validateActivity(activity);
				}

			}
		});
	}

}
