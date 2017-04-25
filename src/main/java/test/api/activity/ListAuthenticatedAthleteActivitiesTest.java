package test.api.activity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.Month;

import org.junit.Test;

import javastrava.api.API;
import javastrava.model.StravaActivity;
import javastrava.util.StravaDateUtils;
import test.api.APIPagingListTest;
import test.api.callback.APIListCallback;
import test.api.util.ArrayCallback;
import test.service.standardtests.data.ActivityDataUtils;
import test.service.standardtests.data.AthleteDataUtils;
import test.utils.RateLimitedTestRunner;

/**
 * <p>
 * Specific tests for {@link API#listAuthenticatedAthleteActivities(Integer, Integer, Integer, Integer)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListAuthenticatedAthleteActivitiesTest extends APIPagingListTest<StravaActivity, Integer> {
	/**
	 * @see test.api.APIListTest#invalidId()
	 */
	@Override
	protected Integer invalidId() {
		// Not applicable, so return null to prevent test from being executed
		return null;
	}

	@Override
	protected APIListCallback<StravaActivity, Integer> listCallback() {
		return ((api, id) -> api.listAuthenticatedAthleteActivities(null, null, null, null));
	}

	@Override
	protected ArrayCallback<StravaActivity> pagingCallback() {
		return (paging -> api().listAuthenticatedAthleteActivities(null, null, paging.getPage(), paging.getPageSize()));
	}

	/**
	 * @see test.api.APIListTest#privateId()
	 */
	@Override
	protected Integer privateId() {
		// Not applicable, so return null to prevent test from being executed
		return null;
	}

	/**
	 * @see test.api.APIListTest#privateIdBelongsToOtherUser()
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
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testListAuthenticatedAthleteActivities_afterActivity() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final LocalDateTime calendar = LocalDateTime.of(2015, Month.JANUARY, 1, 0, 0);

			final StravaActivity[] activities = api().listAuthenticatedAthleteActivities(null, StravaDateUtils.secondsSinceUnixEpoch(calendar), null, null);
			for (final StravaActivity activity : activities) {
				assertNotEquals(Boolean.TRUE, activity.getPrivateActivity());
				assertTrue(activity.getStartDateLocal().isAfter(calendar));
				assertEquals(AthleteDataUtils.ATHLETE_AUTHENTICATED_ID, activity.getAthlete().getId());
				ActivityDataUtils.validate(activity);
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
	@SuppressWarnings("static-method")
	@Test
	public void testListAuthenticatedAthleteActivities_beforeActivity() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final LocalDateTime calendar = LocalDateTime.of(2015, Month.JANUARY, 1, 0, 0);

			final StravaActivity[] activities = api().listAuthenticatedAthleteActivities(StravaDateUtils.secondsSinceUnixEpoch(calendar), null, null, null);
			for (final StravaActivity activity : activities) {
				assertNotEquals(Boolean.TRUE, activity.getPrivateActivity());
				assertTrue(activity.getStartDateLocal().isBefore(calendar));
				assertEquals(AthleteDataUtils.ATHLETE_AUTHENTICATED_ID, activity.getAthlete().getId());
				ActivityDataUtils.validate(activity);
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
	@SuppressWarnings("static-method")
	@Test
	public void testListAuthenticatedAthleteActivities_beforeAfterCombination() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final LocalDateTime before = LocalDateTime.of(2015, Month.JANUARY, 1, 0, 0);
			final LocalDateTime after = LocalDateTime.of(2014, Month.JANUARY, 1, 0, 0);

			final StravaActivity[] activities = api().listAuthenticatedAthleteActivities(StravaDateUtils.secondsSinceUnixEpoch(before), StravaDateUtils.secondsSinceUnixEpoch(after), null, null);
			for (final StravaActivity activity : activities) {
				assertNotEquals(Boolean.TRUE, activity.getPrivateActivity());
				assertTrue(activity.getStartDateLocal().isBefore(before));
				assertTrue(activity.getStartDateLocal().isAfter(after));
				assertEquals(AthleteDataUtils.ATHLETE_AUTHENTICATED_ID, activity.getAthlete().getId());
				ActivityDataUtils.validate(activity);
			}
		});
	}

	/**
	 * <p>
	 * Test listing of {@link StravaActivity activities} between two given times (i.e. before and after parameters in combination) BUT WITH AN INVALID COMBINATION OF BEFORE AND AFTER
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testListAuthenticatedAthleteActivities_beforeAfterInvalidCombination() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final LocalDateTime before = LocalDateTime.of(2014, Month.JANUARY, 1, 0, 0);
			final LocalDateTime after = LocalDateTime.of(2015, Month.JANUARY, 1, 0, 0);

			final StravaActivity[] activities = api().listAuthenticatedAthleteActivities(StravaDateUtils.secondsSinceUnixEpoch(before), StravaDateUtils.secondsSinceUnixEpoch(after), null, null);
			assertNotNull("Returned null collection of activities", activities); //$NON-NLS-1$
			assertEquals(0, activities.length);
		});
	}

	/**
	 * <p>
	 * Test listing of {@link StravaActivity activities} between two given times
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings({ "static-method", "boxing" })
	@Test
	public void testListAuthenticatedAthleteActivities_beforeAfterPaging() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final LocalDateTime before = LocalDateTime.of(2015, Month.JANUARY, 1, 0, 0);
			final LocalDateTime after = LocalDateTime.of(2014, Month.JANUARY, 1, 0, 0);

			final StravaActivity[] activities = api().listAuthenticatedAthleteActivities(StravaDateUtils.secondsSinceUnixEpoch(before), StravaDateUtils.secondsSinceUnixEpoch(after), 1, 1);
			assertNotNull(activities);
			assertEquals(1, activities.length);
			for (final StravaActivity activity : activities) {
				assertNotEquals(Boolean.TRUE, activity.getPrivateActivity());
				assertTrue(activity.getStartDateLocal().isBefore(before));
				assertTrue(activity.getStartDateLocal().isAfter(after));
				assertEquals(AthleteDataUtils.ATHLETE_AUTHENTICATED_ID, activity.getAthlete().getId());
				ActivityDataUtils.validate(activity);
			}
		});
	}

	/**
	 * <p>
	 * Default test to list {@link StravaActivity activities} for the currently authenticated athlete (i.e. the one who corresponds to the current token)
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testListAuthenticatedAthleteActivities_default() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaActivity[] activities = api().listAuthenticatedAthleteActivities(null, null, null, null);

			assertNotNull("Authenticated athlete's activities returned as null", activities); //$NON-NLS-1$
			assertNotEquals("No activities returned for the authenticated athlete", 0, activities.length); //$NON-NLS-1$
			for (final StravaActivity activity : activities) {
				assertNotEquals(Boolean.TRUE, activity.getPrivateActivity());
				assertEquals(AthleteDataUtils.ATHLETE_AUTHENTICATED_ID, activity.getAthlete().getId());
				ActivityDataUtils.validate(activity);
			}
		});
	}

	@Override
	protected void validate(final StravaActivity activity) {
		ActivityDataUtils.validate(activity);

	}

	@Override
	protected void validateArray(final StravaActivity[] activities) {
		for (final StravaActivity activity : activities) {
			ActivityDataUtils.validate(activity);
		}

	}

	/**
	 * @see test.api.APIListTest#validId()
	 */
	@Override
	protected Integer validId() {
		// Return the authenticated athlete's ID (even though it's not actually
		// used, we need something that's not null)
		return AthleteDataUtils.ATHLETE_AUTHENTICATED_ID;
	}

	/**
	 * @see test.api.APIListTest#validIdBelongsToOtherUser()
	 */
	@Override
	protected Integer validIdBelongsToOtherUser() {
		return null;
	}

	/**
	 * @see test.api.APIListTest#validIdNoChildren()
	 */
	@Override
	protected Integer validIdNoChildren() {
		return null;
	}

}
