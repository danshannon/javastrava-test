package test.api.model;

import javastrava.api.v3.model.StravaGear;
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
