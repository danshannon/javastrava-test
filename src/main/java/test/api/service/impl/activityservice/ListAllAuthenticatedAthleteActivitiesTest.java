package test.api.service.impl.activityservice;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import javastrava.api.v3.model.StravaActivity;

import org.junit.Test;

import test.api.model.StravaActivityTest;
import test.api.service.StravaTest;
import test.utils.RateLimitedTestRunner;

public class ListAllAuthenticatedAthleteActivitiesTest extends StravaTest {
	@Test
	public void testListAllAuthenticatedAthleteActivities() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaActivity> activities = strava().listAllAuthenticatedAthleteActivities();
			assertNotNull(activities);
			for (final StravaActivity activity : activities) {
				StravaActivityTest.validateActivity(activity, activity.getId(), activity.getResourceState());
			}
		});
	}

	@Test
	public void testListAllAuthenticatedAthleteActivities_after() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final LocalDateTime after = LocalDateTime.of(2015, Month.JANUARY, 1, 0, 0);
			final List<StravaActivity> activities = strava().listAllAuthenticatedAthleteActivities(null, after);
			for (final StravaActivity activity : activities) {
				StravaActivityTest.validateActivity(activity);
				assertTrue(activity.getStartDateLocal().isAfter(after));
			}
		});
	}

	@Test
	public void testListAllAuthenticatedAthleteActivities_before() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final LocalDateTime before = LocalDateTime.of(2010, Month.JANUARY, 1, 0, 0);
			final List<StravaActivity> activities = strava().listAllAuthenticatedAthleteActivities(before, null);
			for (final StravaActivity activity : activities) {
				StravaActivityTest.validateActivity(activity);
				assertTrue(activity.getStartDateLocal().isBefore(before));
			}
		});
	}

	@Test
	public void testListAllAuthenticatedAthleteActivities_invalidPeriod() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final LocalDateTime before = LocalDateTime.of(2010, Month.JANUARY, 1, 0, 0);
			final LocalDateTime after = LocalDateTime.of(2010, Month.JULY, 1, 0, 0);
			final List<StravaActivity> activities = strava().listAllAuthenticatedAthleteActivities(before, after);
			assertNotNull(activities);
			assertTrue(activities.isEmpty());
		});
	}

	@Test
	public void testListAllAuthenticatedAthleteActivities_period() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final LocalDateTime before = LocalDateTime.of(2010, Month.JANUARY, 1, 0, 0);
			final LocalDateTime after = LocalDateTime.of(2009, Month.JULY, 1, 0, 0);
			final List<StravaActivity> activities = strava().listAllAuthenticatedAthleteActivities(before, after);
			for (final StravaActivity activity : activities) {
				StravaActivityTest.validateActivity(activity);
				assertTrue(activity.getStartDateLocal().isBefore(before));
				assertTrue(activity.getStartDateLocal().isAfter(after));
			}
		});
	}

}
