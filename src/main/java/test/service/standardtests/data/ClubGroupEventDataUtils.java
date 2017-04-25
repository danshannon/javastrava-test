package test.service.standardtests.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jfairy.Fairy;
import org.jfairy.producer.text.TextProducer;

import javastrava.model.StravaClubEvent;
import javastrava.model.StravaClubEventViewerPermissions;
import javastrava.model.reference.StravaResourceState;
import test.service.standardtests.callbacks.GetCallback;
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
		CLUB_EVENT_INVALID_ID = TestUtils.integerProperty("test.clubGroupEventInvalidId"); //$NON-NLS-1$
		CLUB_EVENT_PRIVATE_MEMBER_ID = TestUtils.integerProperty("test.clubGroupEventPrivateId"); //$NON-NLS-1$
	}

	/**
	 * @return Method to use to get a particular event
	 */
	public static GetCallback<StravaClubEvent, Integer> getter() {
		return (strava, id) -> strava.getEvent(id);
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
		event.setWomenOnly(random.nextBoolean());
		event.setPrivateEvent(random.nextBoolean());
		event.setSkillLevels(RefDataUtils.randomSkillLevel());
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

	@SuppressWarnings("boxing")
	private static StravaClubEventViewerPermissions testViewerPermissions() {
		final StravaClubEventViewerPermissions perms = new StravaClubEventViewerPermissions();
		perms.setEdit(random.nextBoolean());
		return perms;
	}

	/**
	 * <p>
	 * Validate the contents of a club event are in line with API documentation
	 * </p>
	 *
	 * @param event
	 *            The event to be validated
	 */
	public static void validateEvent(final StravaClubEvent event) {
		assertNotNull(event);
		ClubGroupEventDataUtils.validateEvent(event, event.getId(), event.getResourceState());
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
	public static void validateEvent(final StravaClubEvent event, final Integer id, final StravaResourceState state) {
		assertNotNull(event);

		// All resource states require that the event has an identifier and a resourceState
		assertNotNull(event.getId());
		assertNotNull(event.getResourceState());

		// Id and resource state must be as expected
		assertEquals(id, event.getId());
		assertEquals(state, event.getResourceState());

		// If resource state is UNKNOWN, or UPDATING, then we can't do any more
		if ((event.getResourceState() == StravaResourceState.UNKNOWN) || (event.getResourceState() == StravaResourceState.UPDATING)) {
			throw new IllegalStateException("Event " + id + " has unexpected resource state " + state); //$NON-NLS-1$ //$NON-NLS-2$
		}

		// For PRIVATE representations, that's it
		if (event.getResourceState() == StravaResourceState.PRIVATE) {
			return;
		}

		// Assumption is that name is required but nothing else is
		// Name is included in META representation since March 2017
		assertNotNull(event.getTitle());

		// If it's a DETAILED representation, then nothing else to check
		if (state == StravaResourceState.DETAILED) {
			return;
		}

		// If it's a SUMAMRY representation, ensure that the things not included in summary representations are null
		if (state == StravaResourceState.SUMMARY) {
			assertNull(event.getViewerPermissions());
			assertNull(event.getStartDatetime());
			assertNull(event.getFrequency());
			assertNull(event.getDayOfWeek());
			assertNull(event.getWeekOfMonth());
			assertNull(event.getDaysOfWeek());
			assertNull(event.getWeeklyInterval());
			return;
		}

		if (state == StravaResourceState.META) {
			assertNull(event.getDescription());
			assertNull(event.getClub());
			assertNull(event.getOrganizingAthlete());
			assertNull(event.getActivityType());
			assertNull(event.getCreatedAt());
			assertNull(event.getRoute());
			assertNull(event.getStartLatlng());
			assertNull(event.getWomenOnly());
			assertNull(event.getPrivateEvent());
			assertNull(event.getSkillLevels());
			assertNull(event.getTerrain());
			assertNull(event.getUpcomingOccurrences());
			assertNull(event.getZone());
			assertNull(event.getAddress());
			assertNull(event.getJoined());
			assertNull(event.getViewerPermissions());
			assertNull(event.getStartDatetime());
			assertNull(event.getFrequency());
			assertNull(event.getDayOfWeek());
			assertNull(event.getWeekOfMonth());
			assertNull(event.getDaysOfWeek());
			assertNull(event.getWeeklyInterval());
			return;
		}

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
			validateEvent(event);
		}

	}
}
