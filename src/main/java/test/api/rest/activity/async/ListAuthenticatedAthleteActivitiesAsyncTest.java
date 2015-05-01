package test.api.rest.activity.async;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.Month;

<<<<<<< HEAD
=======
import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.service.exception.UnauthorizedException;
import javastrava.util.StravaDateUtils;

>>>>>>> branch 'master' of https://github.com/danshannon/javastrava-test.git
import org.junit.Test;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.service.exception.UnauthorizedException;
import javastrava.util.StravaDateUtils;
import test.api.model.StravaActivityTest;
import test.api.rest.activity.ListAuthenticatedAthleteActivitiesTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class ListAuthenticatedAthleteActivitiesAsyncTest extends ListAuthenticatedAthleteActivitiesTest {
	/**
<<<<<<< HEAD
	 *
	 */
	public ListAuthenticatedAthleteActivitiesAsyncTest() {
		this.listCallback = (api, id) -> api.listAuthenticatedAthleteActivitiesAsync(null, null, null, null).get();
		this.pagingCallback = paging -> api()
				.listAuthenticatedAthleteActivitiesAsync(null, null, paging.getPage(), paging.getPageSize()).get();
=======
	 * No-arguments constructor provides the required callbacks
	 */
	public ListAuthenticatedAthleteActivitiesAsyncTest() {
		this.listCallback = (api, id) -> api.listAuthenticatedAthleteActivitiesAsync(null, null, null, null).get();
		this.pagingCallback = paging -> api().listAuthenticatedAthleteActivitiesAsync(null, null, paging.getPage(), paging.getPageSize()).get();
>>>>>>> branch 'master' of https://github.com/danshannon/javastrava-test.git
	}

	/**
	 * <p>
	 * Test listing of {@link StravaActivity activities} after a given time
	 * (i.e. the after parameter, tested in isolation)
	 * </p>
	 *
	 * @throws Exception
	 *
	 * @throws UnauthorizedException
	 */
	@Override
<<<<<<< HEAD
=======
	@Test
>>>>>>> branch 'master' of https://github.com/danshannon/javastrava-test.git
	public void testListAuthenticatedAthleteActivities_afterActivity() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final LocalDateTime calendar = LocalDateTime.of(2015, Month.JANUARY, 1, 0, 0);

<<<<<<< HEAD
			final StravaActivity[] activities = api().listAuthenticatedAthleteActivitiesAsync(null,
					StravaDateUtils.secondsSinceUnixEpoch(calendar), null, null).get();
=======
			final StravaActivity[] activities = api()
					.listAuthenticatedAthleteActivitiesAsync(null, StravaDateUtils.secondsSinceUnixEpoch(calendar), null, null).get();
>>>>>>> branch 'master' of https://github.com/danshannon/javastrava-test.git
			for (final StravaActivity activity : activities) {
				assertNotEquals(Boolean.TRUE, activity.getPrivateActivity());
				assertTrue(activity.getStartDateLocal().isAfter(calendar));
				assertEquals(TestUtils.ATHLETE_AUTHENTICATED_ID, activity.getAthlete().getId());
				StravaActivityTest.validateActivity(activity);
			}
		} );
	}

	/**
	 * <p>
	 * Test listing of {@link StravaActivity activities} before a given time
	 * (i.e. the before parameter, tested in isolation)
	 * </p>
	 *
	 * @throws Exception
	 *
	 * @throws UnauthorizedException
	 */
	@Override
<<<<<<< HEAD
=======
	@Test
>>>>>>> branch 'master' of https://github.com/danshannon/javastrava-test.git
	public void testListAuthenticatedAthleteActivities_beforeActivity() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final LocalDateTime calendar = LocalDateTime.of(2015, Month.JANUARY, 1, 0, 0);

<<<<<<< HEAD
			final StravaActivity[] activities = api().listAuthenticatedAthleteActivitiesAsync(
					StravaDateUtils.secondsSinceUnixEpoch(calendar), null, null, null).get();
=======
			final StravaActivity[] activities = api()
					.listAuthenticatedAthleteActivitiesAsync(StravaDateUtils.secondsSinceUnixEpoch(calendar), null, null, null).get();
>>>>>>> branch 'master' of https://github.com/danshannon/javastrava-test.git
			for (final StravaActivity activity : activities) {
				assertNotEquals(Boolean.TRUE, activity.getPrivateActivity());
				assertTrue(activity.getStartDateLocal().isBefore(calendar));
				assertEquals(TestUtils.ATHLETE_AUTHENTICATED_ID, activity.getAthlete().getId());
				StravaActivityTest.validateActivity(activity);
			}
		} );
	}

	/**
	 * <p>
	 * Test listing of {@link StravaActivity activities} between two given times
	 * (i.e. before and after parameters in combination)
	 * </p>
	 *
	 * @throws Exception
	 *
	 * @throws UnauthorizedException
	 */
	@Override
<<<<<<< HEAD
=======
	@Test
>>>>>>> branch 'master' of https://github.com/danshannon/javastrava-test.git
	public void testListAuthenticatedAthleteActivities_beforeAfterCombination() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final LocalDateTime before = LocalDateTime.of(2015, Month.JANUARY, 1, 0, 0);
			final LocalDateTime after = LocalDateTime.of(2014, Month.JANUARY, 1, 0, 0);

