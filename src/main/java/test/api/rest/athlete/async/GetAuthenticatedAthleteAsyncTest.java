package test.api.rest.athlete.async;

import test.api.rest.athlete.GetAuthenticatedAthleteTest;

public class GetAuthenticatedAthleteAsyncTest extends GetAuthenticatedAthleteTest {
	/**
	 * No-arguments constructor provides the required callbacks
	 */
	public GetAuthenticatedAthleteAsyncTest() {
		this.getCallback = (api, id) -> api.getAuthenticatedAthleteAsync().get();
	}
}
