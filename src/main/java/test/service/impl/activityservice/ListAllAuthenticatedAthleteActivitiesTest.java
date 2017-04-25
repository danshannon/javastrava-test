package test.service.impl.activityservice;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import org.junit.Test;

import javastrava.model.StravaActivity;
import javastrava.model.reference.StravaResourceState;
import test.service.standardtests.ListMethodTest;
import test.service.standardtests.callbacks.ListCallback;
import test.service.standardtests.data.ActivityDataUtils;
import test.service.standardtests.data.AthleteDataUtils;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * <p>
 * Specific tests for listAllAuthenticatedAthleteActivities methods
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListAllAuthenticatedAthleteActivitiesTest extends ListMethodTest<StravaActivity, Integer> {
	@Override
	protected Class<StravaActivity> classUnderTest() {
		return StravaActivity.class;
	}

	@Override
	protected Integer idInvalid() {
		return null;
	}

	@Override
	protected Integer idPrivate() {
		return null;
	}

	@Override
	protected Integer idPrivateBelongsToOtherUser() {
		return null;
	}

	@Override
	protected Integer idValidWithEntries() {
		return AthleteDataUtils.ATHLETE_AUTHENTICATED_ID;
	}

	@Override
	protected Integer idValidWithoutEntries() {
		return null;
	}

	@Override
	protected ListCallback<StravaActivity, Integer> lister() {
		return ((strava, id) -> strava.listAllAuthenticatedAthleteActivities());
	}

	/**
	 * <p>
	 * Test ability to return activities after a specified date and time
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in some unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testListAllAuthenticatedAthleteActivities_after() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final LocalDateTime after = LocalDateTime.of(2016, Month.JANUARY, 1, 0, 0);
			final List<StravaActivity> activities = TestUtils.strava().listAllAuthenticatedAthleteActivities(null, after);
			for (final StravaActivity activity : activities) {
				ActivityDataUtils.validate(activity);
				if (activity.getResourceState() != StravaResourceState.PRIVATE) {
					assertTrue(activity.getStartDateLocal().isAfter(after));
				}
			}
		});
	}

	/**
	 * <p>
	 * Test ability to return activities before a specified date and time
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in some unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testListAllAuthenticatedAthleteActivities_before() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final LocalDateTime before = LocalDateTime.of(2016, Month.JANUARY, 1, 0, 0);
			final List<StravaActivity> activities = TestUtils.strava().listAllAuthenticatedAthleteActivities(before, null);
			for (final StravaActivity activity : activities) {
				ActivityDataUtils.validate(activity);
				if (activity.getResourceState() != StravaResourceState.PRIVATE) {
					assertTrue(activity.getStartDateLocal().isBefore(before));
				}
			}
		});
	}

	/**
	 * <p>
	 * Test ability to return activities within an invalid period of time
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in some unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testListAllAuthenticatedAthleteActivities_invalidPeriod() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final LocalDateTime before = LocalDateTime.of(2016, Month.JANUARY, 1, 0, 0);
			final LocalDateTime after = LocalDateTime.of(2016, Month.JULY, 1, 0, 0);
			final List<StravaActivity> activities = TestUtils.strava().listAllAuthenticatedAthleteActivities(before, after);
			assertNotNull(activities);
			assertTrue(activities.isEmpty());
		});
	}

	/**
	 * <p>
	 * Test ability to return activities within a specific period of time
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in some unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testListAllAuthenticatedAthleteActivities_period() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final LocalDateTime before = LocalDateTime.of(2016, Month.JANUARY, 1, 0, 0);
			final LocalDateTime after = LocalDateTime.of(2015, Month.JULY, 1, 0, 0);
			final List<StravaActivity> activities = TestUtils.strava().listAllAuthenticatedAthleteActivities(before, after);
			for (final StravaActivity activity : activities) {
				ActivityDataUtils.validate(activity);
				assertTrue(activity.getStartDateLocal().isBefore(before));
				assertTrue(activity.getStartDateLocal().isAfter(after));
			}
		});
	}

	/**
	 * <p>
	 * Test ability to return correct activities without view_private scope in the token (private activities are NOT allowed in this case)
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in some unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testListAllAuthenticatedAthleteActivities_withoutViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaActivity> activities = TestUtils.strava().listAuthenticatedAthleteActivities();
			for (final StravaActivity activity : activities) {
				if (activity.getPrivateActivity() == Boolean.TRUE) {
					fail("Returned private activities!"); //$NON-NLS-1$
				}
			}
		});
	}

	/**
	 * <p>
	 * Test ability to return correct activities with view_private scope in the token (private activities are allowed in this case)
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in some unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testListAllAuthenticatedAthleteActivities_withViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaActivity> activities = TestUtils.stravaWithViewPrivate().listAllAuthenticatedAthleteActivities();
			boolean pass = false;
			for (final StravaActivity activity : activities) {
				if (activity.getPrivateActivity() == Boolean.TRUE) {
					pass = true;
					break;
				}
			}
			if (!pass) {
				fail("Didn't return private activities"); //$NON-NLS-1$
			}
		});
	}

	@Override
	protected void validate(StravaActivity activity) {
		ActivityDataUtils.validate(activity);
	}

}
