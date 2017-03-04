package test.service.standardtests.data;

import test.utils.TestUtils;

/**
 * <p>
 * Test data utilities for running races
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class RunningRaceDataUtils {

	/**
	 * Identifier of a valid running race
	 */
	public static Integer	RUNNING_RACE_VALID_ID;
	/**
	 * Invalid identifier of a running race
	 */
	public static Integer	RUNNING_RACE_INVALID_ID;

	static {
		RUNNING_RACE_VALID_ID = TestUtils.integerProperty("test.raceId"); //$NON-NLS-1$
		RUNNING_RACE_INVALID_ID = TestUtils.integerProperty("test.raceInvalidId"); //$NON-NLS-1$

	}

}
