package test.api.rest.segmenteffort;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import javastrava.api.v3.model.StravaSegmentEffort;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.rest.API;
import javastrava.api.v3.service.exception.UnauthorizedException;
import test.api.rest.APIGetTest;
import test.api.rest.callback.APIGetCallback;
import test.issues.strava.Issue78;
import test.service.standardtests.data.SegmentEffortDataUtils;
import test.utils.RateLimitedTestRunner;

/**
 * <p>
 * Specific config and tests for {@link API#getSegmentEffort(Long)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class GetSegmentEffortTest extends APIGetTest<StravaSegmentEffort, Long> {
	@Override
	public void get_private() throws Exception {
		RateLimitedTestRunner.run(() -> {
			if (new Issue78().isIssue()) {
				return;
			}

			super.get_private();

		});
	}

	@Override
	public void get_privateWithoutViewPrivate() throws Exception {
		if (new Issue78().isIssue()) {
			return;
		}

		super.get_privateWithoutViewPrivate();
	}

	@Override
	protected APIGetCallback<StravaSegmentEffort, Long> getter() {
		return ((api, id) -> api.getSegmentEffort(id));
	}

	@Override
	protected Long invalidId() {
		return SegmentEffortDataUtils.SEGMENT_EFFORT_INVALID_ID;
	}

	@Override
	protected Long privateId() {
		return SegmentEffortDataUtils.SEGMENT_EFFORT_PRIVATE_ID;
	}

	@Override
	protected Long privateIdBelongsToOtherUser() {
		return SegmentEffortDataUtils.SEGMENT_EFFORT_OTHER_USER_PRIVATE_ID;
	}

	/**
	 * Check that an effort on a private activity is not returned
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetSegmentEffort_privateActivity() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getSegmentEffort(SegmentEffortDataUtils.SEGMENT_EFFORT_PRIVATE_ACTIVITY_ID);
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
	@SuppressWarnings("static-method")
	@Test
	public void testGetSegmentEffort_privateActivityViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentEffort effort = apiWithViewPrivate().getSegmentEffort(SegmentEffortDataUtils.SEGMENT_EFFORT_PRIVATE_ACTIVITY_ID);
			assertNotNull(effort);
			assertEquals(StravaResourceState.DETAILED, effort.getResourceState());
		});
	}

	@Override
	protected void validate(final StravaSegmentEffort result) throws Exception {
		SegmentEffortDataUtils.validateSegmentEffort(result);

	}

	@Override
	protected Long validId() {
		return SegmentEffortDataUtils.SEGMENT_EFFORT_VALID_ID;
	}

	@Override
	protected Long validIdBelongsToOtherUser() {
		return null;
	}
}
