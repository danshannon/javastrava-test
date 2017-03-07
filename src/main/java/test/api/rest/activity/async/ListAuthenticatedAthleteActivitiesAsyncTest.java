package test.api.rest.activity.async;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.Month;

import org.junit.Test;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.rest.API;
import javastrava.util.StravaDateUtils;
import test.api.model.StravaActivityTest;
import test.api.rest.activity.ListAuthenticatedAthleteActivitiesTest;
import test.api.rest.callback.TestListArrayCallback;
import test.api.rest.util.ArrayCallback;
import test.service.standardtests.data.AthleteDataUtils;
import test.utils.RateLimitedTestRunner;

/**
 * <p>
 * Specific tests for {@link API#listAuthenticatedAthleteActivitiesAsync(Integer, Integer, Integer, Integer)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListAuthenticatedAthleteActivitiesAsyncTest extends ListAuthenticatedAthleteActivitiesTest {
	@Override
	protected ArrayCallback<StravaActivity> pagingCallback() {
		return paging -> api().listAuthenticatedAthleteActivitiesAsync(null, null, paging.getPage(), paging.getPageSize()).get();
	}

	@Override
	protected TestListArrayCallback<StravaActivity, Integer> listCallback() {
		return (api, id) -> api.listAuthenticatedAthleteActivitiesAsync(null, null, null, null).get();
	}

	/**
	 * <p>
	 * Test listing of {@link StravaActivity activities} after a given time (i.e. the after parameter, tested in isolation)
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Override
	public void testListAuthenticatedAthleteActivities_afterActivity() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final LocalDateTime calendar = LocalDateTime.of(2015, Month.JANUARY, 1, 0, 0);

			final StravaActivity[] activities = api()
					.listAuthenticatedAthleteActivitiesAsync(null, StravaDateUtils.secondsSinceUnixEpoch(calendar), null, null)
					.get();
			for (final StravaActivity activity : activities) {
				assertNotEquals(Boolean.TRUE, activity.getPrivateActivity());
				assertTrue(activity.getStartDateLocal().isAfter(calendar));
				assertEquals(AthleteDataUtils.ATHLETE_AUTHENTICATED_ID, activity.getAthlete().getId());
				StravaActivityTest.validate(activity);
			}
		});
	}

	/**
	 * <p>
	 * Test listing of {@link StravaActivity activities} before a given time (i.e. the before parameter, tested in isolation)
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Override
	public void testListAuthenticatedAthleteActivities_beforeActivity() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final LocalDateTime calendar = LocalDateTime.of(2015, Month.JANUARY, 1, 0, 0);

			final StravaActivity[] activities = api()
					.listAuthenticatedAthleteActivitiesAsync(StravaDateUtils.secondsSinceUnixEpoch(calendar), null, null, null)
					.get();
			for (final StravaActivity activity : activities) {
				assertNotEquals(Boolean.TRUE, activity.getPrivateActivity());
				assertTrue(activity.getStartDateLocal().isBefore(calendar));
				assertEquals(AthleteDataUtils.ATHLETE_AUTHENTICATED_ID, activity.getAthlete().getId());
				StravaActivityTest.validate(activity);
			}
		});
	}

	/**
	 * <p>
	 * Test listing of {@link StravaActivity activities} between two given times (i.e. before and after parameters in combination)
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Override
	public void testListAuthenticatedAthleteActivities_beforeAfterCombination() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final LocalDateTime before = LocalDateTime.of(2015, Month.JANUARY, 1, 0, 0);
			final LocalDateTime after = LocalDateTime.of(2014, Month.JANUARY, 1, 0, 0);

			final StravaActivity[] activities = api().listAuthenticatedAthleteActivitiesAsync(
					StravaDateUtils.secondsSinceUnixEpoch(before), StravaDateUtils.secondsSinceUnixEpoch(after), null, null).get();
			for (final StravaActivity activity : activities) {
				assertNotEquals(Boolean.TRUE, activity.getPrivateActivity());
				assertTrue(activity.getStartDateLocal().isBefore(before));
				assertTrue(activity.getStartDateLocal().isAfter(after));
				assertEquals(AthleteDataUtils.ATHLETE_AUTHENTICATED_ID, activity.getAthlete().getId());
				StravaActivityTest.validate(activity);
			}
		});
	}

	/**
	 * <p>
	 * Test listing of {@link StravaActivity activities} between two given times (i.e. before and after parameters in combination)
	 * BUT WITH AN INVALID COMBINATION OF BEFORE AND AFTER
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Override
	public void testListAuthenticatedAthleteActivities_beforeAfterInvalidCombination() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final LocalDateTime before = LocalDateTime.of(2014, Month.JANUARY, 1, 0, 0);
			final LocalDateTime after = LocalDateTime.of(2015, Month.JANUARY, 1, 0, 0);

			final StravaActivity[] activities = api().listAuthenticatedAthleteActivitiesAsync(
					StravaDateUtils.secondsSinceUnixEpoch(before), StravaDateUtils.secondsSinceUnixEpoch(after), null, null).get();
			assertNotNull("Returned null collection of activities", activities); //$NON-NLS-1$
			assertEquals(0, activities.length);
		});
	}

	@SuppressWarnings("boxing")
	@Override
	@Test
	public void testListAuthenticatedAthleteActivities_beforeAfterPaging() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final LocalDateTime before = LocalDateTime.of(2015, Month.JANUARY, 1, 0, 0);
			final LocalDateTime after = LocalDateTime.of(2014, Month.JANUARY, 1, 0, 0);

			final StravaActivity[] activities = api().listAuthenticatedAthleteActivitiesAsync(
					StravaDateUtils.secondsSinceUnixEpoch(before), StravaDateUtils.secondsSinceUnixEpoch(after), 1, 1).get();
			assertNotNull(activities);
			assertEquals(1, activities.length);
			for (final StravaActivity activity : activities) {
				assertNotEquals(Boolean.TRUE, activity.getPrivateActivity());
				assertTrue(activity.getStartDateLocal().isBefore(before));
				assertTrue(activity.getStartDateLocal().isAfter(after));
				assertEquals(AthleteDataUtils.ATHLETE_AUTHENTICATED_ID, activity.getAthlete().getId());
				StravaActivityTest.validate(activity);
			}
		});
	}

	/**
	 * <p>
	 * Default test to list {@link StravaActivity activities} for the currently authenticated athlete (i.e. the one who corresponds
	 * to the current token)
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Override
	@Test
	public void testListAuthenticatedAthleteActivities_default() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaActivity[] activities = api().listAuthenticatedAthleteActivitiesAsync(null, null, null, null).get();

			assertNotNull("Authenticated athlete's activities returned as null", activities); //$NON-NLS-1$
			assertNotEquals("No activities returned for the authenticated athlete", 0, activities.length); //$NON-NLS-1$
			for (final StravaActivity activity : activities) {
				assertNotEquals(Boolean.TRUE, activity.getPrivateActivity());
				assertEquals(AthleteDataUtils.ATHLETE_AUTHENTICATED_ID, activity.getAthlete().getId());
				StravaActivityTest.validate(activity);
			}
		});
	}
}
