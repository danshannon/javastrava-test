package test.service.impl.activityservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import org.junit.Test;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.util.Paging;
import test.api.model.StravaActivityTest;
import test.service.standardtests.PagingListMethodTest;
import test.service.standardtests.callbacks.ListCallback;
import test.service.standardtests.callbacks.PagingListCallback;
import test.service.standardtests.data.AthleteDataUtils;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * <p>
 * Specific tests for methods that list authenticated athlete's activities
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListAuthenticatedAthleteActivitiesTest extends PagingListMethodTest<StravaActivity, Integer> {
	@Override
	protected PagingListCallback<StravaActivity, Integer> pagingLister() {
		return ((strava, paging, id) -> strava.listAuthenticatedAthleteActivities(paging));
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

			final List<StravaActivity> activities = TestUtils.strava().listAuthenticatedAthleteActivities(null, calendar);
			for (final StravaActivity activity : activities) {
				if (activity.getResourceState() != StravaResourceState.PRIVATE) {
					assertTrue(activity.getStartDateLocal().isAfter(calendar));
					assertEquals(AthleteDataUtils.ATHLETE_AUTHENTICATED_ID, activity.getAthlete().getId());
				}
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
	@SuppressWarnings("static-method")
	@Test
	public void testListAuthenticatedAthleteActivities_beforeActivity() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final LocalDateTime calendar = LocalDateTime.of(2015, Month.JANUARY, 1, 0, 0);

			final List<StravaActivity> activities = TestUtils.strava().listAuthenticatedAthleteActivities(calendar, null);
			for (final StravaActivity activity : activities) {
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
	@SuppressWarnings("static-method")
	@Test
	public void testListAuthenticatedAthleteActivities_beforeAfterCombination() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final LocalDateTime before = LocalDateTime.of(2015, Month.JANUARY, 1, 0, 0);
			final LocalDateTime after = LocalDateTime.of(2014, Month.JANUARY, 1, 0, 0);

			final List<StravaActivity> activities = TestUtils.strava().listAuthenticatedAthleteActivities(before, after);
			for (final StravaActivity activity : activities) {
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
	@SuppressWarnings("static-method")
	@Test
	public void testListAuthenticatedAthleteActivities_beforeAfterInvalidCombination() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final LocalDateTime before = LocalDateTime.of(2014, Month.JANUARY, 1, 0, 0);
			final LocalDateTime after = LocalDateTime.of(2015, Month.JANUARY, 1, 0, 0);

			final List<StravaActivity> activities = TestUtils.strava().listAuthenticatedAthleteActivities(before, after);
			assertNotNull("Returned null collection of activities", activities); //$NON-NLS-1$
			assertEquals(0, activities.size());
		});
	}

	/**
	 * <p>
	 * Test listing of activities between two given times
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testListAuthenticatedAthleteActivities_beforeAfterPaging() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final LocalDateTime before = LocalDateTime.of(2015, Month.JANUARY, 1, 0, 0);
			final LocalDateTime after = LocalDateTime.of(2014, Month.JANUARY, 1, 0, 0);
			final Paging pagingInstruction = new Paging(1, 1);

			final List<StravaActivity> activities = TestUtils.strava().listAuthenticatedAthleteActivities(before, after,
					pagingInstruction);
			assertNotNull(activities);
			assertEquals(1, activities.size());
			for (final StravaActivity activity : activities) {
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
	@SuppressWarnings("static-method")
	@Test
	public void testListAuthenticatedAthleteActivities_default() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaActivity> activities = TestUtils.strava().listAuthenticatedAthleteActivities();

			assertNotNull("Authenticated athlete's activities returned as null", activities); //$NON-NLS-1$
			assertNotEquals("No activities returned for the authenticated athlete", 0, activities.size()); //$NON-NLS-1$
			for (final StravaActivity activity : activities) {
				if (activity.getResourceState() != StravaResourceState.PRIVATE) {
					assertEquals(AthleteDataUtils.ATHLETE_AUTHENTICATED_ID, activity.getAthlete().getId());
				}
				StravaActivityTest.validate(activity);
			}
		});
	}

	@Override
	protected void validate(final StravaActivity activity) {
		StravaActivityTest.validate(activity);
	}

	@Override
	protected ListCallback<StravaActivity, Integer> lister() {
		return ((strava, id) -> strava.listAuthenticatedAthleteActivities());
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
	protected Integer idInvalid() {
		return null;
	}

}
