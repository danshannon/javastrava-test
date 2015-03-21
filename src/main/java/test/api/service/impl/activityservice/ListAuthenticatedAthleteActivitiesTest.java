package test.api.service.impl.activityservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.service.exception.UnauthorizedException;
import javastrava.util.Paging;

import org.junit.Test;

import test.api.model.StravaActivityTest;
import test.api.service.impl.util.ListCallback;
import test.api.service.impl.util.PagingListMethodTest;
import test.utils.TestUtils;

public class ListAuthenticatedAthleteActivitiesTest extends PagingListMethodTest<StravaActivity,Integer> {
	/**
	 * <p>
	 * Default test to list {@link StravaActivity activities} for the currently
	 * authenticated athlete (i.e. the one who corresponds to the current token)
	 * </p>
	 *
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testListAuthenticatedAthleteActivities_default() {
		final List<StravaActivity> activities = service().listAuthenticatedAthleteActivities();

		assertNotNull("Authenticated athlete's activities returned as null", activities);
		assertNotEquals("No activities returned for the authenticated athlete", 0, activities.size());
		for (final StravaActivity activity : activities) {
			assertEquals(TestUtils.ATHLETE_AUTHENTICATED_ID, activity.getAthlete().getId());
			StravaActivityTest.validateActivity(activity);
		}
	}

	/**
	 * <p>
	 * Test listing of {@link StravaActivity activities} before a given time
	 * (i.e. the before parameter, tested in isolation)
	 * </p>
	 *
	 * @throws UnauthorizedException
	 */
	@Test
	public void testListAuthenticatedAthleteActivities_beforeActivity() {
		final LocalDateTime calendar = LocalDateTime.of(2015, Month.JANUARY, 1, 0, 0);
		
		final List<StravaActivity> activities = service().listAuthenticatedAthleteActivities(calendar, null);
		for (final StravaActivity activity : activities) {
			assertTrue(activity.getStartDateLocal().isBefore(calendar));
			assertEquals(TestUtils.ATHLETE_AUTHENTICATED_ID, activity.getAthlete().getId());
			StravaActivityTest.validateActivity(activity);
		}
	}

	/**
	 * <p>
	 * Test listing of {@link StravaActivity activities} after a given time
	 * (i.e. the after parameter, tested in isolation)
	 * </p>
	 *
	 * @throws UnauthorizedException
	 */
	@Test
	public void testListAuthenticatedAthleteActivities_afterActivity() {
		final LocalDateTime calendar = LocalDateTime.of(2015, Month.JANUARY, 1, 0, 0);
		
		final List<StravaActivity> activities = service().listAuthenticatedAthleteActivities(null, calendar);
		for (final StravaActivity activity : activities) {
			assertTrue(activity.getStartDateLocal().isAfter(calendar));
			assertEquals(TestUtils.ATHLETE_AUTHENTICATED_ID, activity.getAthlete().getId());
			StravaActivityTest.validateActivity(activity);
		}
	}

	/**
	 * <p>
	 * Test listing of {@link StravaActivity activities} between two given times
	 * (i.e. before and after parameters in combination)
	 * </p>
	 *
	 * @throws UnauthorizedException
	 */
	@Test
	public void testListAuthenticatedAthleteActivities_beforeAfterCombination() {
		final LocalDateTime before = LocalDateTime.of(2015, Month.JANUARY, 1, 0, 0);
		final LocalDateTime after = LocalDateTime.of(2014, Month.JANUARY, 1, 0, 0);

		final List<StravaActivity> activities = service().listAuthenticatedAthleteActivities(before, after);
		for (final StravaActivity activity : activities) {
			assertTrue(activity.getStartDateLocal().isBefore(before));
			assertTrue(activity.getStartDateLocal().isAfter(after));
			assertEquals(TestUtils.ATHLETE_AUTHENTICATED_ID, activity.getAthlete().getId());
			StravaActivityTest.validateActivity(activity);
		}
	}

	/**
	 * <p>
	 * Test listing of {@link StravaActivity activities} between two given times
	 * (i.e. before and after parameters in combination) BUT WITH AN INVALID
	 * COMBINATION OF BEFORE AND AFTER
	 * </p>
	 *
	 * @throws UnauthorizedException
	 */
	@Test
	public void testListAuthenticatedAthleteActivities_beforeAfterInvalidCombination() {
		final LocalDateTime before = LocalDateTime.of(2014, Month.JANUARY, 1, 0, 0);
		final LocalDateTime after = LocalDateTime.of(2015, Month.JANUARY, 1, 0, 0);

		final List<StravaActivity> activities = service().listAuthenticatedAthleteActivities(before, after);
		assertNotNull("Returned null collection of activities", activities);
		assertEquals(0,activities.size());
	}

	@Override
	protected void validate(final StravaActivity activity, final Integer id, final StravaResourceState state) {
		StravaActivityTest.validateActivity(activity, id, state);

	}

	@Override
	protected void validate(final StravaActivity activity) {
		validate(activity, activity.getId(), activity.getResourceState());

	}

	@Override
	protected ListCallback<StravaActivity> callback() {
		return (new ListCallback<StravaActivity>() {

			@Override
			public List<StravaActivity> getList(final Paging paging) {
				return service().listAuthenticatedAthleteActivities(paging);
			}

		});
	}

}
