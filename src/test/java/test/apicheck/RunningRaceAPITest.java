package test.apicheck;

import org.junit.Test;

import javastrava.api.v3.model.StravaRunningRace;
import javastrava.api.v3.rest.API;
import javastrava.api.v3.rest.RunningRaceAPI;
import retrofit.client.Response;
import test.service.standardtests.data.RunningRaceDataUtils;
import test.utils.TestUtils;

/**
 * <p>
 * Check that the API returns no more data than that which is configured in the model
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class RunningRaceAPITest {
	private static RunningRaceAPI api() {
		return API.instance(RunningRaceAPI.class, TestUtils.getValidToken());
	}

	/**
	 * Test the {@link RunningRaceAPI#getRaceRaw(Integer)} endpoint
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetRace() throws Exception {
		final Response response = api().getRaceRaw(RunningRaceDataUtils.RUNNING_RACE_VALID_ID);
		ResponseValidator.validate(response, StravaRunningRace.class, "getRace"); //$NON-NLS-1$
	}

	/**
	 * Test the {@link RunningRaceAPI#listRacesRaw(Integer)} endpoint
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testListRaces() throws Exception {
		final Response response = api().listRacesRaw(null);
		ResponseValidator.validate(response, StravaRunningRace.class, "listRaces"); //$NON-NLS-1$
	}

}
