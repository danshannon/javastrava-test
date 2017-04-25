package test.model;

import javastrava.model.StravaActivityZone;
import test.utils.BeanTest;

/**
 * @author Dan Shannon
 *
 */
public class StravaActivityZoneTest extends BeanTest<StravaActivityZone> {

	@Override
	protected Class<StravaActivityZone> getClassUnderTest() {
		return StravaActivityZone.class;
	}

}
