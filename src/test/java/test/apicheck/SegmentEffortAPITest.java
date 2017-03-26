package test.apicheck;

import org.junit.Test;

import javastrava.api.v3.model.StravaSegmentEffort;
import javastrava.api.v3.rest.API;
import javastrava.api.v3.rest.SegmentEffortAPI;
import retrofit.client.Response;
import test.service.standardtests.data.SegmentEffortDataUtils;
import test.utils.TestUtils;

/**
 * @author Dan Shannon
 *
 */
public class SegmentEffortAPITest {
	private static SegmentEffortAPI api() {
		return API.instance(SegmentEffortAPI.class, TestUtils.getValidToken());
	}

	/**
	 * Test for {@link API#getSegmentEffort(Long)}
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testAPI_getSegmentEffort() throws Exception {
		final Response response = api().getSegmentEffortRaw(SegmentEffortDataUtils.SEGMENT_EFFORT_VALID_ID);
		ResponseValidator.validate(response, StravaSegmentEffort.class, "getSegmentEffort"); //$NON-NLS-1$
	}

}
