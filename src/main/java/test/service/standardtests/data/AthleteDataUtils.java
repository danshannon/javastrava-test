package test.service.standardtests.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jfairy.Fairy;
import org.jfairy.producer.person.Person;
import org.jfairy.producer.text.TextProducer;

import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.model.StravaAthleteSegmentStats;
import javastrava.api.v3.model.StravaAthleteZone;
import javastrava.api.v3.model.StravaAthleteZones;
import javastrava.api.v3.model.StravaClub;
import javastrava.api.v3.model.StravaStatistics;
import javastrava.api.v3.model.reference.StravaFollowerState;
import javastrava.api.v3.model.reference.StravaGearType;
import javastrava.api.v3.model.reference.StravaGender;
import javastrava.api.v3.model.reference.StravaMeasurementMethod;
import javastrava.api.v3.model.reference.StravaResourceState;
import test.utils.TestUtils;

/**
 * <p>
 * Test data utilities for athletes
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class AthleteDataUtils {
	private static Random random = new Random();

	private static Fairy		fairy	= Fairy.create();
	private static TextProducer	text	= Fairy.create().textProducer();

	/**
	 * Identifier of the authenticated athlete
	 */
	public static Integer ATHLETE_AUTHENTICATED_ID;

	/**
	 * Identifier of an athlete who isn't the authenticated athlete
	 */
	public static Integer ATHLETE_VALID_ID;

	/**
	 * Invalid athlete identifier
	 */
	public static Integer ATHLETE_INVALID_ID;

	/**
	 * Identifier of an athlete with no KOM's/CR's
	 */
	public static Integer ATHLETE_WITHOUT_KOMS;

	/**
	 * Identifier of an athlete who is not following anybody
	 */
	public static Integer ATHLETE_WITHOUT_FRIENDS;

	/**
	 * Identifier of an athlete who has flagged their profile as private
	 */
	public static Integer ATHLETE_PRIVATE_ID;

	static {
		ATHLETE_AUTHENTICATED_ID = TestUtils.integerProperty("test.authenticatedAthleteId"); //$NON-NLS-1$
		ATHLETE_VALID_ID = TestUtils.integerProperty("test.athleteId"); //$NON-NLS-1$
		ATHLETE_INVALID_ID = TestUtils.integerProperty("test.athleteInvalidId"); //$NON-NLS-1$
		ATHLETE_WITHOUT_KOMS = TestUtils.integerProperty("test.athleteWithoutKOMs"); //$NON-NLS-1$
		ATHLETE_WITHOUT_FRIENDS = TestUtils.integerProperty("test.athleteWithoutFriends"); //$NON-NLS-1$
		ATHLETE_PRIVATE_ID = TestUtils.integerProperty("test.athletePrivate"); //$NON-NLS-1$

	}

	/**
	 * Create an athlete instance (as opposed to getting one from Strava) for testing purposes
	 * 
	 * @param resourceState
	 *            the resource state the athlete should be created in
	 *
	 * @return The athlete created
	 */
	@SuppressWarnings("boxing")
	public static StravaAthlete testAthlete(StravaResourceState resourceState) {
		if ((resourceState == StravaResourceState.UNKNOWN) || (resourceState == StravaResourceState.UPDATING)) {
			throw new IllegalArgumentException();
		}

		final StravaAthlete athlete = new StravaAthlete();
		final Person person = fairy.person();

		// Set data required for all resource states
		athlete.setId(random.nextInt(2 ^ (31 - 1)));
		athlete.setResourceState(resourceState);

		// For META and PRIVATE states, return only the above data
		if ((resourceState == StravaResourceState.META) || (resourceState == StravaResourceState.PRIVATE)) {
			return athlete;
		}

		// Add data required for SUMMARY and DETAILED states
		athlete.setFirstname(person.firstName());
		athlete.setLastname(person.lastName());
		athlete.setProfile(text.randomString(random.nextInt(100)));
		athlete.setProfileMedium(text.randomString(random.nextInt(100)));
		athlete.setCity(text.word());
		athlete.setState(text.word());
		athlete.setCountry(text.word());
		athlete.setSex(RefDataUtils.randomGender());
		athlete.setFriend(RefDataUtils.randomFollowerState());
		athlete.setFollower(RefDataUtils.randomFollowerState());
		athlete.setPremium(random.nextBoolean());
		athlete.setCreatedAt(DateUtils.zonedDateTime());
		athlete.setUpdatedAt(DateUtils.zonedDateTime());

		// If this is a SUMMARY state athlete, then return it now
		if (resourceState == StravaResourceState.SUMMARY) {
			return athlete;
		}

		// Set the attributes that only apply to DETAILED state athletes
		athlete.setApproveFollowers(random.nextBoolean());
		athlete.setAthleteType(RefDataUtils.randomAthleteType());
		athlete.setBadgeTypeId(1);
		athlete.setBikes(GearDataUtils.testGearList(StravaResourceState.SUMMARY, StravaGearType.BIKE));
		athlete.setClubs(ClubDataUtils.testClubList(StravaResourceState.META));
		athlete.setDatePreference(text.word());
		athlete.setEmail(person.email());
		athlete.setFollowerCount(random.nextInt(10000));
		athlete.setFriendCount(random.nextInt(10000));
		athlete.setFtp(random.nextInt(1000));
		athlete.setMeasurementPreference(RefDataUtils.randomMeasurementMethod());
		athlete.setMutualFriendCount(random.nextInt(10000));
		athlete.setShoes(GearDataUtils.testGearList(StravaResourceState.SUMMARY, StravaGearType.SHOES));
		athlete.setUsername(person.username());
		athlete.setWeight((random.nextFloat() * 100) + 50);

		// Return the athlete
		return athlete;

	}

	/**
	 * Create a random list of athletes
	 * 
	 * @param resourceState
	 *            The resource state the athletes should be created in
	 * @return The generated list of athletes
	 */
	public static List<StravaAthlete> testAthleteList(StravaResourceState resourceState) {
		final List<StravaAthlete> list = new ArrayList<>();
		final int entries = random.nextInt(10000);
		for (int i = 0; i < entries; i++) {
			list.add(testAthlete(resourceState));
		}
		return list;
	}

	/**
	 * <p>
	 * Validate structure and content
	 * </p>
	 *
	 * @param stats
	 *            Stats to be validated
	 */
	@SuppressWarnings("boxing")
	public static void validateAthleteSegmentStats(final StravaAthleteSegmentStats stats) {
		assertNotNull(stats);
		assertNotNull(stats.getEffortCount());
		assertTrue(stats.getEffortCount() > 0);
		assertNotNull(stats.getPrElapsedTime());
		assertTrue(stats.getPrElapsedTime() > 0);
		assertNotNull(stats.getPrDate());
	}

	/**
	 * Validate structure and content of an athlete
	 *
	 * @param athlete
	 *            Athlete to be validated
	 */
	public static void validateAthlete(final StravaAthlete athlete) {
		AthleteDataUtils.validateAthlete(athlete, athlete.getId(), athlete.getResourceState());
	}

	/**
	 * Validate structure and content of an athlete
	 *
	 * @param athlete
	 *            Athlete to be validated
	 * @param expectedId
	 *            Expected id of the athlete
	 * @param state
	 *            Expected resource state
	 */
	public static void validateAthlete(final StravaAthlete athlete, final Integer expectedId, final StravaResourceState state) {
		assertNotNull(athlete);
		assertEquals(expectedId, athlete.getId());
		assertEquals(state, athlete.getResourceState());
	
		if (athlete.getResourceState() == StravaResourceState.DETAILED) {
			// Not returned because it's not part of the API for detailed
			// athlete returns
			assertNull(athlete.getApproveFollowers());
			assertNotNull(athlete.getBikes());
			// Optional assertNotNull(athlete.getCity());
			assertNotNull(athlete.getClubs());
			for (final StravaClub club : athlete.getClubs()) {
				ClubDataUtils.validate(club);
			}
			// Optional assertNotNull(athlete.getCountry());
			assertNotNull(athlete.getCreatedAt());
			assertNotNull(athlete.getDatePreference());
			assertNotNull(athlete.getEmail());
			assertNotNull(athlete.getFirstname());
			// Is NULL because this IS the authenticated athlete and you can't
			// follow yourself
			assertNull(athlete.getFollower());
			assertNotNull(athlete.getFollowerCount());
			// Is NULL because this is the authenticated athlete and you can't
			// follow yourself
			assertNull(athlete.getFriend());
			assertNotNull(athlete.getFriendCount());
			assertNotNull(athlete.getFtp());
			assertNotNull(athlete.getLastname());
			assertNotNull(athlete.getMeasurementPreference());
			assertFalse(StravaMeasurementMethod.UNKNOWN == athlete.getMeasurementPreference());
			assertNotNull(athlete.getMutualFriendCount());
			assertEquals(new Integer(0), athlete.getMutualFriendCount());
			assertNotNull(athlete.getPremium());
			assertNotNull(athlete.getProfile());
			assertNotNull(athlete.getProfileMedium());
			assertNotNull(athlete.getResourceState());
			assertNotNull(athlete.getSex());
			assertNotNull(athlete.getShoes());
			// Optional assertNotNull(athlete.getState());
			assertNotNull(athlete.getUpdatedAt());
			// Not part of detailed data
			if (athlete.getId().equals(ATHLETE_AUTHENTICATED_ID)) {
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
		fail("Athlete returned with unexpected resource state " + state + " : " + athlete); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * @param zones
	 *            Zones to be validated
	 */
	public static void validateAthleteZones(StravaAthleteZones zones) {
		assertNotNull(zones.getHeartRate());
	}

	/**
	 * @param zone
	 *            Zone to be validate
	 */
	public static void validateAthleteZone(StravaAthleteZone zone) {
		assertNotNull(zone.getZones());
	}

	/**
	 * Validate the structure and content of a statistics object
	 * 
	 * @param stats
	 *            The object to be validated
	 */
	public static void validate(final StravaStatistics stats) {
		assertNotNull(stats);
	
	}
}
