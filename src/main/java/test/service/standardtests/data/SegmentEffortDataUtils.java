package test.service.standardtests.data;

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

}
