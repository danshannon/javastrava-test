package test.apicheck;

import java.io.IOException;

import org.junit.Test;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaActivityZone;
import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.model.StravaComment;
import javastrava.api.v3.model.StravaLap;
import javastrava.api.v3.model.StravaPhoto;
import javastrava.api.v3.rest.API;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.json.exception.JsonSerialisationException;
import retrofit.client.Response;
import test.apicheck.api.ActivityAPI;
import test.apicheck.api.ResponseValidator;
import test.service.standardtests.data.ActivityDataUtils;
import test.utils.TestUtils;

/**
 * @author Dan Shannon
 *
 */
public class ActivityAPITest {
	private ActivityAPI api() {
		return API.instance(ActivityAPI.class, TestUtils.getValidToken());
	}

	@Test
	public void testAPI_getActivity() throws NotFoundException, JsonSerialisationException, IOException {
		final Response response = api().getActivity(ActivityDataUtils.ACTIVITY_FOR_AUTHENTICATED_USER, Boolean.FALSE);
		ResponseValidator.validate(response, StravaActivity.class, "getActivity"); //$NON-NLS-1$
	}

	@Test
	public void testAPI_getActivityRun() throws NotFoundException, JsonSerialisationException, IOException {
		final Response response = api().getActivity(ActivityDataUtils.ACTIVITY_RUN_WITH_SEGMENTS, Boolean.FALSE);
		ResponseValidator.validate(response, StravaActivity.class, "getActivityRun"); //$NON-NLS-1$
	}

	@Test
	public void testAPI_listActivityComments() throws NotFoundException, IOException, JsonSerialisationException {
		final Response response = api().listActivityComments(ActivityDataUtils.ACTIVITY_WITH_COMMENTS, null, null, null);
		ResponseValidator.validate(response, StravaComment.class, "listActivityComments"); //$NON-NLS-1$
	}

	@Test
	public void testAPI_listActivityKudoers() throws JsonSerialisationException, IOException, NotFoundException {
		final Response response = api().listActivityKudoers(ActivityDataUtils.ACTIVITY_WITH_KUDOS, null, null);
		ResponseValidator.validate(response, StravaAthlete.class, "listActivityKudoers"); //$NON-NLS-1$
	}

	@Test
	public void testAPI_listActivityLaps() throws NotFoundException, JsonSerialisationException, IOException {
		final Response response = api().listActivityLaps(ActivityDataUtils.ACTIVITY_WITH_LAPS);
		ResponseValidator.validate(response, StravaLap.class, "listActivityLaps"); //$NON-NLS-1$
	}

	@Test
	public void testAPI_listActivityPhotos() throws JsonSerialisationException, IOException, NotFoundException {
		final Response response = api().listActivityPhotos(ActivityDataUtils.ACTIVITY_WITH_PHOTOS);
		ResponseValidator.validate(response, StravaPhoto.class, "listActivityPhotos"); //$NON-NLS-1$
	}

	@Test
	public void testAPI_listActivityZones() throws NotFoundException, JsonSerialisationException, IOException {
		final Response response = api().listActivityZones(ActivityDataUtils.ACTIVITY_WITH_ZONES);
		ResponseValidator.validate(response, StravaActivityZone.class, "listActivityZones"); //$NON-NLS-1$
	}

	@Test
	public void testAPI_listAuthenticatedAthleteActivities() throws JsonSerialisationException, IOException {
		final Response response = api().listAuthenticatedAthleteActivities(null, null, null, null);
		ResponseValidator.validate(response, StravaActivity.class, "listAuthenticatedAthleteActivities"); //$NON-NLS-1$
	}

	@Test
	public void testAPI_listFriendsActivities() throws JsonSerialisationException, IOException {
		final Response response = api().listFriendsActivities(null, null);
		ResponseValidator.validate(response, StravaActivity.class, "listFriendsActivities"); //$NON-NLS-1$
	}

	@Test
	public void testAPI_listRelatedActivities() throws NotFoundException, JsonSerialisationException, IOException {
		final Response response = api().listRelatedActivities(ActivityDataUtils.ACTIVITY_FOR_AUTHENTICATED_USER, null, null);
		ResponseValidator.validate(response, StravaActivity.class, "listRelatedActivities"); //$NON-NLS-1$
	}

}
