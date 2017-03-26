package test.apicheck;

import org.junit.Test;

import javastrava.api.v3.model.StravaStream;
import javastrava.api.v3.rest.API;
import javastrava.api.v3.rest.StreamAPI;
import retrofit.client.Response;
import test.service.standardtests.data.ActivityDataUtils;
import test.service.standardtests.data.RouteDataUtils;
import test.service.standardtests.data.SegmentDataUtils;
import test.service.standardtests.data.SegmentEffortDataUtils;
import test.utils.TestUtils;

/**
 * <p>
 * Check that the API returns no more data than that which is configured in the model
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class StreamAPITest {
	private static StreamAPI api() {
		return API.instance(StreamAPI.class, TestUtils.getValidToken());
	}

	/**
	 * Test the
	 * {@link StreamAPI#getActivityStreamsRaw(Long, String, javastrava.api.v3.model.reference.StravaStreamResolutionType, javastrava.api.v3.model.reference.StravaStreamSeriesDownsamplingType)}
	 * endpoint
	 *
	 * @throws Exception
	 *             if the test fails for an unexpected reason
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetActivityStreams() throws Exception {
		final Response response = api().getActivityStreamsRaw(ActivityDataUtils.ACTIVITY_FOR_AUTHENTICATED_USER, null, null, null);
		ResponseValidator.validate(response, StravaStream.class, "getActivityStreams"); //$NON-NLS-1$
	}

	/**
	 * Test the {@link StreamAPI#getEffortStreamsRaw(Long, String, javastrava.api.v3.model.reference.StravaStreamResolutionType, javastrava.api.v3.model.reference.StravaStreamSeriesDownsamplingType)}
	 * endpoint
	 *
	 * @throws Exception
	 *             if the test fails for an unexpected reason
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetEffortStreams() throws Exception {
		final Response response = api().getEffortStreamsRaw(SegmentEffortDataUtils.SEGMENT_EFFORT_VALID_ID, null, null, null);
		ResponseValidator.validate(response, StravaStream.class, "getEffortStreams"); //$NON-NLS-1$
	}

	/**
	 * Test the {@link StreamAPI#getRouteStreamsRaw(Integer)} endpoint
	 *
	 * @throws Exception
	 *             if the test fails for an unexpected reason
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetRouteStreams() throws Exception {
		final Response response = api().getRouteStreamsRaw(RouteDataUtils.ROUTE_VALID_ID);
		ResponseValidator.validate(response, StravaStream.class, "getRouteStreams"); //$NON-NLS-1$
	}

	/**
	 * Test the
	 * {@link StreamAPI#getSegmentStreamsRaw(Integer, String, javastrava.api.v3.model.reference.StravaStreamResolutionType, javastrava.api.v3.model.reference.StravaStreamSeriesDownsamplingType)}
	 * endpoint
	 * 
	 * @throws Exception
	 *             if the test fails for an unexpected reason
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetSegmentStreams() throws Exception {
		final Response response = api().getSegmentStreamsRaw(SegmentDataUtils.SEGMENT_VALID_ID, null, null, null);
		ResponseValidator.validate(response, StravaStream.class, "getSegmentStreams"); //$NON-NLS-1$
	}
}
