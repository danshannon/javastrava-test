/**
 *
 */
package test.api.rest.segmenteffort.async;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import javastrava.api.v3.model.StravaSegmentEffort;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.rest.API;
import javastrava.api.v3.service.exception.UnauthorizedException;
import test.api.rest.callback.APIGetCallback;
import test.api.rest.segmenteffort.GetSegmentEffortTest;
import test.service.standardtests.data.SegmentEffortDataUtils;
import test.utils.RateLimitedTestRunner;

/**
 * <p>
 * Specific config and tests for {@link API#getSegmentEffortAsync(Long)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class GetSegmentEffortAsyncTest extends GetSegmentEffortTest {
	@Override
	protected APIGetCallback<StravaSegmentEffort, Long> getter() {
		return ((api, id) -> api.getSegmentEffortAsync(id).get());
	}

	/**
	 * Check that an effort on a private activity is not returned
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Override
	public void testGetSegmentEffort_privateActivity() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getSegmentEffortAsync(SegmentEffortDataUtils.SEGMENT_EFFORT_PRIVATE_ACTIVITY_ID).get();
			} catch (final UnauthorizedException e) {
				// expected
				return;
			}
			fail("Returned segment effort for a private activity, without view_private"); //$NON-NLS-1$
		});
	}

	/**
	 * Check that an effort on a private activity is returned with view_private scope
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Override
	public void testGetSegmentEffort_privateActivityViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentEffort effort = apiWithViewPrivate().getSegmentEffortAsync(SegmentEffortDataUtils.SEGMENT_EFFORT_PRIVATE_ACTIVITY_ID).get();
			assertNotNull(effort);
			assertEquals(StravaResourceState.DETAILED, effort.getResourceState());
		});
	}

}
