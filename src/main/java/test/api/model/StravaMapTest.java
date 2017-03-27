package test.api.model;

import javastrava.api.v3.model.StravaMap;
import test.utils.BeanTest;

/**
 * <p>
 * Tests for {@link StravaMap}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class StravaMapTest extends BeanTest<StravaMap> {

	@Override
	protected Class<StravaMap> getClassUnderTest() {
		return StravaMap.class;
	}
}
