package test.api.service.impl.retrofit.activityservices;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.service.ActivityServices;
import javastrava.api.v3.service.impl.retrofit.ActivityServicesImpl;

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

	private ActivityServices service() {
		return ActivityServicesImpl.implementation(TestUtils.getValidToken());

	}
}
