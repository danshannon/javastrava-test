package test.apicheck;

import java.io.IOException;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.model.StravaClub;
import javastrava.api.v3.rest.API;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.json.exception.JsonSerialisationException;

import org.junit.Test;

import retrofit.client.Response;
import test.apicheck.api.ClubAPI;
import test.apicheck.api.ResponseValidator;
import test.utils.TestUtils;

/**
 * @author Dan Shannon
 *
 */
public class ClubAPITest {
	@Test
	public void testAPI_getClub() throws NotFoundException, JsonSerialisationException, IOException {
		Response response = api().getClub(TestUtils.CLUB_VALID_ID);
		ResponseValidator.validate(response, StravaClub.class, "getClub");
	}
	
	@Test
	public void testAPI_listAuthenticatedAthleteClubs() throws JsonSerialisationException, IOException {
		Response response = api().listAuthenticatedAthleteClubs();
		ResponseValidator.validate(response, StravaClub.class, "listAuthenticatedAthleteClubs");
	}
	
	@Test
	public void testAPI_listClubMembers() throws JsonSerialisationException, IOException, NotFoundException {
		Response response = api().listClubMembers(TestUtils.CLUB_VALID_ID, null, null);
		ResponseValidator.validate(response, StravaAthlete.class, "listClubMembers");
	}
	
	@Test
	public void testAPI_listRecentClubactivities() throws JsonSerialisationException, IOException, NotFoundException {
		Response response = api().listRecentClubActivities(TestUtils.CLUB_VALID_ID, null, null);
		ResponseValidator.validate(response, StravaActivity.class, "listRecentClubActivities");
	}
	
	private ClubAPI api() {
		return API.instance(ClubAPI.class, TestUtils.getValidToken());
	}

}
