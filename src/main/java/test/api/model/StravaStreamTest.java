package test.api.model;

import javastrava.api.v3.model.StravaStream;
import test.utils.BeanTest;

/**
 * <p>
 * Tests for {@link StravaStream}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class StravaStreamTest extends BeanTest<StravaStream> {

	@Override
	protected Class<StravaStream> getClassUnderTest() {
		return StravaStream.class;
	}

}
