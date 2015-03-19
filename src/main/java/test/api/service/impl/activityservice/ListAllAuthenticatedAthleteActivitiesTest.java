package test.api.service.impl.activityservice;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import javastrava.api.v3.model.StravaActivity;

import org.junit.Test;

import test.api.model.StravaActivityTest;
import test.api.service.StravaTest;

public class ListAllAuthenticatedAthleteActivitiesTest extends StravaTest {
	@Test
	// TODO Other test cases (before, after, both, invalid)
	public void testListAllAuthenticatedAthleteActivities() {
		final List<StravaActivity> activities = service().listAllAuthenticatedAthleteActivities();
		assertNotNull(activities);
		for (final StravaActivity activity : activities) {
			StravaActivityTest.validateActivity(activity, activity.getId(), activity.getResourceState());
		}
	}

}
