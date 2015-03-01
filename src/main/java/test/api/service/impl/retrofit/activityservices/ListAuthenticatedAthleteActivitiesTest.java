package test.api.service.impl.retrofit.activityservices;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.service.ActivityServices;
import javastrava.api.v3.service.exception.UnauthorizedException;
import javastrava.api.v3.service.impl.retrofit.ActivityServicesImpl;
import javastrava.util.Paging;

import org.junit.Test;

import test.api.model.StravaActivityTest;
import test.api.service.impl.ListCallback;
import test.api.service.impl.PagingListMethodTest;
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
		final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		calendar.set(2015, Calendar.JANUARY, 1);

		final List<StravaActivity> activities = service().listAuthenticatedAthleteActivities(calendar, null);
		for (final StravaActivity activity : activities) {
			assertTrue(activity.getStartDate().before(calendar.getTime()));
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
		final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		calendar.set(2015, Calendar.JANUARY, 1);

		final List<StravaActivity> activities = service().listAuthenticatedAthleteActivities(null, calendar);
		for (final StravaActivity activity : activities) {
			assertTrue(activity.getStartDate().after(calendar.getTime()));
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
		final Calendar before = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		before.set(2015, Calendar.JANUARY, 1);
		final Calendar after = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		after.set(2014, Calendar.JANUARY, 1);

		final List<StravaActivity> activities = service().listAuthenticatedAthleteActivities(before, after);
		for (final StravaActivity activity : activities) {
			assertTrue(activity.getStartDate().before(before.getTime()));
			assertTrue(activity.getStartDate().after(after.getTime()));
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
		final ActivityServices service = ActivityServicesImpl.implementation(TestUtils.getValidToken());
		final Calendar before = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		before.set(2014, Calendar.JANUARY, 1);
		final Calendar after = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		after.set(2015, Calendar.JANUARY, 1);

		final List<StravaActivity> activities = service.listAuthenticatedAthleteActivities(before, after);
		assertNotNull("Returned null collection of activities", activities);
		assertEquals(0,activities.size());
	}

	private ActivityServices service() {
		return ActivityServicesImpl.implementation(TestUtils.getValidToken());
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
