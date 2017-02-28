package test.service.impl.segmenteffortservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import javastrava.api.v3.model.StravaSegmentEffort;
import javastrava.api.v3.model.reference.StravaResourceState;
import test.api.model.StravaSegmentEffortTest;
import test.service.standardtests.GetMethodTest;
import test.service.standardtests.callbacks.GetCallback;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * <p>
 * Specific tests for getSegmentEffort methods
 * </p>
 * 
 * @author Dan Shannon
 *
 */
public class GetSegmentEffortTest extends GetMethodTest<StravaSegmentEffort, Long> {
	/**
	 * Check that an effort on a private activity is not returned
	 *
	 * @throws Exception
	 *             if an unexpected error occurs
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetSegmentEffort_privateActivity() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentEffort effort = TestUtils.strava().getSegmentEffort(new Long(5735858255L));
			assertNotNull(effort);
			assertEquals(StravaResourceState.PRIVATE, effort.getResourceState());
		});
	}

	/**
	 * Check that an effort on a private segment is not returned
	 *
	 * @throws Exception
	 *             if an unexpected error occurs
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetSegmentEffort_privateSegment() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentEffort effort = TestUtils.strava().getSegmentEffort(TestUtils.SEGMENT_EFFORT_PRIVATE_ID);
			assertNotNull(effort);
			assertEquals(StravaResourceState.PRIVATE, effort.getResourceState());
		});
	}

	@Override
	protected Long getIdValid() {
		return TestUtils.SEGMENT_EFFORT_VALID_ID;
	}

	@Override
	protected Long getIdInvalid() {
		return TestUtils.SEGMENT_EFFORT_INVALID_ID;
	}

	@Override
	protected Long getIdPrivate() {
		return TestUtils.SEGMENT_EFFORT_PRIVATE_ID;
	}

	@Override
	protected Long getIdPrivateBelongsToOtherUser() {
		return null;
	}

	@Override
	protected GetCallback<StravaSegmentEffort, Long> getter() throws Exception {
		return ((strava, id) -> strava.getSegmentEffort(id));
	}

	@Override
	protected void validate(StravaSegmentEffort object) {
		StravaSegmentEffortTest.validateSegmentEffort(object);
	}

}
