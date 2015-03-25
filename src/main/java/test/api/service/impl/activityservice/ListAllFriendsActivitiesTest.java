package test.api.service.impl.activityservice;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javastrava.api.v3.model.StravaActivity;

import org.junit.Test;

import test.api.model.StravaActivityTest;
import test.api.service.StravaTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestCallback;

public class ListAllFriendsActivitiesTest extends StravaTest {
	@Test
	public void testListAllFriendsActivities() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				final List<StravaActivity> activities = strava().listAllFriendsActivities();
				assertNotNull(activities);
				assertTrue(activities.size() <= 200);
				for (final StravaActivity activity : activities) {
					StravaActivityTest.validateActivity(activity);
				}
			}
		});
	}
}
