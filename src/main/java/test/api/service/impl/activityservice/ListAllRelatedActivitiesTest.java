package test.api.service.impl.activityservice;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import javastrava.api.v3.model.StravaActivity;

import org.junit.Test;

import test.api.model.StravaActivityTest;
import test.api.service.StravaTest;
import test.utils.TestUtils;

public class ListAllRelatedActivitiesTest extends StravaTest {
	@Test
	public void testListAllRelatedActivities_validActivity() {
		List<StravaActivity> activities = service().listAllRelatedActivities(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER);
		assertNotNull(activities);
		for (StravaActivity activity : activities) {
			StravaActivityTest.validateActivity(activity);
		}
	}
	
	@Test
	public void testListAllRelatedActivities_invalidActivity() {
		List<StravaActivity> activities = service().listAllRelatedActivities(TestUtils.ACTIVITY_INVALID);
		assertNull(activities);
	}

	
	@Test
	public void testListAllRelatedActivities_otherUserActivity() {
		List<StravaActivity> activities = service().listAllRelatedActivities(TestUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER);
		assertNotNull(activities);
		for (StravaActivity activity : activities) {
			StravaActivityTest.validateActivity(activity);
		}
		
	}
	
}
