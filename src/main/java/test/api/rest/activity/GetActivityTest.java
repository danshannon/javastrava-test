package test.api.rest.activity;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaSegmentEffort;
import javastrava.api.v3.rest.API;
import test.api.model.StravaActivityTest;
import test.api.rest.APIGetTest;
import test.api.rest.callback.APIGetCallback;
import test.service.standardtests.data.ActivityDataUtils;
import test.utils.RateLimitedTestRunner;

/**
 * <p>
 * Specific tests for {@link API#getActivity(Long, Boolean)} methods
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class GetActivityTest extends APIGetTest<StravaActivity, Long> {
	@Override
	protected Long invalidId() {
		return ActivityDataUtils.ACTIVITY_INVALID;
	}

	@Override
	protected Long privateId() {
		return ActivityDataUtils.ACTIVITY_PRIVATE;
	}

	@Override
	protected Long privateIdBelongsToOtherUser() {
		return ActivityDataUtils.ACTIVITY_PRIVATE_OTHER_USER;
	}

	/**
	 * <p>
	 * Test retrieval of a known {@link StravaActivity}, complete with all {@link StravaSegmentEffort efforts}
	 * </p>
	 *
	 * @throws Exception
	 *             if test fails for an unexpected reason
	 */
	@Test
	public void testGetActivity_run() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaActivity activity = this.getter().get(api(), ActivityDataUtils.ACTIVITY_RUN_WITH_SEGMENTS);
			assertNotNull(activity);
			validate(activity);
		});
	}

	/**
	 * <p>
	 * Test retrieval of a run which belongs to someone else
	 * </p>
	 *
	 * @throws Exception
	 *             if test fails for an unexpected reason
	 */
	@Test
	public void testGetActivity_runOtherUser() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaActivity activity = this.getter().get(api(), ActivityDataUtils.ACTIVITY_RUN_OTHER_USER);
			assertNotNull(activity);
			StravaActivityTest.validate(activity);
		});
	}

	@Override
	protected void validate(final StravaActivity activity) throws Exception {
		StravaActivityTest.validate(activity);
	}

	@Override
	protected Long validId() {
		return ActivityDataUtils.ACTIVITY_FOR_AUTHENTICATED_USER;
	}

	@Override
	protected Long validIdBelongsToOtherUser() {
		return ActivityDataUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER;
	}

	@Override
	protected APIGetCallback<StravaActivity, Long> getter() {
		return ((api, id) -> api.getActivity(id, Boolean.FALSE));
	}
}
