package test.service.standardtests.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jfairy.Fairy;
import org.jfairy.producer.text.TextProducer;

import javastrava.api.v3.model.StravaAchievement;
import javastrava.api.v3.model.StravaAthleteSegmentStats;
import javastrava.api.v3.model.StravaSegmentEffort;
import javastrava.api.v3.model.reference.StravaActivityType;
import javastrava.api.v3.model.reference.StravaResourceState;
import test.api.model.StravaAchievementTest;
import test.utils.TestUtils;

/**
 * <p>
 * Test data utilities for segment efforts
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class SegmentEffortDataUtils {

	/**
	 * Identifier of a valid segment effort that belongs to the authenticated user
	 */
	public static Long	SEGMENT_EFFORT_VALID_ID;
	/**
	 * Invalid segment effort identifier
	 */
	public static Long	SEGMENT_EFFORT_INVALID_ID;
	/**
	 * Identifier of a segment effort on a private segment belonging to the authenticated user
	 */
	public static Long	SEGMENT_EFFORT_PRIVATE_ID;
	/**
	 * Identifier of a segment effort on a private segment belonging to someone other than the authenticated user
	 */
	public static Long	SEGMENT_EFFORT_OTHER_USER_PRIVATE_ID;
	/**
	 * Identifier of a segment effort on a private activity belonging to the authenticated user
	 */
	public static Long	SEGMENT_EFFORT_PRIVATE_ACTIVITY_ID;

	static {
		SEGMENT_EFFORT_INVALID_ID = TestUtils.longProperty("test.segmentEffortInvalidId"); //$NON-NLS-1$
		SEGMENT_EFFORT_VALID_ID = TestUtils.longProperty("test.segmentEffortId"); //$NON-NLS-1$
		SEGMENT_EFFORT_PRIVATE_ID = TestUtils.longProperty("test.segmentEffortPrivateId"); //$NON-NLS-1$
		SEGMENT_EFFORT_OTHER_USER_PRIVATE_ID = TestUtils.longProperty("test.segmentEffortOtherUserPrivateId"); //$NON-NLS-1$
		SEGMENT_EFFORT_PRIVATE_ACTIVITY_ID = TestUtils.longProperty("test.segmentEffortPrivateActivityId"); //$NON-NLS-1$
	}

	private static Random random = new Random();

	private static TextProducer text = Fairy.create().textProducer();

	/**
	 * Generate a list of randomised segment efforts with the required resource state
	 *
	 * @param resourceState
	 *            The required resource state
	 * @return The generated list
	 */
	public static List<StravaSegmentEffort> testSegmentEffortList(StravaResourceState resourceState) {
		final List<StravaSegmentEffort> list = new ArrayList<>();
		final int entries = random.nextInt(10000);
		for (int i = 0; i < entries; i++) {
			list.add(testSegmentEffort(resourceState));
		}
		return list;
	}

	/**
	 * Generate a randomised segment effort with the required resource state
	 *
	 * @param resourceState
	 *            The required resource state
	 * @return The generated segment effort
	 */
	@SuppressWarnings("boxing")
	public static StravaSegmentEffort testSegmentEffort(StravaResourceState resourceState) {
		final StravaSegmentEffort effort = new StravaSegmentEffort();

		effort.setId(random.nextLong());
		effort.setResourceState(resourceState);

		// If this is a META or PRIVATE effort, return now
		if ((resourceState == StravaResourceState.META) || (resourceState == StravaResourceState.PRIVATE)) {
			return effort;
		}

		// Add the attributes common to SUMMARY and DETAILED efforts
		effort.setAchievements(testAchievementsList());
		effort.setActivity(ActivityDataUtils.testActivity(StravaResourceState.META));
		effort.setAthlete(AthleteDataUtils.testAthlete(StravaResourceState.META));
		effort.setAthleteSegmentStats(testAthleteSegmentStats());
		effort.setAverageCadence(random.nextFloat() * 100);
		effort.setAverageHeartrate(random.nextFloat() * 175);
		effort.setAverageWatts(random.nextFloat() * 500);
		effort.setElapsedTime(random.nextInt(10000));
		effort.setEndIndex(random.nextInt(10000));
		effort.setHidden(random.nextBoolean());
		effort.setIsKom(random.nextBoolean());
		effort.setKomRank(random.nextInt(100));
		effort.setMaxHeartrate(random.nextInt(220));
		effort.setMovingTime(random.nextInt(10000));
		effort.setName(text.sentence());
		effort.setPrRank(random.nextInt(100));
		effort.setSegment(SegmentDataUtils.testSegment(resourceState == StravaResourceState.SUMMARY ? StravaResourceState.META : StravaResourceState.SUMMARY));
		effort.setStartDate(DateUtils.zonedDateTime());
		effort.setStartDateLocal(DateUtils.localDateTime());
		effort.setStartIndex(random.nextInt(10000));

		return effort;
	}

	@SuppressWarnings("boxing")
	private static StravaAthleteSegmentStats testAthleteSegmentStats() {
		final StravaAthleteSegmentStats stats = new StravaAthleteSegmentStats();

		stats.setEffortCount(random.nextInt(100));
		stats.setPrDate(DateUtils.localDate());
		stats.setPrElapsedTime(random.nextInt(10000));

		return stats;
	}

	private static List<StravaAchievement> testAchievementsList() {
		final List<StravaAchievement> list = new ArrayList<StravaAchievement>();
		final int entries = random.nextInt(100);
		for (int i = 0; i < entries; i++) {
			list.add(testAchievement());
		}
		return list;
	}

	@SuppressWarnings("boxing")
	private static StravaAchievement testAchievement() {
		final StravaAchievement achievement = new StravaAchievement();

		achievement.setRank(random.nextInt(10000));
		achievement.setType(text.word());
		achievement.setTypeId(random.nextInt(2));

		return achievement;
	}

	/**
	 * Validate a list of efforts
	 *
	 * @param list
	 *            The list to be validated
	 */
	public static void validateSegmentEffortList(final List<StravaSegmentEffort> list) {
		for (final StravaSegmentEffort effort : list) {
			validateSegmentEffort(effort);
		}

	}

	/**
	 * Validate the structure and content of an effort
	 *
	 * @param effort
	 *            The effort to be validated
	 */
	public static void validateSegmentEffort(final StravaSegmentEffort effort) {
		SegmentEffortDataUtils.validateSegmentEffort(effort, effort.getId(), effort.getResourceState());
	}

	/**
	 * Validate the structure and content of an effort
	 *
	 * @param effort
	 *            The effort to be validated
	 * @param id
	 *            The expected identifier of the effort
	 * @param state
	 *            The expected resource state of the effort
	 */
	@SuppressWarnings("boxing")
	public static void validateSegmentEffort(final StravaSegmentEffort effort, final Long id, final StravaResourceState state) {
		assertNotNull(effort);
		assertEquals(id, effort.getId());
		assertEquals(state, effort.getResourceState());

		if ((state == StravaResourceState.DETAILED) || (state == StravaResourceState.SUMMARY)) {
			if (effort.getActivity() != null) {
				ActivityDataUtils.validate(effort.getActivity(), effort.getActivity().getId(), effort.getActivity().getResourceState());
			}
			if (effort.getAthlete() != null) {
				AthleteDataUtils.validateAthlete(effort.getAthlete(), effort.getAthlete().getId(), effort.getAthlete().getResourceState());
			}
			if ((effort.getActivity() != null) && (effort.getActivity().getAthlete() != null)) {
				assertEquals(effort.getActivity().getAthlete().getId(), effort.getAthlete().getId());
			}
			// Only returned for rides, and then only if it was measured
			if (effort.getActivity() != null) {
				if (effort.getActivity().getType() == StravaActivityType.RIDE) {
					if (effort.getAverageCadence() != null) {
						assertTrue(effort.getAverageCadence() >= 0);
					}
					if (effort.getAverageWatts() != null) {
						assertTrue(effort.getAverageWatts() >= 0);
					}
				} else {
					// If we can't tell what sort of activity it was, then can't
					// tell if average cadence/watts can be set or not
					if (effort.getActivity().getResourceState() != StravaResourceState.META) {
						assertNull(effort.getAverageCadence());
						assertNull(effort.getAverageWatts());
					}
				}
			}
			// If returned then there should be a max heartrate too
			if (effort.getAverageHeartrate() != null) {
				assertTrue(effort.getAverageHeartrate() >= 0);
				assertTrue(effort.getMaxHeartrate() >= 0);
				assertTrue(effort.getMaxHeartrate() >= effort.getAverageHeartrate());
			}
			// If there's a max heartrate, then there should be an average
			if (effort.getMaxHeartrate() != null) {
				assertNotNull(effort.getAverageHeartrate());
			}
			assertNotNull(effort.getDistance());
			assertNotNull(effort.getElapsedTime());
			// Only returned if in the top 10 at the time
			if (effort.getKomRank() != null) {
				assertTrue(effort.getKomRank() <= 10);
				assertTrue(effort.getKomRank() >= 1);
			}
			// Only returned if it's one of the top 3 efforts for the athlete
			if (effort.getPrRank() != null) {
				assertTrue(effort.getPrRank() > 0);
				assertTrue(effort.getPrRank() < 4);
			}
			if (effort.getEndIndex() != null) {
				assertNotNull(effort.getStartIndex());
				assertTrue(effort.getEndIndex() > effort.getStartIndex());
			}
			if (effort.getStartIndex() != null) {
				assertNotNull(effort.getEndIndex());
			}
			if (effort.getSegment() != null) {
				SegmentDataUtils.validateSegment(effort.getSegment());
			}
			// Optional (only returned with activities)
			// assertNotNull(effort.getHidden());

			// Only returned for starred segments
			// assertNotNull(effort.getIsKom());

			assertNotNull(effort.getStartDate());
			assertNotNull(effort.getStartDateLocal());

			// Not always returned
			if (effort.getAthleteSegmentStats() != null) {
				AthleteDataUtils.validateAthleteSegmentStats(effort.getAthleteSegmentStats());
			}
			if (effort.getAchievements() != null) {
				for (final StravaAchievement achievement : effort.getAchievements()) {
					StravaAchievementTest.validate(achievement);
				}
			}

		}

		if (state == StravaResourceState.DETAILED) {
			assertNotNull(effort.getEndIndex());
			assertNotNull(effort.getMovingTime());
			assertNotNull(effort.getName());
			assertNotNull(effort.getSegment());
			assertNotNull(effort.getStartIndex());
			return;
		}
		if (state == StravaResourceState.SUMMARY) {
			// Optional (not returned with athlete_pr_effort in starred
			// segments)
			// assertNotNull(effort.getEndIndex());

			// Optional (not returned with athlete_pr_effort in starred
			// segments)
			// assertNotNull(effort.getMovingTime());

			// Optional (not returned with athlete_pr_effort in starred
			// segments)
			// assertNotNull(effort.getName());

			// Optional (not returned with athlete_pr_effort in starred
			// segments)
			// assertNotNull(effort.getSegment());

			// Optional (not returned with athlete_pr_effort in starred
			// segments)
			// assertNotNull(effort.getStartIndex());
			return;
		}
		if ((state == StravaResourceState.META) || (state == StravaResourceState.PRIVATE)) {
			assertNotNull(effort.getId());
			assertNull(effort.getActivity());
			assertNull(effort.getAthlete());
			assertNull(effort.getAverageCadence());
			assertNull(effort.getAverageHeartrate());
			assertNull(effort.getAverageWatts());
			assertNull(effort.getDistance());
			assertNull(effort.getElapsedTime());
			assertNull(effort.getEndIndex());
			assertNull(effort.getHidden());
			assertNull(effort.getIsKom());
			assertNull(effort.getKomRank());
			assertNull(effort.getMaxHeartrate());
			assertNull(effort.getMovingTime());
			assertNull(effort.getName());
			assertNull(effort.getPrRank());
			assertNull(effort.getSegment());
			assertNull(effort.getStartDate());
			assertNull(effort.getStartDateLocal());
			assertNull(effort.getStartIndex());
			return;
		}
		fail("Unexpected state for segment effort " + state + " " + effort); //$NON-NLS-1$ //$NON-NLS-2$
	}
}
