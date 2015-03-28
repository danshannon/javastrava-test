package test.apicheck;

import java.io.IOException;

import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.model.StravaSegmentEffort;
import javastrava.api.v3.model.StravaStatistics;
import javastrava.api.v3.rest.API;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.json.exception.JsonSerialisationException;

import org.junit.Test;

import retrofit.client.Response;
import test.apicheck.api.AthleteAPI;
import test.apicheck.api.ResponseValidator;
import test.utils.TestUtils;

/**
 * @author Dan Shannon
 *
 */
public class AthleteAPITest {
	@Test
	public void testAPI_getAuthenticatedAthlete() throws JsonSerialisationException, IOException {
		Response response = api().getAuthenticatedAthlete();
		ResponseValidator.validate(response, StravaAthlete.class, "getAuthenticatedAthlete");
	}
	
	@Test
	public void testAPI_getAthlete() throws JsonSerialisationException, IOException, NotFoundException {
		Response response = api().getAthlete(TestUtils.ATHLETE_VALID_ID);
		ResponseValidator.validate(response, StravaAthlete.class, "getAthlete");
	}
	
	@Test
	public void testAPI_listAthleteFriends() throws NotFoundException, JsonSerialisationException, IOException {
		Response response = api().listAthleteFriends(TestUtils.ATHLETE_AUTHENTICATED_ID, null, null);
		ResponseValidator.validate(response, StravaAthlete.class, "listAthleteFriends");
	}
	
	@Test
	public void testAPI_listAthleteKOMs() throws NotFoundException, JsonSerialisationException, IOException {
		Response response = api().listAthleteKOMs(TestUtils.ATHLETE_AUTHENTICATED_ID, null, null);
		ResponseValidator.validate(response, StravaSegmentEffort.class, "listAthleteKOMs");
	}
	
	@Test
	public void testAPI_listAthletesBothFollowing() throws NotFoundException, JsonSerialisationException, IOException {
		Response response = api().listAthletesBothFollowing(TestUtils.ATHLETE_VALID_ID, null, null);
		ResponseValidator.validate(response, StravaAthlete.class, "listAthletesNothFollowing");
	}
	
	@Test
	public void testAPI_listAuthenticatedAthleteFriends() throws JsonSerialisationException, IOException {
		Response response = api().listAuthenticatedAthleteFriends(null, null);
		ResponseValidator.validate(response, StravaAthlete.class, "listAuthenticatedAthleteFriends");
	}
	
	@Test
	public void testAPI_getStatistics() throws NotFoundException, JsonSerialisationException, IOException {
		Response response = api().statistics(TestUtils.ATHLETE_AUTHENTICATED_ID);
		ResponseValidator.validate(response, StravaStatistics.class, "getStatistics" );
	}
	
	private AthleteAPI api() {
		return API.instance(AthleteAPI.class, TestUtils.getValidToken());
	}
	

}
