package test.apicheck;

import org.junit.Test;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.model.StravaClub;
import javastrava.api.v3.model.StravaClubAnnouncement;
import javastrava.api.v3.model.StravaClubEvent;
import javastrava.api.v3.rest.API;
import javastrava.api.v3.rest.ClubAPI;
import retrofit.client.Response;
import test.service.standardtests.data.ClubDataUtils;
import test.utils.TestUtils;

/**
 * Tests for {@link ClubAPI} methods
 *
 * @author Dan Shannon
 *
 */
public class ClubAPITest {
	private static ClubAPI api() {
		return API.instance(ClubAPI.class, TestUtils.getValidToken());
	}

	/**
	 * Test get club
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testAPI_getClubRaw() throws Exception {
		final Response response = api().getClubRaw(ClubDataUtils.CLUB_VALID_ID);
		ResponseValidator.validate(response, StravaClub.class, "getClub"); //$NON-NLS-1$
	}

	/**
	 * Test listing authenticated athlete's clubs
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testAPI_listAuthenticatedAthleteClubsRaw() throws Exception {
		final Response response = api().listAuthenticatedAthleteClubsRaw();
		ResponseValidator.validate(response, StravaClub.class, "listAuthenticatedAthleteClubs"); //$NON-NLS-1$
	}

	/**
	 * Test listing club administrators
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testAPI_listClubAdminsRaw() throws Exception {
		final Response response = api().listClubAdminsRaw(ClubDataUtils.CLUB_VALID_ID, null, null);
		ResponseValidator.validate(response, StravaAthlete.class, "listClubAdmins"); //$NON-NLS-1$
	}

	/**
	 * Test listing club announcements
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testAPI_listClubAnnouncementsRaw() throws Exception {
		final Response response = api().listClubAnnouncementsRaw(ClubDataUtils.CLUB_VALID_ID);
		ResponseValidator.validate(response, StravaClubAnnouncement.class, "listClubAnnouncements"); //$NON-NLS-1$
	}

	/**
	 * Test listing club group events
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testAPI_listClubGroupEventsRaw() throws Exception {
		final Response response = api().listClubGroupEventsRaw(ClubDataUtils.CLUB_VALID_ID);
		ResponseValidator.validate(response, StravaClubEvent.class, "listClubGroupEvents"); //$NON-NLS-1$
	}

	/**
	 * Test listing club members
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testAPI_listClubMembersRaw() throws Exception {
		final Response response = api().listClubMembersRaw(ClubDataUtils.CLUB_VALID_ID, null, null);
		ResponseValidator.validate(response, StravaAthlete.class, "listClubMembers"); //$NON-NLS-1$
	}

	/**
	 * Test listing recent club activities
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testAPI_listRecentClubactivitiesRaw() throws Exception {
		final Response response = api().listRecentClubActivitiesRaw(ClubDataUtils.CLUB_VALID_ID, null, null);
		ResponseValidator.validate(response, StravaActivity.class, "listRecentClubActivities"); //$NON-NLS-1$
	}

}
