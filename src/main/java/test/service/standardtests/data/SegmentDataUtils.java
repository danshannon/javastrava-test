package test.service.standardtests.data;

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
}
