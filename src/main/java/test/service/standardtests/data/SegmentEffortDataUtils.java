package test.service.standardtests.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jfairy.Fairy;
import org.jfairy.producer.text.TextProducer;

import javastrava.api.v3.model.StravaAchievement;
import javastrava.api.v3.model.StravaAthleteSegmentStats;
import javastrava.api.v3.model.StravaSegmentEffort;
import javastrava.api.v3.model.reference.StravaResourceState;
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

	public static List<StravaSegmentEffort> testSegmentEffortList(StravaResourceState resourceState) {
		final List<StravaSegmentEffort> list = new ArrayList<>();
		final int entries = random.nextInt(10000);
		for (int i = 0; i < entries; i++) {
			list.add(testSegmentEffort(resourceState));
		}
		return list;
	}

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
}
