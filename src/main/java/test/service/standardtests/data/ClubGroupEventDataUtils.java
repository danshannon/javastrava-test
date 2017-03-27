package test.service.standardtests.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jfairy.Fairy;
import org.jfairy.producer.text.TextProducer;

import javastrava.api.v3.model.StravaClubEvent;
import javastrava.api.v3.model.StravaClubEventViewerPermissions;
import javastrava.api.v3.model.reference.StravaResourceState;
import test.utils.TestUtils;

/**
 * <p>
 * Test data utilities for Clubs
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ClubGroupEventDataUtils {
	private static Random		random	= new Random();
	private static TextProducer	fairy	= Fairy.create().textProducer();

	/**
	 * Identifier of a valid club event
	 */
	public static Integer CLUB_EVENT_VALID_ID;

	/**
	 * Invalid club event identifier
	 */
	public static Integer CLUB_EVENT_INVALID_ID;

	/**
	 * Identifier of a private club event that the authenticated athlete has joined
	 */
	public static Integer CLUB_EVENT_PRIVATE_MEMBER_ID;

	static {
		CLUB_EVENT_VALID_ID = TestUtils.integerProperty("test.clubGroupEventValidId"); //$NON-NLS-1$
		CLUB_EVENT_INVALID_ID = TestUtils.integerProperty("test.clubGRoupEventInvalidId"); //$NON-NLS-1$
		CLUB_EVENT_PRIVATE_MEMBER_ID = TestUtils.integerProperty("test.clubGroupEventPrivateId"); //$NON-NLS-1$
	}

	/**
	 * Construct a test club group event with the given resource state
	 *
	 * @param resourceState
	 *            The resource state to be assigned to the club
	 * @return The club group event created
	 */
	@SuppressWarnings("boxing")
	public static StravaClubEvent testClubGroupEvent(StravaResourceState resourceState) {
		if ((resourceState == StravaResourceState.UNKNOWN) || (resourceState == StravaResourceState.UPDATING)) {
			throw new IllegalArgumentException();
		}

		final StravaClubEvent event = new StravaClubEvent();

		// Set data which occurs for every resource state
		event.setResourceState(resourceState);
		event.setId(random.nextInt(2 ^ (31 - 1)));

		// Return only the above data for META and PRIVATE clubs
		if ((resourceState == StravaResourceState.META) || (resourceState == StravaResourceState.PRIVATE)) {
			return event;
		}

		// Set data which occurs for both SUMMARY and DETAILED states
		event.setTitle(fairy.word(6));
		event.setDescription(fairy.paragraph());
		event.setClub(ClubDataUtils.testClub(StravaResourceState.META));
		event.setOrganizingAthlete(AthleteDataUtils.testAthlete(StravaResourceState.SUMMARY));
		event.setActivityType(RefDataUtils.randomActivityType());
		event.setCreatedAt(DateUtils.zonedDateTime());
		event.setRoute(RouteDataUtils.testRoute(StravaResourceState.META));
		event.setStartLatlng(MapDataUtils.testMapPoint(resourceState));
		event.setWomanOnly(random.nextBoolean());
		event.setPrivateEvent(random.nextBoolean());
		event.setSkillLevel(RefDataUtils.randomSkillLevel());
		event.setTerrain(RefDataUtils.randomTerrainType());
		event.setUpcomingOccurrences(DateUtils.localDateTimeList(5));
		event.setZone(fairy.word());
		event.setAddress(fairy.paragraph());
		event.setJoined(random.nextBoolean());

		// Return only the above data for SUMMARY clubs
		if (resourceState == StravaResourceState.SUMMARY) {
			return event;
		}

		// Set data which is only appropriate to DETAILED state
		event.setViewerPermissions(testViewerPermissions());
		event.setStartDatetime(DateUtils.localDateTime());
		event.setFrequency(RefDataUtils.randomEventFrequency());
		event.setDayOfWeek(fairy.word());
		event.setWeekOfMonth(RefDataUtils.randomWeekOfMonth());
		event.setWeeklyInterval(random.nextInt(4));

		return event;
	}

	@SuppressWarnings("boxing")
	private static StravaClubEventViewerPermissions testViewerPermissions() {
		final StravaClubEventViewerPermissions perms = new StravaClubEventViewerPermissions();
		perms.setEdit(random.nextBoolean());
		return perms;
	}

	/**
	 * Generate a list of clubs
	 *
	 * @param resourceState
	 *            The resource state of the clubs to be generated
	 * @return The list of clubs
	 */
	public static List<StravaClubEvent> testClubGroupEventList(StravaResourceState resourceState) {
		final List<StravaClubEvent> list = new ArrayList<>();
		final int entries = random.nextInt(10000);
		for (int i = 0; i < entries; i++) {
			list.add(testClubGroupEvent(resourceState));
		}
		return list;
	}

	/**
	 * <p>
	 * Validate the contents of a club event are in line with API documentation
	 * </p>
	 *
	 * @param event
	 *            The event to be validated
	 */
	public static void validate(final StravaClubEvent event) {
		assertNotNull(event);
		ClubGroupEventDataUtils.validate(event, event.getId(), event.getResourceState());
	}

	/**
	 * <p>
	 * Validate the contents of a club are in line with API documentation
	 * </p>
	 *
	 * @param event
	 *            The club event to be validated
	 * @param id
	 *            The club event's identifier
	 * @param state
	 *            The resource state of the club event
	 */
	public static void validate(final StravaClubEvent event, final Integer id, final StravaResourceState state) {
		assertNotNull(event);
		assertNotNull(event.getId());
		assertNotNull(event.getResourceState());
		assertEquals(id, event.getId());
		assertEquals(state, event.getResourceState());

	}

	/**
	 * <p>
	 * Validate the contents of all the club events in a list
	 * </p>
	 *
	 * @param list
	 *            List of clubs to be validated
	 */
	public static void validateList(final List<StravaClubEvent> list) {
		for (final StravaClubEvent event : list) {
			validate(event);
		}

	}
}
