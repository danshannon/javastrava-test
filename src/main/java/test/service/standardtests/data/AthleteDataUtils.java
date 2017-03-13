package test.service.standardtests.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jfairy.Fairy;
import org.jfairy.producer.person.Person;
import org.jfairy.producer.text.TextProducer;

import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.model.reference.StravaGearType;
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
}
