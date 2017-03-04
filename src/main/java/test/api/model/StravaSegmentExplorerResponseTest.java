package test.api.model;

import static org.junit.Assert.assertNotNull;

import javastrava.api.v3.model.StravaSegmentExplorerResponse;
import javastrava.api.v3.model.StravaSegmentExplorerResponseSegment;
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

	/**
	 * Validate the structure and content of a response
	 * 
	 * @param response
	 *            The response to be validated
	 */
	public static void validate(final StravaSegmentExplorerResponse response) {
		assertNotNull(response);
		assertNotNull(response.getSegments());
		for (final StravaSegmentExplorerResponseSegment segment : response.getSegments()) {
			StravaSegmentExplorerResponseSegmentTest.validate(segment);
		}

	}

	@Override
	protected Class<StravaSegmentExplorerResponse> getClassUnderTest() {
		return StravaSegmentExplorerResponse.class;
	}
}
