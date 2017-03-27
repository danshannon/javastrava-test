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
import org.jfairy.producer.text.TextProducer;

import javastrava.api.v3.model.StravaSegment;
import javastrava.api.v3.model.StravaSegmentExplorerResponse;
import javastrava.api.v3.model.StravaSegmentExplorerResponseSegment;
import javastrava.api.v3.model.StravaSegmentLeaderboard;
import javastrava.api.v3.model.StravaSegmentLeaderboardEntry;
import javastrava.api.v3.model.reference.StravaClimbCategory;
import javastrava.api.v3.model.reference.StravaGender;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.model.reference.StravaSegmentActivityType;
import test.utils.TestUtils;

/**
 * <p>
 * Test data utilities for segments
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class SegmentDataUtils {

	/**
	 * Identifier of a valid segment that belongs to the authenticated user
	 */
	public static Integer	SEGMENT_VALID_ID;
	/**
	 * Invalid segment identifier
	 */
	public static Integer	SEGMENT_INVALID_ID;
	/**
	 * Identifier of a valid segment that belongs to the authenticated user and is flagged as private
	 */
	public static Integer	SEGMENT_PRIVATE_ID;
	/**
	 * Identifier of a valid segment that belongs to someone other than the authenticated user and is flagged as private
	 */
	public static Integer	SEGMENT_OTHER_USER_PRIVATE_ID;
	/**
	 * Identifier of a valid segment that is flagged as hazardous
	 */
	public static Integer	SEGMENT_HAZARDOUS_ID;
	/**
	 * Identifier of a valid segment that is starred by the authenticated user and flagged as private
	 */
	public static Integer	SEGMENT_PRIVATE_STARRED_ID;

	static {
		SEGMENT_VALID_ID = TestUtils.integerProperty("test.segmentId"); //$NON-NLS-1$
		SEGMENT_INVALID_ID = TestUtils.integerProperty("test.segmentInvalidId"); //$NON-NLS-1$
		SEGMENT_PRIVATE_ID = TestUtils.integerProperty("test.segmentPrivateId"); //$NON-NLS-1$
		SEGMENT_OTHER_USER_PRIVATE_ID = TestUtils.integerProperty("test.segmentOtherUserPrivateId"); //$NON-NLS-1$
		SEGMENT_HAZARDOUS_ID = TestUtils.integerProperty("test.segmentHazardousId"); //$NON-NLS-1$
		SEGMENT_PRIVATE_STARRED_ID = TestUtils.integerProperty("test.segmentPrivateStarredId"); //$NON-NLS-1$

	}

	private static Random random = new Random();

	private static TextProducer text = Fairy.create().textProducer();

	/**
	 * Generate a randomised segment with the required resource state
	 *
	 * @param resourceState
	 *            The required resource state
	 * @return The generated segment
	 */
	@SuppressWarnings("boxing")
	public static StravaSegment testSegment(StravaResourceState resourceState) {
		if ((resourceState == StravaResourceState.UNKNOWN) || (resourceState == StravaResourceState.UPDATING)) {
			throw new IllegalArgumentException();
		}

		final StravaSegment segment = new StravaSegment();

		// Add the attributes common to all resource states
		segment.setId(random.nextInt(Integer.MAX_VALUE));
		segment.setResourceState(resourceState);

		// If we're looking for a META or PRIVATE segment then return it now
		if ((resourceState == StravaResourceState.META) || (resourceState == StravaResourceState.PRIVATE)) {
			return segment;
		}

		// Set attributes common to both SUMMARY and DETAILED resource states
		segment.setActivityType(RefDataUtils.randomSegmentActivityType());
		segment.setAthletePrEffort(SegmentEffortDataUtils.testSegmentEffort(StravaResourceState.SUMMARY));
		segment.setAverageGrade(random.nextFloat() * 25);
		segment.setCity(text.word());
		segment.setClimbCategory(RefDataUtils.randomClimbCategory());
		segment.setCountry(text.word());
		segment.setDistance(random.nextFloat() * 20000);
		segment.setElevationHigh(random.nextFloat() * 9000);
		segment.setElevationLow(random.nextFloat() * segment.getElevationHigh());
		segment.setEndLatlng(MapDataUtils.testMapPoint(resourceState));
		segment.setHazardous(random.nextBoolean());
		segment.setMaximumGrade(random.nextFloat() * 100);
		segment.setName(text.sentence());
		segment.setPrivateSegment(random.nextBoolean());
		segment.setStarred(random.nextBoolean());
		segment.setStarredDate(DateUtils.zonedDateTime());
		segment.setStartLatlng(MapDataUtils.testMapPoint(resourceState));
		segment.setState(text.sentence());

		// If this is a SUMMARY segment, return it now
		if (resourceState == StravaResourceState.SUMMARY) {
			return segment;
		}

		// Set the attributes specific to DETAILED resource state
		segment.setAthleteCount(random.nextInt(100000));
		segment.setCreatedAt(DateUtils.zonedDateTime());
		segment.setEffortCount(random.nextInt(100000));
		segment.setMap(MapDataUtils.testMap(resourceState));
		segment.setStarCount(random.nextInt(100));
		segment.setTotalElevationGain(random.nextFloat() * 10000);
		segment.setUpdatedAt(DateUtils.zonedDateTime());

		return segment;
	}

	/**
	 * Create a list of generated segments
	 *
	 * @param resourceState
	 *            The required resource state of the segments in the list
	 *
	 * @param maxEntries
	 *            The maximum number of entries to include in the list
	 * @return The generated list
	 */
	public static List<StravaSegment> testSegmentList(StravaResourceState resourceState, int maxEntries) {
		final List<StravaSegment> list = new ArrayList<StravaSegment>();
		final int entries = random.nextInt(maxEntries);
		for (int i = 0; i < entries; i++) {
			list.add(testSegment(resourceState));
		}
		return list;
	}

	/**
	 * Validate the structure and content of a response segment
	 *
	 * @param segment
	 *            The segment to be validated
	 */
	@SuppressWarnings("boxing")
	public static void validateSegmentExplorerResponseSegment(final StravaSegmentExplorerResponseSegment segment) {
		assertNotNull(segment);
		assertNotNull(segment.getAvgGrade());
		assertNotNull(segment.getClimbCategory());
		assertFalse(segment.getClimbCategory().equals(StravaClimbCategory.UNKNOWN));
		assertNotNull(segment.getClimbCategoryDesc());
		// assertEquals(segment.getClimbCategoryDesc(),segment.getClimbCategory().getDescription());
		assertNotNull(segment.getDistance());
		assertTrue(segment.getDistance() >= 0);
		assertNotNull(segment.getElevDifference());
		assertTrue(segment.getElevDifference() >= 0);
		assertNotNull(segment.getEndLatlng());
		MapDataUtils.validate(segment.getEndLatlng());
		assertNotNull(segment.getId());
		assertNotNull(segment.getName());
		assertNotNull(segment.getPoints());
		assertNotNull(segment.getStartLatlng());
		MapDataUtils.validate(segment.getStartLatlng());

	}

	/**
	 * Validate the structure and content of a response
	 *
	 * @param response
	 *            The response to be validated
	 */
	public static void validateSegmentExplorerResponse(final StravaSegmentExplorerResponse response) {
		assertNotNull(response);
		assertNotNull(response.getSegments());
		for (final StravaSegmentExplorerResponseSegment segment : response.getSegments()) {
			validateSegmentExplorerResponseSegment(segment);
		}

	}

	/**
	 * Validate the structure and content of a leaderboard entry
	 *
	 * @param entry
	 *            The entry to be validated
	 */
	@SuppressWarnings("boxing")
	public static void validateSegmentLeaderboardEntry(final StravaSegmentLeaderboardEntry entry) {
		assertNotNull(entry);
		assertNotNull(entry.getActivityId());
		if (entry.getAthleteGender() != null) {
			assertFalse(entry.getAthleteGender() == StravaGender.UNKNOWN);
		}
		assertNotNull(entry.getAthleteName());
		assertNotNull(entry.getAthleteProfile());
		if (entry.getAverageHr() != null) {
			assertTrue(entry.getAverageHr() >= 0);
		}
		if (entry.getAverageWatts() != null) {
			assertTrue(entry.getAverageWatts() >= 0);
		}
		assertNotNull(entry.getDistance());
		assertTrue(entry.getDistance() >= 0);
		assertNotNull(entry.getEffortId());
		assertNotNull(entry.getElapsedTime());
		assertTrue(entry.getElapsedTime() >= 0);
		assertNotNull(entry.getMovingTime());
		assertTrue(entry.getMovingTime() >= 0);
		assertTrue(entry.getElapsedTime() >= entry.getMovingTime());
		assertNotNull(entry.getRank());
		assertTrue(entry.getRank() > 0);
		assertNotNull(entry.getStartDate());
		assertNotNull(entry.getStartDateLocal());
	}

	/**
	 * Validate the structure and content of a leaderboard
	 *
	 * @param leaderboard
	 *            the leaderboard to be validated
	 */
	@SuppressWarnings("boxing")
	public static void validateSegmentLeaderboard(final StravaSegmentLeaderboard leaderboard) {
		assertNotNull(leaderboard);
		// Optional (if using API only) assertNotNull(leaderboard.getAthleteEntries());
		// Optional assertNotNull(leaderboard.getEffortCount());
		assertNotNull(leaderboard.getEntryCount());
		if (leaderboard.getEntryCount() != 0) {
			assertNotNull(leaderboard.getEntries());
		}
		// TODO Apparently optional but see https://github.com/danshannon/javastravav3api/issues/22
		// assertNotNull(leaderboard.getKomType());
		assertNotNull(leaderboard.getNeighborhoodCount());
		if (leaderboard.getAthleteEntries() != null) {
			for (final StravaSegmentLeaderboardEntry entry : leaderboard.getAthleteEntries()) {
				validateSegmentLeaderboardEntry(entry);
			}
		}
		for (final StravaSegmentLeaderboardEntry entry : leaderboard.getEntries()) {
			validateSegmentLeaderboardEntry(entry);
		}
	}

	/**
	 * Validate a list of segments
	 *
	 * @param segments
	 *            The list of segments to be validated
	 */
	public static void validateSegmentList(final List<StravaSegment> segments) {
		for (final StravaSegment segment : segments) {
			SegmentDataUtils.validateSegment(segment);
		}

	}

	/**
	 * Validate the structure and content of a segment
	 *
	 * @param segment
	 *            The segment to be validated
	 */
	public static void validateSegment(final StravaSegment segment) {
		assertNotNull(segment);
		validateSegment(segment, segment.getId(), segment.getResourceState());
	}

	/**
	 * Validate the structure and content of a segment
	 *
	 * @param segment
	 *            The segment to be validated
	 * @param id
	 *            The expected identifier of the segment
	 * @param state
	 *            The expected resource state of the segment
	 */
	public static void validateSegment(final StravaSegment segment, final Integer id, final StravaResourceState state) {
		assertNotNull(segment);
		assertEquals(id, segment.getId());
		assertEquals(state, segment.getResourceState());

		if ((state == StravaResourceState.DETAILED) || (state == StravaResourceState.SUMMARY)) {
			assertNotNull(segment.getActivityType());
			assertFalse("Segment " + segment.getId() + " has an unknown activity type", //$NON-NLS-1$ //$NON-NLS-2$
					segment.getActivityType() == StravaSegmentActivityType.UNKNOWN);
			// Optional assertNull(segment.getAthleteCount());

			// Can be null, if the athlete's never done the segment (and it's
			// only returned with starred segments anyway)
			if (segment.getAthletePrEffort() != null) {
				SegmentEffortDataUtils.validateSegmentEffort(segment.getAthletePrEffort(), segment.getAthletePrEffort().getId(), segment.getAthletePrEffort().getResourceState());
			}

			assertNotNull(segment.getAverageGrade());

			// Optional assertNotNull(segment.getCity());

			assertNotNull(segment.getClimbCategory());
			assertFalse(segment.getClimbCategory() == StravaClimbCategory.UNKNOWN);

			// Optional assertNotNull(segment.getCountry());

			assertNotNull(segment.getDistance());
			assertNotNull(segment.getElevationHigh());
			assertNotNull(segment.getElevationLow());
			assertNotNull(segment.getEndLatlng());
			assertNotNull(segment.getMaximumGrade());

			// Optional assertNotNull("Segment " + segment.getId() + " has no
			// name!", segment.getName());

			assertNotNull(segment.getPrivateSegment());
			assertNotNull(segment.getStarred());
			assertNotNull(segment.getStartLatlng());
			// Optional assertNotNull(segment.getState());
			assertNotNull(segment.getHazardous());
			// Optional assertNotNull(segment.getStarCount());
		}

		if (state == StravaResourceState.DETAILED) {
			assertNotNull(segment.getCreatedAt());
			assertNotNull(segment.getEffortCount());
			assertNotNull(segment.getMap());
			MapDataUtils.validateMap(segment.getMap(), segment.getMap().getId(), segment.getMap().getResourceState(), null);
			assertNotNull(segment.getTotalElevationGain());
			assertNotNull(segment.getUpdatedAt());
			return;
		}
		if (state == StravaResourceState.SUMMARY) {
			assertNull(segment.getCreatedAt());
			assertNull(segment.getEffortCount());
			assertNull(segment.getMap());
			assertNull(segment.getTotalElevationGain());
			assertNull(segment.getUpdatedAt());
			return;
		}
		if ((state == StravaResourceState.META) || (state == StravaResourceState.PRIVATE)) {
			assertNull(segment.getActivityType());
			assertNull(segment.getAthleteCount());
			assertNull(segment.getAthletePrEffort());
			assertNull(segment.getAverageGrade());
			assertNull(segment.getCity());
			assertNull(segment.getClimbCategory());
			assertNull(segment.getCountry());
			assertNull(segment.getCreatedAt());
			assertNull(segment.getDistance());
			assertNull(segment.getEffortCount());
			assertNull(segment.getElevationHigh());
			assertNull(segment.getElevationLow());
			assertNull(segment.getEndLatlng());
			assertNull(segment.getHazardous());
			assertNull(segment.getMap());
			assertNull(segment.getMaximumGrade());
			assertNull(segment.getName());
			assertNull(segment.getPrivateSegment());
			assertNull(segment.getStarCount());
			assertNull(segment.getStarred());
			assertNull(segment.getStartLatlng());
			assertNull(segment.getState());
			assertNull(segment.getTotalElevationGain());
			assertNull(segment.getUpdatedAt());
			return;
		}
		fail("Unexpected segment state " + state + " for segment " + segment); //$NON-NLS-1$ //$NON-NLS-2$
	}
}
