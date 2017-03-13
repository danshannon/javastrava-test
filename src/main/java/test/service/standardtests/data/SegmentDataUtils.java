package test.service.standardtests.data;

import java.util.Random;

import org.jfairy.Fairy;
import org.jfairy.producer.text.TextProducer;

import javastrava.api.v3.model.StravaSegment;
import javastrava.api.v3.model.reference.StravaResourceState;
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
}
