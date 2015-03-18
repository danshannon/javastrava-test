package test.api.service.impl.activityservice;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.service.ActivityService;
import javastrava.api.v3.service.impl.ActivityServiceImpl;

import org.junit.Test;

import test.api.model.StravaActivityTest;
import test.utils.TestUtils;

public class ListAllFriendsActivitiesTest {
	@Test
	public void testListAllFriendsActivities() {
		List<StravaActivity> activities = service().listAllFriendsActivities();
		assertNotNull(activities);
		assertTrue(activities.size() <= 200);
		for (StravaActivity activity : activities) {
			StravaActivityTest.validateActivity(activity);
		}
	}
	
	private ActivityService service() {
		return ActivityServiceImpl.instance(TestUtils.getValidToken());
	}

}
