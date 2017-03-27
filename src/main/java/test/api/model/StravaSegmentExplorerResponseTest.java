package test.api.model;

import javastrava.api.v3.model.StravaSegmentExplorerResponse;
import test.utils.BeanTest;

/**
 * <p>
 * Tests for {@link StravaSegmentExplorerResponse}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class StravaSegmentExplorerResponseTest extends BeanTest<StravaSegmentExplorerResponse> {

	@Override
	protected Class<StravaSegmentExplorerResponse> getClassUnderTest() {
		return StravaSegmentExplorerResponse.class;
	}
}
