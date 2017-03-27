package test.api.model;

import javastrava.api.v3.model.StravaSegment;
import test.utils.BeanTest;

/**
 * <p>
 * Tests for {@link StravaSegment}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class StravaSegmentTest extends BeanTest<StravaSegment> {

	@Override
	protected Class<StravaSegment> getClassUnderTest() {
		return StravaSegment.class;
	}
}
