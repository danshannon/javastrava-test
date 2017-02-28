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
import test.utils.TestUtils;

/**
 * @author Dan Shannon
 *
 */
public class ActivityAPITest {
	@Test
	public void testAPI_getActivity() throws NotFoundException, JsonSerialisationException, IOException {
		final Response response = api().getActivity(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER, Boolean.FALSE);
		ResponseValidator.validate(response, StravaActivity.class, "getActivity");
	}

	@Test
	public void testAPI_getActivityRun() throws NotFoundException, JsonSerialisationException, IOException {
		final Response response = api().getActivity(TestUtils.ACTIVITY_RUN_WITH_SEGMENTS, Boolean.FALSE);
		ResponseValidator.validate(response, StravaActivity.class, "getActivityRun");
	}

	@Test
	public void testAPI_listActivityComments() throws NotFoundException, IOException, JsonSerialisationException {
		final Response response = api().listActivityComments(TestUtils.ACTIVITY_WITH_COMMENTS, null, null, null);
		ResponseValidator.validate(response, StravaComment.class, "listActivityComments");
	}

	@Test
	public void testAPI_listActivityKudoers() throws JsonSerialisationException, IOException, NotFoundException {
		final Response response = api().listActivityKudoers(TestUtils.ACTIVITY_WITH_KUDOS, null, null);
		ResponseValidator.validate(response, StravaAthlete.class, "listActivityKudoers");
	}

	@Test
	public void testAPI_listActivityLaps() throws NotFoundException, JsonSerialisationException, IOException {
		final Response response = api().listActivityLaps(TestUtils.ACTIVITY_WITH_LAPS);
		ResponseValidator.validate(response, StravaLap.class, "listActivityLaps");
	}

	@Test
	public void testAPI_listActivityPhotos() throws JsonSerialisationException, IOException, NotFoundException {
		final Response response = api().listActivityPhotos(TestUtils.ACTIVITY_WITH_PHOTOS);
		ResponseValidator.validate(response, StravaPhoto.class, "listActivityPhotos");
	}

	@Test
	public void testAPI_listActivityZones() throws NotFoundException, JsonSerialisationException, IOException {
		final Response response = api().listActivityZones(TestUtils.ACTIVITY_WITH_ZONES);
		ResponseValidator.validate(response, StravaActivityZone.class, "listActivityZones");
	}

	@Test
	public void testAPI_listAuthenticatedAthleteActivities() throws JsonSerialisationException, IOException {
		final Response response = api().listAuthenticatedAthleteActivities(null, null, null, null);
		ResponseValidator.validate(response, StravaActivity.class, "listAuthenticatedAthleteActivities");
	}

	@Test
	public void testAPI_listFriendsActivities() throws JsonSerialisationException, IOException {
		final Response response = api().listFriendsActivities(null, null);
		ResponseValidator.validate(response, StravaActivity.class, "listFriendsActivities");
	}

	@Test
	public void testAPI_listRelatedActivities() throws NotFoundException, JsonSerialisationException, IOException {
		final Response response = api().listRelatedActivities(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER, null, null);
		ResponseValidator.validate(response, StravaActivity.class, "listRelatedActivities");
	}

	private ActivityAPI api() {
		return API.instance(ActivityAPI.class, TestUtils.getValidToken());
	}

}
