package test.api.model;

import static org.junit.Assert.assertNotNull;
import javastrava.api.v3.model.StravaSegmentExplorerResponse;
import javastrava.api.v3.model.StravaSegmentExplorerResponseSegment;
import test.utils.BeanTest;

/**
 * @author dshannon
 *
 */
public class StravaSegmentExplorerResponseTest extends BeanTest<StravaSegmentExplorerResponse> {

	@Override
	protected Class<StravaSegmentExplorerResponse> getClassUnderTest() {
		return StravaSegmentExplorerResponse.class;
	}

	public static void validate(final StravaSegmentExplorerResponse response) {
		assertNotNull(response);
		assertNotNull(response.getSegments());
		for (final StravaSegmentExplorerResponseSegment segment : response.getSegments()) {
			StravaSegmentExplorerResponseSegmentTest.validate(segment);
		}

	}
}
