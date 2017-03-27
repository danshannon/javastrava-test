package test.apicheck;

import org.junit.Test;

import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.model.StravaClubEvent;
import javastrava.api.v3.rest.API;
import javastrava.api.v3.rest.ClubGroupEventAPI;
import retrofit.client.Response;
import test.service.standardtests.data.ClubGroupEventDataUtils;
import test.utils.TestUtils;

/**
 * Test for {@link ClubGroupEventAPI} methods
 *
 * @author Dan Shannon
 *
 */
public class ClubGroupEventAPITest {
	private static ClubGroupEventAPI api() {
		return API.instance(ClubGroupEventAPI.class, TestUtils.getValidToken());
	}

	/**
	 * Test get event
	 *
	 * @throws Exception
	 *             If the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetEvent() throws Exception {
		final Response response = api().getEventRaw(ClubGroupEventDataUtils.CLUB_EVENT_VALID_ID);
		ResponseValidator.validate(response, StravaClubEvent.class, "getEvent"); //$NON-NLS-1$
	}

	/**
	 * Test list joined athletes
	 * 
	 * @throws Exception
	 *             If the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testListEventJoinedAthletes() throws Exception {
		final Response response = api().listEventJoinedAthletesRaw(ClubGroupEventDataUtils.CLUB_EVENT_VALID_ID, null, null);
		ResponseValidator.validate(response, StravaAthlete.class, "listEventJoinedAthletes"); //$NON-NLS-1$
	}

}
