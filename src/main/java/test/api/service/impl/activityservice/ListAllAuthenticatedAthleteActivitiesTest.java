package test.api.service.impl.activityservice;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.service.ActivityService;
import javastrava.api.v3.service.impl.ActivityServiceImpl;

import org.junit.Test;

import test.api.model.StravaActivityTest;
import test.utils.TestUtils;

public class ListAllAuthenticatedAthleteActivitiesTest {
	@Test
	// TODO Other test cases (before, after, both, invalid)
	public void testListAllAuthenticatedAthleteActivities() {
		final List<StravaActivity> activities = service().listAllAuthenticatedAthleteActivities();
		assertNotNull(activities);
		for (final StravaActivity activity : activities) {
			StravaActivityTest.validateActivity(activity, activity.getId(), activity.getResourceState());
		}
	}

	private ActivityService service() {
		return ActivityServiceImpl.instance(TestUtils.getValidToken());

	}
}
