package test.api.model;

import javastrava.api.v3.model.StravaMapPoint;
import test.utils.BeanTest;

/**
 * <p>
 * Tests for {@link StravaMapPoint}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class StravaMapPointTest extends BeanTest<StravaMapPoint> {

	@Override
	protected Class<StravaMapPoint> getClassUnderTest() {
		return StravaMapPoint.class;
	}

}
