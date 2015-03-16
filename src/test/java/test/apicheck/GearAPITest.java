package test.apicheck;

import java.io.IOException;

import javastrava.api.v3.model.StravaGear;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.api.v3.service.impl.retrofit.Retrofit;
import javastrava.util.exception.JsonSerialisationException;

import org.junit.Test;

import retrofit.client.Response;
import test.apicheck.api.GearAPI;
import test.apicheck.api.ResponseValidator;
import test.utils.TestUtils;

/**
 * @author dshannon
 *
 */
public class GearAPITest {
	@Test
	public void testAPI_getGear() throws NotFoundException, JsonSerialisationException, IOException {
		Response response = api().getGear(TestUtils.GEAR_VALID_ID);
		ResponseValidator.validate(response, StravaGear.class, "getGear");
	}
	
	private GearAPI api() {
		return Retrofit.retrofit(GearAPI.class, TestUtils.getValidToken());
	}

}
