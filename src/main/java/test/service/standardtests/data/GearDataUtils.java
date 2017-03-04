package test.service.standardtests.data;

import test.utils.TestUtils;

/**
 * <p>
 * Test data utilities for Gear
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class GearDataUtils {

	/**
	 * Identifier of gear that belongs to the authenticated user
	 */
	public static String	GEAR_VALID_ID;
	/**
	 * Invalid gear identifier
	 */
	public static String	GEAR_INVALID_ID;
	/**
	 * Identifier of gear that belongs to someone other than the authenticated athlete
	 */
	public static String	GEAR_OTHER_ATHLETE_ID;

	static {
		GEAR_VALID_ID = TestUtils.stringProperty("test.gearId"); //$NON-NLS-1$
		GEAR_INVALID_ID = TestUtils.stringProperty("test.gearInvalidId"); //$NON-NLS-1$
		GEAR_OTHER_ATHLETE_ID = TestUtils.stringProperty("test.gearOtherAthleteId"); //$NON-NLS-1$

	}
}
