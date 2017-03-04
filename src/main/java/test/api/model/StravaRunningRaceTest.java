/**
 *
 */
package test.api.model;

import static org.junit.Assert.assertNotNull;

import javastrava.api.v3.model.StravaRunningRace;
import test.utils.BeanTest;

/**
 * <p>
 * Model tests for StravaRunningRace
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class StravaRunningRaceTest extends BeanTest<StravaRunningRace> {

	/**
	 * <p>
	 * Validate that the contents of the race are valid
	 * </p>
	 *
	 * @param race
	 *            The race to be validated
	 */
	public static void validate(final StravaRunningRace race) {
		assertNotNull(race.getResourceState());
	}

	/**
	 * @see test.utils.BeanTest#getClassUnderTest()
	 */
	@Override
	protected Class<StravaRunningRace> getClassUnderTest() {
		return StravaRunningRace.class;
	}

}
