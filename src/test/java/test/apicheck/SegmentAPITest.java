package test.apicheck;

import java.io.IOException;

import javastrava.api.v3.model.StravaSegment;
import javastrava.api.v3.model.StravaSegmentEffort;
import javastrava.api.v3.model.StravaSegmentLeaderboard;
import javastrava.api.v3.rest.API;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.json.exception.JsonSerialisationException;

import org.junit.Test;

import retrofit.client.Response;
import test.apicheck.api.ResponseValidator;
import test.apicheck.api.SegmentAPI;
import test.utils.TestUtils;

/**
 * @author Dan Shannon
 *
 */
public class SegmentAPITest {
	@Test
	public void testAPI_getSegment() throws NotFoundException, JsonSerialisationException, IOException {
		final Response response = api().getSegment(TestUtils.SEGMENT_VALID_ID);
		ResponseValidator.validate(response, StravaSegment.class, "getSegment");
	}

	@Test
	public void testAPI_getSegmentLeaderboard() throws NotFoundException, JsonSerialisationException, IOException {
		final Response response = api().getSegmentLeaderboard(TestUtils.SEGMENT_VALID_ID, null, null, null, null, null, null, null, null, null);
		ResponseValidator.validate(response, StravaSegmentLeaderboard.class, "getSegmentLeaderboard");
	}

	@Test
	public void testAPI_listAuthenticatedAthleteStarredSegments() throws JsonSerialisationException, IOException {
		final Response response = api().listAuthenticatedAthleteStarredSegments(null, null);
		ResponseValidator.validate(response, StravaSegment.class, "listAuthenticatedAthleteStarredSegments");
	}

	@Test
	public void testAPI_listSegmentEfforts() throws NotFoundException, JsonSerialisationException, IOException {
		final Response response = api().listSegmentEfforts(TestUtils.SEGMENT_VALID_ID, null, null, null, null, null);
		ResponseValidator.validate(response, StravaSegmentEffort.class, "listSegmentEfforts");
	}

	private SegmentAPI api() {
		return API.instance(SegmentAPI.class, TestUtils.getValidToken());
	}

}
