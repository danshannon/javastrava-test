package test.model;

import javastrava.model.StravaResponse;
import test.utils.BeanTest;

/**
 * <p>
 * Tests for {@link StravaResponse}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class StravaResponseTest extends BeanTest<StravaResponse> {

	@Override
	protected Class<StravaResponse> getClassUnderTest() {
		return StravaResponse.class;
	}

}
