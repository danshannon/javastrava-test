package test.api.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.model.StravaClub;
import javastrava.api.v3.model.reference.StravaFollowerState;
import javastrava.api.v3.model.reference.StravaGender;
import javastrava.api.v3.model.reference.StravaMeasurementMethod;
import javastrava.api.v3.model.reference.StravaResourceState;
import test.utils.BeanTest;
import test.utils.TestUtils;

/**
 * @author dshannon
 *
 */
public class StravaAthleteTest extends BeanTest<StravaAthlete> {

	@Override
	protected Class<StravaAthlete> getClassUnderTest() {
		return StravaAthlete.class;
	}

	public static void validateAthlete(final StravaAthlete athlete, final Integer expectedId, final StravaResourceState state) {
		assertNotNull(athlete);
		assertEquals(expectedId, athlete.getId());
		assertEquals(state,athlete.getResourceState());

		if (athlete.getResourceState() == StravaResourceState.DETAILED) {
			// Not returned because it's not part of the API for detailed athlete returns
			assertNull(athlete.getApproveFollowers());
			assertNotNull(athlete.getBikes());
			// Optional assertNotNull(athlete.getCity());
			assertNotNull(athlete.getClubs());
			for (final StravaClub club : athlete.getClubs()) {
				StravaClubTest.validate(club);
			}
			// Optional assertNotNull(athlete.getCountry());
			assertNotNull(athlete.getCreatedAt());
			assertNotNull(athlete.getDatePreference());
			assertNotNull(athlete.getEmail());
			assertNotNull(athlete.getFirstname());
			// Is NULL because this IS the authenticated athlete and you can't follow yourself
			assertNull(athlete.getFollower());
			assertNotNull(athlete.getFollowerCount());
			// Is NULL because this is the authenticated athlete and you can't follow yourself
			assertNull(athlete.getFriend());
			assertNotNull(athlete.getFriendCount());
			assertNotNull(athlete.getFtp());
			assertNotNull(athlete.getLastname());
			assertNotNull(athlete.getMeasurementPreference());
			assertFalse(StravaMeasurementMethod.UNKNOWN == athlete.getMeasurementPreference());
			assertNotNull(athlete.getMutualFriendCount());
			assertEquals(new Integer(0),athlete.getMutualFriendCount());
			assertNotNull(athlete.getPremium());
			assertNotNull(athlete.getProfile());
			assertNotNull(athlete.getProfileMedium());
			assertNotNull(athlete.getResourceState());
			assertNotNull(athlete.getSex());
			assertNotNull(athlete.getShoes());
			// Optional assertNotNull(athlete.getState());
			assertNotNull(athlete.getUpdatedAt());
			// Not part of detailed data
			if (athlete.getId().equals(TestUtils.ATHLETE_AUTHENTICATED_ID)) {
				assertNotNull(athlete.getWeight());
			} else {
				assertNull(athlete.getWeight());
			}
			assertNotNull(athlete.getBadgeTypeId());
			return;
		}
		if (athlete.getResourceState() == StravaResourceState.SUMMARY) {
			// Not part of summary data
			assertNull(athlete.getApproveFollowers());
			// Not part of summary data
			assertNull(athlete.getBikes());
			// Optional assertNotNull(athlete.getCity());
			// Not part of summary data
			assertNull(athlete.getClubs());
			// Optional assertNotNull(athlete.getCountry());
			assertNotNull(athlete.getCreatedAt());
			// Not part of summary data
			assertNull(athlete.getDatePreference());
			// Not part of summary data
			assertNull(athlete.getEmail());
			assertNotNull(athlete.getFirstname());
			if (athlete.getFollower() != null) {
				assertFalse(StravaFollowerState.UNKNOWN == athlete.getFollower());
			}
			// Not part of summary data
			assertNull(athlete.getFollowerCount());
			if (athlete.getFriend() != null) {
				assertFalse(StravaFollowerState.UNKNOWN == athlete.getFriend());
			}
			assertNull(athlete.getFriendCount());
			// Not part of summary data
			assertNull(athlete.getFtp());
			assertNotNull(athlete.getLastname());
			// Not part of summary data
			assertNull(athlete.getMeasurementPreference());
			// Not part of summary data
			assertNull(athlete.getMutualFriendCount());
			assertNotNull(athlete.getPremium());
			assertNotNull(athlete.getProfile());
			assertNotNull(athlete.getProfileMedium());
			// Not part of summary data
			assertNotNull(athlete.getResourceState());
			// Optional
			if (athlete.getSex() != null) {
				assertFalse(athlete.getSex() == StravaGender.UNKNOWN);
			}
			// Not part of summary data
			assertNull(athlete.getShoes());
			// Optional assertNotNull(athlete.getState());
			assertNotNull(athlete.getUpdatedAt());
			// Not part of summary data
			assertNull(athlete.getWeight());
			assertNotNull(athlete.getBadgeTypeId());
			return;
		}
		if (athlete.getResourceState() == StravaResourceState.META) {
			assertNull(athlete.getApproveFollowers());
			assertNull(athlete.getBikes());
			assertNull(athlete.getCity());
			assertNull(athlete.getClubs());
			assertNull(athlete.getCountry());
			assertNull(athlete.getCreatedAt());
			assertNull(athlete.getDatePreference());
			assertNull(athlete.getEmail());
			assertNull(athlete.getFirstname());
			assertNull(athlete.getFollower());
			assertNull(athlete.getFollowerCount());
			assertNull(athlete.getFriend());
			assertNull(athlete.getFriendCount());
			assertNull(athlete.getFtp());
			assertNull(athlete.getLastname());
			assertNull(athlete.getMeasurementPreference());
			assertNull(athlete.getMutualFriendCount());
			assertNull(athlete.getPremium());
			assertNull(athlete.getProfile());
			assertNull(athlete.getProfileMedium());
			assertNull(athlete.getSex());
			assertNull(athlete.getShoes());
			assertNull(athlete.getState());
			assertNull(athlete.getUpdatedAt());
			assertNull(athlete.getWeight());
			assertNull(athlete.getBadgeTypeId());
			return;
		}
		fail("Athlete returned with unexpected resource state " + state + " : " + athlete);
	}

	public static void validateAthlete(final StravaAthlete athlete) {
		StravaAthleteTest.validateAthlete(athlete, athlete.getId(), athlete.getResourceState());
	}
}