<<<<<<< HEAD
			final StravaActivity[] activities = api()
					.listAuthenticatedAthleteActivitiesAsync(StravaDateUtils.secondsSinceUnixEpoch(before),
							StravaDateUtils.secondsSinceUnixEpoch(after), null, null)
					.get();
=======
			final StravaActivity[] activities = api().listAuthenticatedAthleteActivitiesAsync(StravaDateUtils.secondsSinceUnixEpoch(before),
					StravaDateUtils.secondsSinceUnixEpoch(after), null, null).get();
>>>>>>> branch 'master' of https://github.com/danshannon/javastrava-test.git
			for (final StravaActivity activity : activities) {
				assertNotEquals(Boolean.TRUE, activity.getPrivateActivity());
				assertTrue(activity.getStartDateLocal().isBefore(before));
				assertTrue(activity.getStartDateLocal().isAfter(after));
				assertEquals(TestUtils.ATHLETE_AUTHENTICATED_ID, activity.getAthlete().getId());
				StravaActivityTest.validateActivity(activity);
			}
		} );
	}

	/**
	 * <p>
	 * Test listing of {@link StravaActivity activities} between two given times
	 * (i.e. before and after parameters in combination) BUT WITH AN INVALID
	 * COMBINATION OF BEFORE AND AFTER
	 * </p>
	 *
	 * @throws Exception
	 *
	 * @throws UnauthorizedException
	 */
	@Override
<<<<<<< HEAD
=======
	@Test
>>>>>>> branch 'master' of https://github.com/danshannon/javastrava-test.git
	public void testListAuthenticatedAthleteActivities_beforeAfterInvalidCombination() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final LocalDateTime before = LocalDateTime.of(2014, Month.JANUARY, 1, 0, 0);
			final LocalDateTime after = LocalDateTime.of(2015, Month.JANUARY, 1, 0, 0);

<<<<<<< HEAD
			final StravaActivity[] activities = api()
					.listAuthenticatedAthleteActivitiesAsync(StravaDateUtils.secondsSinceUnixEpoch(before),
							StravaDateUtils.secondsSinceUnixEpoch(after), null, null)
					.get();
=======
			final StravaActivity[] activities = api().listAuthenticatedAthleteActivitiesAsync(StravaDateUtils.secondsSinceUnixEpoch(before),
					StravaDateUtils.secondsSinceUnixEpoch(after), null, null).get();
>>>>>>> branch 'master' of https://github.com/danshannon/javastrava-test.git
			assertNotNull("Returned null collection of activities", activities);
			assertEquals(0, activities.length);
		} );
	}

	@Override
	@Test
	public void testListAuthenticatedAthleteActivities_beforeAfterPaging() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final LocalDateTime before = LocalDateTime.of(2015, Month.JANUARY, 1, 0, 0);
			final LocalDateTime after = LocalDateTime.of(2014, Month.JANUARY, 1, 0, 0);

<<<<<<< HEAD
			final StravaActivity[] activities = api()
					.listAuthenticatedAthleteActivitiesAsync(StravaDateUtils.secondsSinceUnixEpoch(before),
							StravaDateUtils.secondsSinceUnixEpoch(after), 1, 1)
					.get();
=======
			final StravaActivity[] activities = api().listAuthenticatedAthleteActivitiesAsync(StravaDateUtils.secondsSinceUnixEpoch(before),
					StravaDateUtils.secondsSinceUnixEpoch(after), 1, 1).get();
>>>>>>> branch 'master' of https://github.com/danshannon/javastrava-test.git
			assertNotNull(activities);
			assertEquals(1, activities.length);
			for (final StravaActivity activity : activities) {
				assertNotEquals(Boolean.TRUE, activity.getPrivateActivity());
				assertTrue(activity.getStartDateLocal().isBefore(before));
				assertTrue(activity.getStartDateLocal().isAfter(after));
				assertEquals(TestUtils.ATHLETE_AUTHENTICATED_ID, activity.getAthlete().getId());
				StravaActivityTest.validateActivity(activity);
			}
		} );
	}

	/**
	 * <p>
	 * Default test to list {@link StravaActivity activities} for the currently
	 * authenticated athlete (i.e. the one who corresponds to the current token)
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
<<<<<<< HEAD
			final StravaActivity[] activities = api().listAuthenticatedAthleteActivitiesAsync(null, null, null, null)
					.get();
=======
			final StravaActivity[] activities = api().listAuthenticatedAthleteActivitiesAsync(null, null, null, null).get();
>>>>>>> branch 'master' of https://github.com/danshannon/javastrava-test.git

			assertNotNull("Authenticated athlete's activities returned as null", activities);
			assertNotEquals("No activities returned for the authenticated athlete", 0, activities.length);
			for (final StravaActivity activity : activities) {
				assertNotEquals(Boolean.TRUE, activity.getPrivateActivity());
				assertEquals(TestUtils.ATHLETE_AUTHENTICATED_ID, activity.getAthlete().getId());
				StravaActivityTest.validateActivity(activity);
			}
<<<<<<< HEAD
		} );
	}

=======
		});
	}
>>>>>>> branch 'master' of https://github.com/danshannon/javastrava-test.git
}
