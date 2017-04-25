package test.apicheck;

import org.junit.Test;

import javastrava.api.API;
import javastrava.api.ActivityAPI;
import javastrava.model.StravaActivity;
import javastrava.model.StravaActivityZone;
import javastrava.model.StravaAthlete;
import javastrava.model.StravaComment;
import javastrava.model.StravaLap;
import javastrava.model.StravaPhoto;
import retrofit.client.Response;
import test.service.standardtests.data.ActivityDataUtils;
import test.utils.TestUtils;

/**
 * <p>
 * API check tests for the {@link ActivityAPI} - ensure that there are no attributes returned not covered by the model
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ActivityAPITest {
	private static ActivityAPI api() {
		return API.instance(ActivityAPI.class, TestUtils.getValidToken());
	}

	/**
	 * Test for {@link ActivityAPI#getActivity(Long, Boolean)}
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testAPI_getActivity() throws Exception {
		final Response response = api().getActivityRaw(ActivityDataUtils.ACTIVITY_FOR_AUTHENTICATED_USER, Boolean.FALSE);
		ResponseValidator.validate(response, StravaActivity.class, "getActivity"); //$NON-NLS-1$
	}

	/**
	 * Test for {@link ActivityAPI#getActivityRaw(Long, Boolean)} for a run
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testAPI_getActivityRun() throws Exception {
		final Response response = api().getActivityRaw(ActivityDataUtils.ACTIVITY_RUN_WITH_SEGMENTS, Boolean.FALSE);
		ResponseValidator.validate(response, StravaActivity.class, "getActivityRun"); //$NON-NLS-1$
	}

	/**
	 * Test for {@link ActivityAPI#listActivityCommentsRaw(Long, Boolean, Integer, Integer)}
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testAPI_listActivityComments() throws Exception {
		final Response response = api().listActivityCommentsRaw(ActivityDataUtils.ACTIVITY_WITH_COMMENTS, null, null, null);
		ResponseValidator.validate(response, StravaComment.class, "listActivityComments"); //$NON-NLS-1$
	}

	/**
	 * Test for {@link ActivityAPI#listActivityKudoersRaw(Long, Integer, Integer)}
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testAPI_listActivityKudoers() throws Exception {
		final Response response = api().listActivityKudoersRaw(ActivityDataUtils.ACTIVITY_WITH_KUDOS, null, null);
		ResponseValidator.validate(response, StravaAthlete.class, "listActivityKudoers"); //$NON-NLS-1$
	}

	/**
	 * Test for {@link ActivityAPI#listActivityLapsRaw(Long)}
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testAPI_listActivityLaps() throws Exception {
		final Response response = api().listActivityLapsRaw(ActivityDataUtils.ACTIVITY_WITH_LAPS);
		ResponseValidator.validate(response, StravaLap.class, "listActivityLaps"); //$NON-NLS-1$
	}

	/**
	 * Test for {@link ActivityAPI#listActivityPhotosRaw(Long)}
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testAPI_listActivityPhotos() throws Exception {
		final Response response = api().listActivityPhotosRaw(ActivityDataUtils.ACTIVITY_WITH_PHOTOS);
		ResponseValidator.validate(response, StravaPhoto.class, "listActivityPhotos"); //$NON-NLS-1$
	}

	/**
	 * Test for {@link ActivityAPI#listActivityZonesRaw(Long)}
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testAPI_listActivityZones() throws Exception {
		final Response response = api().listActivityZonesRaw(ActivityDataUtils.ACTIVITY_WITH_ZONES);
		ResponseValidator.validate(response, StravaActivityZone.class, "listActivityZones"); //$NON-NLS-1$
	}

	/**
	 * Test for {@link ActivityAPI#listAuthenticatedAthleteActivitiesRaw(Integer, Integer, Integer, Integer)}
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testAPI_listAuthenticatedAthleteActivities() throws Exception {
		final Response response = api().listAuthenticatedAthleteActivitiesRaw(null, null, null, null);
		ResponseValidator.validate(response, StravaActivity.class, "listAuthenticatedAthleteActivities"); //$NON-NLS-1$
	}

	/**
	 * Test for {@link ActivityAPI#listFriendsActivitiesRaw(Integer, Integer)}
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testAPI_listFriendsActivities() throws Exception {
		final Response response = api().listFriendsActivitiesRaw(null, null);
		ResponseValidator.validate(response, StravaActivity.class, "listFriendsActivities"); //$NON-NLS-1$
	}

	/**
	 * Test for {@link ActivityAPI#listRelatedActivitiesRaw(Long, Integer, Integer)}
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testAPI_listRelatedActivities() throws Exception {
		final Response response = api().listRelatedActivitiesRaw(ActivityDataUtils.ACTIVITY_FOR_AUTHENTICATED_USER, null, null);
		ResponseValidator.validate(response, StravaActivity.class, "listRelatedActivities"); //$NON-NLS-1$
	}

}
