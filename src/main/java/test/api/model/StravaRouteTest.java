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

	/**
	 * Validate structure and content of a StravaRoute
	 *
	 * @param route
	 *            The route to validate
	 */
	public static void validate(StravaRoute route) {
		// TODO
	}

	@Override
	protected Class<StravaRoute> getClassUnderTest() {
		return StravaRoute.class;
	}

}
