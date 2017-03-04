package test.apicheck;

import java.io.IOException;

import org.junit.Test;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.model.StravaClub;
import javastrava.api.v3.model.StravaClubAnnouncement;
import javastrava.api.v3.rest.API;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.json.exception.JsonSerialisationException;
import retrofit.client.Response;
import test.apicheck.api.ClubAPI;
import test.apicheck.api.ResponseValidator;
import test.service.standardtests.data.ClubDataUtils;
import test.utils.TestUtils;

/**
 * @author Dan Shannon
 *
 */
public class ClubAPITest {
	private ClubAPI api() {
		return API.instance(ClubAPI.class, TestUtils.getValidToken());
	}

	@Test
	public void testAPI_getClub() throws NotFoundException, JsonSerialisationException, IOException {
		final Response response = api().getClub(ClubDataUtils.CLUB_VALID_ID);
		ResponseValidator.validate(response, StravaClub.class, "getClub");
	}

	@Test
	public void testAPI_listAuthenticatedAthleteClubs() throws JsonSerialisationException, IOException {
		final Response response = api().listAuthenticatedAthleteClubs();
		ResponseValidator.validate(response, StravaClub.class, "listAuthenticatedAthleteClubs");
	}

	@Test
	public void testAPI_listClubAnnouncements() throws JsonSerialisationException, IOException {
		final Response response = api().listClubAnnouncements(ClubDataUtils.CLUB_VALID_ID);
		ResponseValidator.validate(response, StravaClubAnnouncement.class, "listClubAnnouncements");
	}

	@Test
	public void testAPI_listClubMembers() throws JsonSerialisationException, IOException, NotFoundException {
		final Response response = api().listClubMembers(ClubDataUtils.CLUB_VALID_ID, null, null);
		ResponseValidator.validate(response, StravaAthlete.class, "listClubMembers");
	}

	@Test
	public void testAPI_listRecentClubactivities() throws JsonSerialisationException, IOException, NotFoundException {
		final Response response = api().listRecentClubActivities(ClubDataUtils.CLUB_VALID_ID, null, null);
		ResponseValidator.validate(response, StravaActivity.class, "listRecentClubActivities");
	}

}
