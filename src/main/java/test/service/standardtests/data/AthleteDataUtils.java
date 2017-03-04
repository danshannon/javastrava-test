package test.service.standardtests.data;

import test.utils.TestUtils;

/**
 * <p>
 * Test data utilities for athletes
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class AthleteDataUtils {

	/**
	 * Identifier of the authenticated athlete
	 */
	public static Integer ATHLETE_AUTHENTICATED_ID;

	/**
	 * Identifier of an athlete who isn't the authenticated athlete
	 */
	public static Integer ATHLETE_VALID_ID;

	/**
	 * Invalid athlete identifier
	 */
	public static Integer ATHLETE_INVALID_ID;

	/**
	 * Identifier of an athlete with no KOM's/CR's
	 */
	public static Integer ATHLETE_WITHOUT_KOMS;

	/**
	 * Identifier of an athlete who is not following anybody
	 */
	public static Integer ATHLETE_WITHOUT_FRIENDS;

	/**
	 * Identifier of an athlete who has flagged their profile as private
	 */
	public static Integer ATHLETE_PRIVATE_ID;

	static {
		ATHLETE_AUTHENTICATED_ID = TestUtils.integerProperty("test.authenticatedAthleteId"); //$NON-NLS-1$
		ATHLETE_VALID_ID = TestUtils.integerProperty("test.athleteId"); //$NON-NLS-1$
		ATHLETE_INVALID_ID = TestUtils.integerProperty("test.athleteInvalidId"); //$NON-NLS-1$
		ATHLETE_WITHOUT_KOMS = TestUtils.integerProperty("test.athleteWithoutKOMs"); //$NON-NLS-1$
		ATHLETE_WITHOUT_FRIENDS = TestUtils.integerProperty("test.athleteWithoutFriends"); //$NON-NLS-1$
		ATHLETE_PRIVATE_ID = TestUtils.integerProperty("test.athletePrivate"); //$NON-NLS-1$

	}
}
