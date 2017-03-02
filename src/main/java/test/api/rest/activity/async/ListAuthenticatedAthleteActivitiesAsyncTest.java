package test.api.rest.activity.async;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.Month;

import org.junit.Test;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.service.exception.UnauthorizedException;
import javastrava.util.StravaDateUtils;
import test.api.model.StravaActivityTest;
import test.api.rest.TestListArrayCallback;
import test.api.rest.activity.ListAuthenticatedAthleteActivitiesTest;
import test.api.rest.util.ArrayCallback;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class ListAuthenticatedAthleteActivitiesAsyncTest extends ListAuthenticatedAthleteActivitiesTest {
	/**
	 * @see test.api.rest.activity.ListAuthenticatedAthleteActivitiesTest#pagingCallback()
	 */
	@Override
	protected ArrayCallback<StravaActivity> pagingCallback() {
		return paging -> api().listAuthenticatedAthleteActivitiesAsync(null, null, paging.getPage(), paging.getPageSize()).get();
	}

	/**
	 * @see test.api.rest.activity.ListAuthenticatedAthleteActivitiesTest#listCallback()
	 */
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
				assertEquals(TestUtils.ATHLETE_AUTHENTICATED_ID, activity.getAthlete().getId());
				StravaActivityTest.validateActivity(activity);
			}
		});
	}

	/**
	 * <p>
	 * Test listing of {@link StravaActivity activities} before a given time (i.e. the before parameter, tested in isolation)
	 * </p>
	 *
	 * @throws Exception
	 *
	 * @throws UnauthorizedException
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
				assertEquals(TestUtils.ATHLETE_AUTHENTICATED_ID, activity.getAthlete().getId());
				StravaActivityTest.validateActivity(activity);
			}
		});
	}

	/**
	 * <p>
	 * Test listing of {@link StravaActivity activities} between two given times (i.e. before and after parameters in combination)
	 * </p>
	 *
	 * @throws Exception
	 *
	 * @throws UnauthorizedException
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
				assertEquals(TestUtils.ATHLETE_AUTHENTICATED_ID, activity.getAthlete().getId());
				StravaActivityTest.validateActivity(activity);
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
	 *
	 * @throws UnauthorizedException
	 */
	@Override
	public void testListAuthenticatedAthleteActivities_beforeAfterInvalidCombination() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final LocalDateTime before = LocalDateTime.of(2014, Month.JANUARY, 1, 0, 0);
			final LocalDateTime after = LocalDateTime.of(2015, Month.JANUARY, 1, 0, 0);

			final StravaActivity[] activities = api().listAuthenticatedAthleteActivitiesAsync(
					StravaDateUtils.secondsSinceUnixEpoch(before), StravaDateUtils.secondsSinceUnixEpoch(after), null, null).get();
			assertNotNull("Returned null collection of activities", activities);
			assertEquals(0, activities.length);
		});
	}

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
				assertEquals(TestUtils.ATHLETE_AUTHENTICATED_ID, activity.getAthlete().getId());
				StravaActivityTest.validateActivity(activity);
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
	 *
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Override
	@Test
	public void testListAuthenticatedAthleteActivities_default() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaActivity[] activities = api().listAuthenticatedAthleteActivitiesAsync(null, null, null, null).get();

			assertNotNull("Authenticated athlete's activities returned as null", activities);
			assertNotEquals("No activities returned for the authenticated athlete", 0, activities.length);
			for (final StravaActivity activity : activities) {
				assertNotEquals(Boolean.TRUE, activity.getPrivateActivity());
				assertEquals(TestUtils.ATHLETE_AUTHENTICATED_ID, activity.getAthlete().getId());
				StravaActivityTest.validateActivity(activity);
			}
		});
	}
}
