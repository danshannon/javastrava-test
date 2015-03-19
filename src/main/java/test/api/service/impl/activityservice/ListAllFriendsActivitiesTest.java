package test.api.service.impl.activityservice;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javastrava.api.v3.model.StravaActivity;

import org.junit.Test;

import test.api.model.StravaActivityTest;
import test.api.service.StravaTest;

public class ListAllFriendsActivitiesTest extends StravaTest {
	@Test
	public void testListAllFriendsActivities() {
		List<StravaActivity> activities = service().listAllFriendsActivities();
		assertNotNull(activities);
		assertTrue(activities.size() <= 200);
		for (StravaActivity activity : activities) {
			StravaActivityTest.validateActivity(activity);
		}
	}
	
}
