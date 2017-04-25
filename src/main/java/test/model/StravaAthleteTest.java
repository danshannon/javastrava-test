package test.model;

import javastrava.model.StravaAthlete;
import test.utils.BeanTest;

/**
 * <p>
 * Tests for StravaAthlete
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class StravaAthleteTest extends BeanTest<StravaAthlete> {

	@Override
	protected Class<StravaAthlete> getClassUnderTest() {
		return StravaAthlete.class;
	}
}
