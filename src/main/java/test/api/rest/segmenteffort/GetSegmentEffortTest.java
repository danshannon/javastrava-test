package test.api.rest.segmenteffort;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import javastrava.api.v3.model.StravaSegmentEffort;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.service.exception.UnauthorizedException;
import test.api.model.StravaSegmentEffortTest;
import test.api.rest.APIGetTest;
import test.api.rest.callback.APIGetCallback;
import test.issues.strava.Issue78;
import test.service.standardtests.data.SegmentEffortDataUtils;
import test.utils.RateLimitedTestRunner;

public class GetSegmentEffortTest extends APIGetTest<StravaSegmentEffort, Long> {
	/**
	 * @see test.api.rest.APIGetTest#invalidId()
	 */
	@Override
	protected Long invalidId() {
		return SegmentEffortDataUtils.SEGMENT_EFFORT_INVALID_ID;
	}

	/**
	 * @see test.api.rest.APIGetTest#privateId()
	 */
	@Override
	protected Long privateId() {
		return SegmentEffortDataUtils.SEGMENT_EFFORT_PRIVATE_ID;
	}

	/**
	 * @see test.api.rest.APIGetTest#privateIdBelongsToOtherUser()
	 */
	@Override
	protected Long privateIdBelongsToOtherUser() {
		return SegmentEffortDataUtils.SEGMENT_EFFORT_OTHER_USER_PRIVATE_ID;
	}

	/**
	 * Check that an effort on a private activity is not returned
	 *
	 * @throws Exception
	 */
	@Test
	public void testGetSegmentEffort_privateActivity() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getSegmentEffort(SegmentEffortDataUtils.SEGMENT_EFFORT_PRIVATE_ACTIVITY_ID);
			} catch (final UnauthorizedException e) {
				// expected
				return;
			}
			fail("Returned segment effort for a private activity, without view_private");
		});
	}

	/**
	 * Check that an effort on a private activity is returned with view_private scope
	 *
	 * @throws Exception
	 */
	@Test
	public void testGetSegmentEffort_privateActivityViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentEffort effort = apiWithViewPrivate().getSegmentEffort(SegmentEffortDataUtils.SEGMENT_EFFORT_PRIVATE_ACTIVITY_ID);
			assertNotNull(effort);
			assertEquals(StravaResourceState.DETAILED, effort.getResourceState());
		});
	}

	/**
	 * Check that an effort on a private segment is not returned
	 *
	 * @throws Exception
	 */
	@Override
	public void get_private() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// TODO This is a workaround for issue javastravav3api#78
			if (new Issue78().isIssue()) {
				return;
			}
			// End of workaround
			super.get_private();

		});
	}

	/**
	 * @see test.api.rest.APITest#validate(java.lang.Object)
	 */
	@Override
	protected void validate(final StravaSegmentEffort result) throws Exception {
		StravaSegmentEffortTest.validateSegmentEffort(result);

	}

	/**
	 * @see test.api.rest.APIGetTest#validId()
	 */
	@Override
	protected Long validId() {
		return SegmentEffortDataUtils.SEGMENT_EFFORT_VALID_ID;
	}

	/**
	 * @see test.api.rest.APIGetTest#validIdBelongsToOtherUser()
	 */
	@Override
	protected Long validIdBelongsToOtherUser() {
		return null;
	}

	/**
	 * @see test.api.rest.APIGetTest#get_privateWithoutViewPrivate()
	 */
	@Override
	public void get_privateWithoutViewPrivate() throws Exception {
		// TODO Workaround for issue 78
		if (new Issue78().isIssue()) {
			return;
		}
		super.get_privateWithoutViewPrivate();
	}

	@Override
	protected APIGetCallback<StravaSegmentEffort, Long> getter() {
		return ((api, id) -> api.getSegmentEffort(id));
	}

}
