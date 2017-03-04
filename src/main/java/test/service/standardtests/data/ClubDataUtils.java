package test.service.standardtests.data;

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
}
