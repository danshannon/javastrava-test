package test.apicheck;

import java.io.IOException;

import javastrava.api.v3.model.StravaGear;
import javastrava.api.v3.rest.API;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.json.exception.JsonSerialisationException;

import org.junit.Test;

import retrofit.client.Response;
import test.apicheck.api.GearAPI;
import test.apicheck.api.ResponseValidator;
import test.service.standardtests.data.GearDataUtils;
import test.utils.TestUtils;

/**
 * @author Dan Shannon
 *
 */
public class GearAPITest {
	@Test
	public void testAPI_getGear() throws NotFoundException, JsonSerialisationException, IOException {
		Response response = api().getGear(GearDataUtils.GEAR_VALID_ID);
		ResponseValidator.validate(response, StravaGear.class, "getGear");
	}
	
	private GearAPI api() {
		return API.instance(GearAPI.class, TestUtils.getValidToken());
	}

}
