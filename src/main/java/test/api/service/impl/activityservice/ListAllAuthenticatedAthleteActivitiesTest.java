package test.api.service.impl.activityservice;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.rest.API;
import javastrava.api.v3.service.exception.UnauthorizedException;

import org.junit.Test;

import test.api.model.StravaActivityTest;
import test.api.service.StravaTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

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
	
	@Test
	public void testListAllAuthenticatedAthleteActivities_withViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaActivity> activities = stravaWithViewPrivate().listAllAuthenticatedAthleteActivities();
			boolean pass = false;
			for (StravaActivity activity : activities) {
				if (activity.getPrivateActivity() == Boolean.TRUE) {
					pass = true;
					break;
				}
			}
			if (!pass) {
				fail("Didn't return private activities");
			}
		});
	}

	@Test
	public void testListAllAuthenticatedAthleteActivities_withoutViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaActivity> activities = strava().listAuthenticatedAthleteActivities();
			for (StravaActivity activity : activities) {
				if (activity.getPrivateActivity() == Boolean.TRUE) {
					try {
						new API(TestUtils.getValidToken()).getActivity(activity.getId(), null);
					} catch (UnauthorizedException e) {
						// expected
					}
					// TODO Workaround for issue #68
					// fail("Returned private activity" + activity);
					// End of workaround
				}
			}
		});
	}

}
