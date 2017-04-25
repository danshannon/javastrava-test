package test.service.standardtests.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jfairy.Fairy;
import org.jfairy.producer.text.TextProducer;

import javastrava.model.StravaClub;
import javastrava.model.StravaClubAnnouncement;
import javastrava.model.StravaClubEvent;
import javastrava.model.reference.StravaClubType;
import javastrava.model.reference.StravaResourceState;
import test.utils.TestUtils;

/**
 * <p>
 * Test data utilities for Clubs
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ClubDataUtils {
	private static Random		random	= new Random();
	private static TextProducer	fairy	= Fairy.create().textProducer();

	/**
	 * Identifier of a valid club
	 */
	public static Integer	CLUB_VALID_ID;
	/**
	 * Invalid club identifier
	 */
	public static Integer	CLUB_INVALID_ID;
	/**
	 * Identifier of a public club that the authenticated athlete is a member of
	 */
	public static Integer	CLUB_PUBLIC_MEMBER_ID;
	/**
	 * Identifier of a public club that the authenticated athlete is not a member of
	 */
	public static Integer	CLUB_PUBLIC_NON_MEMBER_ID;
	/**
	 * Identifier of a private club that the authenticated athlete is a member of
	 */
	public static Integer	CLUB_PRIVATE_MEMBER_ID;
	/**
	 * Identifier of a private club that the authenticated athlete is not a member of
	 */
	public static Integer	CLUB_PRIVATE_NON_MEMBER_ID;

	static {
		CLUB_VALID_ID = TestUtils.integerProperty("test.clubId"); //$NON-NLS-1$
		CLUB_INVALID_ID = TestUtils.integerProperty("test.clubInvalidId"); //$NON-NLS-1$
		CLUB_PRIVATE_MEMBER_ID = TestUtils.integerProperty("test.clubPrivateMemberId"); //$NON-NLS-1$
		CLUB_PRIVATE_NON_MEMBER_ID = TestUtils.integerProperty("test.clubPrivateNonMemberId"); //$NON-NLS-1$
		CLUB_PUBLIC_NON_MEMBER_ID = TestUtils.integerProperty("test.clubNonMemberId"); //$NON-NLS-1$
		CLUB_PUBLIC_MEMBER_ID = TestUtils.integerProperty("test.clubPublicMemberId"); //$NON-NLS-1$
	}

	/**
	 * @param clubs
	 *            List of clubs to check
	 * @param id
	 *            Id of the club we're checking for membership
	 * @return <code>true</code> if one of the clubs has the given id
	 */
	public static boolean checkIsMember(final StravaClub[] clubs, final Integer id) {
		for (final StravaClub club : clubs) {
			if (club.getId().intValue() == id.intValue()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Construct a test club with the given resource state
	 *
	 * @param resourceState
	 *            The resource state to be assigned to the club
	 * @return The club created
	 */
	@SuppressWarnings("boxing")
	public static StravaClub testClub(StravaResourceState resourceState) {
		if ((resourceState == StravaResourceState.UNKNOWN) || (resourceState == StravaResourceState.UPDATING)) {
			throw new IllegalArgumentException();
		}

		final StravaClub club = new StravaClub();

		// Set data which occurs for every resource state
		club.setResourceState(resourceState);
		club.setId(random.nextInt(2 ^ (31 - 1)));
		club.setName(fairy.word(4));

		// Return only the above data for META and PRIVATE clubs
		if ((resourceState == StravaResourceState.META) || (resourceState == StravaResourceState.PRIVATE)) {
			return club;
		}

		// Set data which occurs for both SUMMARY and DETAILED states
		club.setProfile(fairy.randomString(random.nextInt(100)));
		club.setProfileMedium(fairy.randomString(random.nextInt(100)));
		club.setCoverPhoto(fairy.randomString(random.nextInt(100)));
		club.setCoverPhotoSmall(fairy.randomString(random.nextInt(100)));
		club.setSportType(RefDataUtils.randomSportType());
		club.setCity(fairy.word());
		club.setState(fairy.word());
		club.setCountry(fairy.word());
		club.setPrivateClub(random.nextBoolean());
		club.setMemberCount(random.nextInt(10000));
		club.setFeatured(random.nextBoolean());
		club.setVerified(random.nextBoolean());
		club.setUrl(fairy.randomString(random.nextInt(100)));

		// Return only the above data for SUMMARY clubs
		if (resourceState == StravaResourceState.SUMMARY) {
			return club;
		}

		// Set data which is only appropriate to DETAILED state
		club.setAdmin(random.nextBoolean());
		club.setClubType(RefDataUtils.randomClubType());
		club.setDescription(fairy.paragraph());
		club.setFollowingCount(random.nextInt(10000));
		club.setMembership(RefDataUtils.randomClubMembershipStatus());
		club.setOwner(random.nextBoolean());
		club.setPostCount(random.nextInt(10000));
		return club;
	}

	/**
	 * Generate a list of clubs
	 *
	 * @param resourceState
	 *            The resource state of the clubs to be generated
	 * @return The list of clubs
	 */
	public static List<StravaClub> testClubList(StravaResourceState resourceState) {
		final List<StravaClub> list = new ArrayList<>();
		final int entries = random.nextInt(10000);
		for (int i = 0; i < entries; i++) {
			list.add(testClub(resourceState));
		}
		return list;
	}

	/**
	 * <p>
	 * Validate the contents of a club are in line with API documentation
	 * </p>
	 *
	 * @param club
	 *            The club to be validated
	 */
	public static void validate(final StravaClub club) {
		assertNotNull(club);
		ClubDataUtils.validate(club, club.getId(), club.getResourceState());
	}

	/**
	 * <p>
	 * Validate the contents of a club are in line with API documentation
	 * </p>
	 *
	 * @param club
	 *            The club to be validated
	 * @param id
	 *            The club's identifier
	 * @param state
	 *            The resource state of the club
	 */
	public static void validate(final StravaClub club, final Integer id, final StravaResourceState state) {
		assertNotNull(club);
		assertNotNull(club.getId());
		assertNotNull(club.getResourceState());
		assertEquals(id, club.getId());
		assertEquals(state, club.getResourceState());

		if (state == StravaResourceState.DETAILED) {
			assertNotNull(club.getCity());
			assertNotNull(club.getClubType());
			assertFalse(club.equals(StravaClubType.UNKNOWN));
			assertNotNull(club.getCountry());
			// Optional
			// assertNotNull(club.getDescription());
			assertNotNull(club.getMemberCount());
			assertTrue(club.getMemberCount().intValue() >= 0);
			assertNotNull(club.getName());
			assertNotNull(club.getPrivateClub());
			assertNotNull(club.getProfile());
			assertNotNull(club.getProfileMedium());
			assertNotNull(club.getSportType());
			assertNotNull(club.getState());
			// Optional assertNotNull(club.getUrl());
			// Optional assertNotNull(club.getVerified());
			return;
		}
		if (state == StravaResourceState.SUMMARY) {
			// OPTIONAL assertNotNull(club.getCity());
			assertNull(club.getClubType());
			// Optional assertNotNull(club.getCountry());
			// Optional assertNull(club.getDescription());
			// Optional assertNull(club.getMemberCount());
			assertNotNull(club.getName());
			assertNotNull(club.getPrivateClub());
			assertNotNull(club.getProfile());
			assertNotNull(club.getProfileMedium());
			assertNotNull(club.getSportType());
			// Optional assertNotNull(club.getState());
			// Optional assertNull(club.getUrl());
			// Optional assertNotNull(club.getVerified());
			return;
		}
		if ((state == StravaResourceState.META) || (state == StravaResourceState.PRIVATE)) {
			return;
		}
		fail("Unexpected state " + state + " for club " + club); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * Validate contents of the object
	 *
	 * @param object
	 *            Object to be validated
	 */
	public static void validateClubAnnouncement(StravaClubAnnouncement object) {
		assertNotNull(object.getAthlete());
		assertNotNull(object.getClubId());
		assertNotEquals("Unknown StravaResourceState" + object.getResourceState(), object.getResourceState(), //$NON-NLS-1$
				StravaResourceState.UNKNOWN);

	}

	/**
	 * @param event
	 *            The event to be validated
	 */
	public static void validateClubEvent(StravaClubEvent event) {
		assertNotNull(event.getId());
		assertNotNull(event.getClub());
		assertNotNull(event.getResourceState());

	}

	/**
	 * <p>
	 * Validate the contents of all the clubs in a list
	 * </p>
	 *
	 * @param list
	 *            List of clubs to be validated
	 */
	public static void validateList(final List<StravaClub> list) {
		for (final StravaClub club : list) {
			validate(club);
		}

	}
}
