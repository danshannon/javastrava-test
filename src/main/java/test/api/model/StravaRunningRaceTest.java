/**
 *
 */
package test.api.model;

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
	 * @see test.utils.BeanTest#getClassUnderTest()
	 */
	@Override
	protected Class<StravaRunningRace> getClassUnderTest() {
		return StravaRunningRace.class;
	}

}
