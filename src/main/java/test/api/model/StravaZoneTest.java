package test.api.model;

import javastrava.api.v3.model.StravaZone;
import test.utils.BeanTest;

/**
 * Bean test for {@link StravaZone}
 *
 * @author Dan Shannon
 *
 */
public class StravaZoneTest extends BeanTest<StravaZone> {

	@Override
	protected Class<StravaZone> getClassUnderTest() {
		return StravaZone.class;
	}

}
