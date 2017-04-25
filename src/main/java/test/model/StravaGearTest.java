package test.model;

import javastrava.model.StravaGear;
import test.utils.BeanTest;

/**
 * <p>
 * Tests for StravaGear
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class StravaGearTest extends BeanTest<StravaGear> {

	@Override
	protected Class<StravaGear> getClassUnderTest() {
		return StravaGear.class;
	}

}
