package test.service.impl.activityservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaSegmentEffort;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.service.exception.UnauthorizedException;
import javastrava.api.v3.service.impl.StravaServiceImpl;
import test.api.model.StravaActivityTest;
import test.service.standardtests.UpdatableGetMethodTest;
import test.service.standardtests.callbacks.CreateCallback;
import test.service.standardtests.callbacks.DeleteCallback;
import test.service.standardtests.callbacks.GetCallback;
import test.service.standardtests.data.ActivityDataUtils;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * <p>
 * Tests for the strava get Activity service methods
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class GetActivityTest extends UpdatableGetMethodTest<StravaActivity, Long> {
	@Override
	protected CreateCallback<StravaActivity> creator() throws Exception {
		return ActivityDataUtils.creator();
	}

	@Override
	protected DeleteCallback<StravaActivity> deleter() throws Exception {
		return ActivityDataUtils.deleter();
	}

	@Override
	protected StravaActivity generateInvalidObject() {
		return ActivityDataUtils.generateInvalidObject();
	}

	@Override
	protected StravaActivity generateValidObject() {
		return ActivityDataUtils.generateValidObject();
	}

	@Override
	protected Long getIdInvalid() {
		return ActivityDataUtils.ACTIVITY_INVALID;
	}

	@Override
	protected Long getIdPrivate() {
		return ActivityDataUtils.ACTIVITY_PRIVATE;
	}

	@Override
	protected Long getIdPrivateBelongsToOtherUser() {
		return ActivityDataUtils.ACTIVITY_PRIVATE_OTHER_USER;
	}

	@Override
	protected Long getIdValid() {
		return ActivityDataUtils.ACTIVITY_FOR_AUTHENTICATED_USER;
	}

	@Override
	protected GetCallback<StravaActivity, Long> getter() {
		return ActivityDataUtils.getter();
	}

	/**
	 * @throws Exception
	 *             if the test fails for an unexpected reason
	 */
	@Test
	public void testGetActivity_caching() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaActivity activity = getter().get(TestUtils.strava(), ActivityDataUtils.ACTIVITY_FOR_AUTHENTICATED_USER);
			final int requests = StravaServiceImpl.requestRate;
			final StravaActivity activity2 = getter().get(TestUtils.strava(), ActivityDataUtils.ACTIVITY_FOR_AUTHENTICATED_USER);
			assertEquals(StravaServiceImpl.requestRate, requests);
			assertEquals(activity, activity2);
		});
	}

	/**
	 * <p>
	 * Test retrieval of a known {@link StravaActivity} that belongs to the authenticated user; it should be a detailed {@link StravaResourceState representation}
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails for an unexpected reason
	 *
	 */
	@Test
	public void testGetActivity_knownActivityBelongsToAuthenticatedUser() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaActivity activity = getter().get(TestUtils.strava(), ActivityDataUtils.ACTIVITY_FOR_AUTHENTICATED_USER);

			assertNotNull("Returned null StravaActivity for known activity with id " + ActivityDataUtils.ACTIVITY_FOR_AUTHENTICATED_USER, //$NON-NLS-1$
					activity);
			assertEquals("Returned activity is not a detailed representation as expected - " + activity.getResourceState(), //$NON-NLS-1$
					StravaResourceState.DETAILED, activity.getResourceState());
			StravaActivityTest.validate(activity, ActivityDataUtils.ACTIVITY_FOR_AUTHENTICATED_USER, StravaResourceState.DETAILED);
		});
	}

	/**
	 * <p>
	 * Test retrieval of a known {@link StravaActivity} that DOES NOT belong to the authenticated user; it should be a summary {@link StravaResourceState representation}
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Test
	public void testGetActivity_knownActivityBelongsToUnauthenticatedUser() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaActivity activity = getter().get(TestUtils.strava(), ActivityDataUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER);

			assertNotNull("Returned null StravaActivity for known activity with id " + ActivityDataUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER, //$NON-NLS-1$
					activity);
			assertEquals("Returned activity is not a summary representation as expected - " + activity.getResourceState(), //$NON-NLS-1$
					StravaResourceState.SUMMARY, activity.getResourceState());
			StravaActivityTest.validate(activity, ActivityDataUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER, StravaResourceState.SUMMARY);
		});
	}

	/**
	 * <p>
	 * Test retrieval of a known {@link StravaActivity}, complete with all {@link StravaSegmentEffort efforts}
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 *
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetActivity_knownActivityWithEfforts() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaActivity activity = TestUtils.strava().getActivity(ActivityDataUtils.ACTIVITY_WITH_EFFORTS, Boolean.TRUE);

			assertNotNull("Returned null StravaActivity for known activity with id " + ActivityDataUtils.ACTIVITY_WITH_EFFORTS, activity); //$NON-NLS-1$
			assertNotNull("StravaActivity " + ActivityDataUtils.ACTIVITY_WITH_EFFORTS + " was returned but segmentEfforts is null", //$NON-NLS-1$ //$NON-NLS-2$
					activity.getSegmentEfforts());
			assertNotEquals("StravaActivity " + ActivityDataUtils.ACTIVITY_WITH_EFFORTS + " was returned but segmentEfforts is empty", 0, //$NON-NLS-1$ //$NON-NLS-2$
					activity.getSegmentEfforts().size());
			StravaActivityTest.validate(activity);
		});
	}

	/**
	 * <p>
	 * Test retrieval of a known {@link StravaActivity}, without the non-important/hidden efforts being returned (i.e. includeAllEfforts = false)
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetActivity_knownActivityWithoutEfforts() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaActivity activity = TestUtils.strava().getActivity(ActivityDataUtils.ACTIVITY_WITH_EFFORTS, Boolean.FALSE);

			assertNotNull("Returned null StravaActivity for known activity with id " + ActivityDataUtils.ACTIVITY_WITH_EFFORTS, activity); //$NON-NLS-1$
			assertNotNull("Returned null segment efforts for known activity, when they were expected", //$NON-NLS-1$
					activity.getSegmentEfforts());
			StravaActivityTest.validate(activity, ActivityDataUtils.ACTIVITY_WITH_EFFORTS, activity.getResourceState());
		});
	}

	/**
	 * <p>
	 * Test retrieval of a known {@link StravaActivity}, which is known to be a run
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Test
	public void testGetActivity_run() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaActivity activity = getter().get(TestUtils.strava(), ActivityDataUtils.ACTIVITY_RUN_WITH_SEGMENTS);
			assertNotNull(activity);
			StravaActivityTest.validate(activity);
		});
	}

	@Override
	protected void validate(StravaActivity object) {
		StravaActivityTest.validate(object);
	}
}
