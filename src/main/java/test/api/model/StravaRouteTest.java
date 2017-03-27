package test.api.model;

import javastrava.api.v3.model.StravaRoute;
import test.utils.BeanTest;

/**
 * <p>
 * Tests for {@link StravaRoute}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class StravaRouteTest extends BeanTest<StravaRoute> {

	@Override
	protected Class<StravaRoute> getClassUnderTest() {
		return StravaRoute.class;
	}

}
