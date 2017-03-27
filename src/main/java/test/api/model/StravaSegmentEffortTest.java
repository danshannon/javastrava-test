package test.api.model;

import javastrava.api.v3.model.StravaSegmentEffort;
import test.utils.BeanTest;

/**
 * <p>
 * Tests for {@link StravaSegmentEffort}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class StravaSegmentEffortTest extends BeanTest<StravaSegmentEffort> {

	@Override
	protected Class<StravaSegmentEffort> getClassUnderTest() {
		return StravaSegmentEffort.class;
	}
}
