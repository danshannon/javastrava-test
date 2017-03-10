package test.apicheck;

import java.io.IOException;

import javastrava.api.v3.model.StravaSegmentEffort;
import javastrava.api.v3.rest.API;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.json.exception.JsonSerialisationException;

import org.junit.Test;

import retrofit.client.Response;
import test.apicheck.api.ResponseValidator;
import test.apicheck.api.SegmentEffortAPI;
import test.service.standardtests.data.SegmentEffortDataUtils;
import test.utils.TestUtils;

/**
 * @author Dan Shannon
 *
 */
public class SegmentEffortAPITest {
	private SegmentEffortAPI api() {
		return API.instance(SegmentEffortAPI.class, TestUtils.getValidToken());
	}

	@Test
	public void testAPI_getSegmentEffort() throws NotFoundException, JsonSerialisationException, IOException {
		Response response = api().getSegmentEffort(SegmentEffortDataUtils.SEGMENT_EFFORT_VALID_ID);
		ResponseValidator.validate(response, StravaSegmentEffort.class, "getSegmentEffort");
	}

}
