package test.apicheck;

import org.junit.Test;

import javastrava.api.API;
import javastrava.api.RouteAPI;
import javastrava.model.StravaRoute;
import retrofit.client.Response;
import test.service.standardtests.data.AthleteDataUtils;
import test.service.standardtests.data.RouteDataUtils;
import test.utils.TestUtils;

/**
 * <p>
 * Check that the API returns no more data than that which is configured in the model
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class RouteAPITest {
	private static RouteAPI api() {
		return API.instance(RouteAPI.class, TestUtils.getValidToken());
	}

	/**
	 * Test the {@link RouteAPI#getRouteRaw(Integer)} endpoint
	 *
	 * @throws Exception
	 *             if the test fails for an unexpected reason
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetRoute() throws Exception {
		final Response response = api().getRouteRaw(RouteDataUtils.ROUTE_VALID_ID);
		ResponseValidator.validate(response, StravaRoute.class, "getRoute"); //$NON-NLS-1$
	}

	/**
	 * Test the {@link RouteAPI#listAthleteRoutesRaw(Integer)} endpoint
	 *
	 * @throws Exception
	 *             if the test fails for an unexpected reason
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testListRoutes() throws Exception {
		final Response response = api().listAthleteRoutesRaw(AthleteDataUtils.ATHLETE_VALID_ID);
		ResponseValidator.validate(response, StravaRoute.class, "listRoutes"); //$NON-NLS-1$
	}
}
