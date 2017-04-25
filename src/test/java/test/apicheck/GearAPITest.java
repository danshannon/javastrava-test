package test.apicheck;

import org.junit.Test;

import javastrava.api.API;
import javastrava.api.GearAPI;
import javastrava.model.StravaGear;
import retrofit.client.Response;
import test.service.standardtests.data.GearDataUtils;
import test.utils.TestUtils;

/**
 * @author Dan Shannon
 *
 */
public class GearAPITest {
	private static GearAPI api() {
		return API.instance(GearAPI.class, TestUtils.getValidToken());
	}

	/**
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testAPI_getGear() throws Exception {
		final Response response = api().getGearRaw(GearDataUtils.GEAR_VALID_ID);
		ResponseValidator.validate(response, StravaGear.class, "getGear"); //$NON-NLS-1$
	}

}
