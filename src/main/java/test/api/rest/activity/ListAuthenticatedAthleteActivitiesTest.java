package test.api.rest.activity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import org.junit.Test;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.service.exception.UnauthorizedException;
import javastrava.util.StravaDateUtils;
import test.api.model.StravaActivityTest;
import test.api.rest.APIPagingListTest;
import test.api.rest.TestListArrayCallback;
import test.api.rest.util.ArrayCallback;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class ListAuthenticatedAthleteActivitiesTest extends APIPagingListTest<StravaActivity, Integer> {
	@Override
	protected ArrayCallback<StravaActivity> pagingCallback() {
		return (paging -> api().listAuthenticatedAthleteActivities(null, null, paging.getPage(), paging.getPageSize()));
	}

	@Override
	protected TestListArrayCallback<StravaActivity, Integer> listCallback() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see test.api.rest.APIListTest#invalidId()
	 */
	@Override
	protected Integer invalidId() {
		// Not applicable, so return null to prevent test from being executed
		return null;
	}

	/**
	 * @see test.api.rest.APIListTest#privateId()
	 */
	@Override
	protected Integer privateId() {
		// Not applicable, so return null to prevent test from being executed
		return null;
	}

	/**
	 * @see test.api.rest.APIListTest#privateIdBelongsToOtherUser()
	 */
	@Override
	protected Integer privateIdBelongsToOtherUser() {
		// Not applicable, so return null to prevent test from being executed
		return null;
	}

	/**
	 * <p>
	 * Test listing of {@link StravaActivity activities} after a given time (i.e. the after parameter, tested in isolation)
	 * </p>
	 *
	 * @throws Exception
	 *
	 * @throws UnauthorizedException
	 */
	@Test
	public void testListAuthenticatedAthleteActivities_afterActivity() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final LocalDateTime calendar = LocalDateTime.of(2015, Month.JANUARY, 1, 0, 0);

			final StravaActivity[] activities = api().listAuthenticatedAthleteActivities(null,
					StravaDateUtils.secondsSinceUnixEpoch(calendar), null, null);
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
	@Test
	public void testListAuthenticatedAthleteActivities_beforeActivity() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final LocalDateTime calendar = LocalDateTime.of(2015, Month.JANUARY, 1, 0, 0);

			final StravaActivity[] activities = api()
					.listAuthenticatedAthleteActivities(StravaDateUtils.secondsSinceUnixEpoch(calendar), null, null, null);
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
	@Test
	public void testListAuthenticatedAthleteActivities_beforeAfterCombination() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final LocalDateTime before = LocalDateTime.of(2015, Month.JANUARY, 1, 0, 0);
			final LocalDateTime after = LocalDateTime.of(2014, Month.JANUARY, 1, 0, 0);

			final StravaActivity[] activities = api().listAuthenticatedAthleteActivities(
					StravaDateUtils.secondsSinceUnixEpoch(before), StravaDateUtils.secondsSinceUnixEpoch(after), null, null);
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
	@Test
	public void testListAuthenticatedAthleteActivities_beforeAfterInvalidCombination() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final LocalDateTime before = LocalDateTime.of(2014, Month.JANUARY, 1, 0, 0);
			final LocalDateTime after = LocalDateTime.of(2015, Month.JANUARY, 1, 0, 0);

			final StravaActivity[] activities = api().listAuthenticatedAthleteActivities(
					StravaDateUtils.secondsSinceUnixEpoch(before), StravaDateUtils.secondsSinceUnixEpoch(after), null, null);
			assertNotNull("Returned null collection of activities", activities);
			assertEquals(0, activities.length);
		});
	}

	@Test
	public void testListAuthenticatedAthleteActivities_beforeAfterPaging() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final LocalDateTime before = LocalDateTime.of(2015, Month.JANUARY, 1, 0, 0);
			final LocalDateTime after = LocalDateTime.of(2014, Month.JANUARY, 1, 0, 0);

			final StravaActivity[] activities = api().listAuthenticatedAthleteActivities(
					StravaDateUtils.secondsSinceUnixEpoch(before), StravaDateUtils.secondsSinceUnixEpoch(after), 1, 1);
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
	@Test
	public void testListAuthenticatedAthleteActivities_default() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaActivity[] activities = api().listAuthenticatedAthleteActivities(null, null, null, null);

			assertNotNull("Authenticated athlete's activities returned as null", activities);
			assertNotEquals("No activities returned for the authenticated athlete", 0, activities.length);
			for (final StravaActivity activity : activities) {
				assertNotEquals(Boolean.TRUE, activity.getPrivateActivity());
				assertEquals(TestUtils.ATHLETE_AUTHENTICATED_ID, activity.getAthlete().getId());
				StravaActivityTest.validateActivity(activity);
			}
		});
	}

	@Override
	protected void validate(final StravaActivity activity) {
		StravaActivityTest.validateActivity(activity);

	}

	/**
	 * @see test.api.rest.APIListTest#validateArray(java.lang.Object[])
	 */
	@Override
	protected void validateArray(final StravaActivity[] activities) {
		StravaActivityTest.validateList(Arrays.asList(activities));

	}

	/**
	 * @see test.api.rest.APIListTest#validId()
	 */
	@Override
	protected Integer validId() {
		// Return the authenticated athlete's ID (even though it's not actually
		// used, we need something that's not null)
		return TestUtils.ATHLETE_AUTHENTICATED_ID;
	}

	/**
	 * @see test.api.rest.APIListTest#validIdBelongsToOtherUser()
	 */
	@Override
	protected Integer validIdBelongsToOtherUser() {
		return null;
	}

	/**
	 * @see test.api.rest.APIListTest#validIdNoChildren()
	 */
	@Override
	protected Integer validIdNoChildren() {
		return null;
	}

}
