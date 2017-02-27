package test.api.service.impl.activityservice;

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
import test.api.service.standardtests.UpdatableGetMethodTest;
import test.api.service.standardtests.callbacks.CreateCallback;
import test.api.service.standardtests.callbacks.DeleteCallback;
import test.api.service.standardtests.callbacks.GetCallback;
import test.api.service.standardtests.data.ActivityDataUtils;
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
	/**
	 * @throws Exception
	 *             if the test fails for an unexpected reason
	 */
	@Test
	public void testGetActivity_caching() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaActivity activity = getter().get(TestUtils.strava(), TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER);
			final int requests = StravaServiceImpl.requestRate;
			final StravaActivity activity2 = getter().get(TestUtils.strava(), TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER);
			assertEquals(StravaServiceImpl.requestRate, requests);
			assertEquals(activity, activity2);
		});
	}

	/**
	 * <p>
	 * Test retrieval of a known {@link StravaActivity} that belongs to the authenticated user; it should be a detailed
	 * {@link StravaResourceState representation}
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails for an unexpected reason
	 *
	 */
	@Test
	public void testGetActivity_knownActivityBelongsToAuthenticatedUser() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaActivity activity = getter().get(TestUtils.strava(), TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER);

			assertNotNull("Returned null StravaActivity for known activity with id " + TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER, //$NON-NLS-1$
					activity);
			assertEquals("Returned activity is not a detailed representation as expected - " + activity.getResourceState(), //$NON-NLS-1$
					StravaResourceState.DETAILED, activity.getResourceState());
			StravaActivityTest.validateActivity(activity, TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER, StravaResourceState.DETAILED);
		});
	}

	/**
	 * <p>
	 * Test retrieval of a known {@link StravaActivity} that DOES NOT belong to the authenticated user; it should be a summary
	 * {@link StravaResourceState representation}
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Test
	public void testGetActivity_knownActivityBelongsToUnauthenticatedUser() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaActivity activity = getter().get(TestUtils.strava(), TestUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER);

			assertNotNull("Returned null StravaActivity for known activity with id " + TestUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER, //$NON-NLS-1$
					activity);
			assertEquals("Returned activity is not a summary representation as expected - " + activity.getResourceState(), //$NON-NLS-1$
					StravaResourceState.SUMMARY, activity.getResourceState());
			StravaActivityTest.validateActivity(activity, TestUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER, StravaResourceState.SUMMARY);
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
			final StravaActivity activity = TestUtils.strava().getActivity(TestUtils.ACTIVITY_WITH_EFFORTS, Boolean.TRUE);

			assertNotNull("Returned null StravaActivity for known activity with id " + TestUtils.ACTIVITY_WITH_EFFORTS, activity); //$NON-NLS-1$
			assertNotNull("StravaActivity " + TestUtils.ACTIVITY_WITH_EFFORTS + " was returned but segmentEfforts is null", //$NON-NLS-1$ //$NON-NLS-2$
					activity.getSegmentEfforts());
			assertNotEquals("StravaActivity " + TestUtils.ACTIVITY_WITH_EFFORTS + " was returned but segmentEfforts is empty", 0, //$NON-NLS-1$ //$NON-NLS-2$
					activity.getSegmentEfforts().size());
			StravaActivityTest.validateActivity(activity);
		});
	}

	/**
	 * <p>
	 * Test retrieval of a known {@link StravaActivity}, without the non-important/hidden efforts being returned (i.e.
	 * includeAllEfforts = false)
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetActivity_knownActivityWithoutEfforts() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaActivity activity = TestUtils.strava().getActivity(TestUtils.ACTIVITY_WITH_EFFORTS, Boolean.FALSE);

			assertNotNull("Returned null StravaActivity for known activity with id " + TestUtils.ACTIVITY_WITH_EFFORTS, activity); //$NON-NLS-1$
			assertNotNull("Returned null segment efforts for known activity, when they were expected", //$NON-NLS-1$
					activity.getSegmentEfforts());
			StravaActivityTest.validateActivity(activity, TestUtils.ACTIVITY_WITH_EFFORTS, activity.getResourceState());
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
			final StravaActivity activity = getter().get(TestUtils.strava(), TestUtils.ACTIVITY_RUN_OTHER_USER);
			assertNotNull(activity);
			StravaActivityTest.validateActivity(activity);
		});
	}

	@Override
	protected CreateCallback<StravaActivity> creator() throws Exception {
		return ActivityDataUtils.creator();
	}

	@Override
	protected DeleteCallback<StravaActivity> deleter() throws Exception {
		return ActivityDataUtils.deleter();
	}

	@Override
	protected GetCallback<StravaActivity, Long> getter() throws Exception {
		return ActivityDataUtils.getter();
	}

	@Override
	protected StravaActivity generateValidObject() {
		return ActivityDataUtils.generateValidObject();
	}

	@Override
	protected StravaActivity generateInvalidObject() {
		return ActivityDataUtils.generateInvalidObject();
	}

	@Override
	protected Long getIdValid() {
		return TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER;
	}

	@Override
	protected Long getIdInvalid() {
		return TestUtils.ACTIVITY_INVALID;
	}

	@Override
	protected Long getIdPrivate() {
		return TestUtils.ACTIVITY_PRIVATE;
	}

	@Override
	protected Long getIdPrivateBelongsToOtherUser() {
		return TestUtils.ACTIVITY_PRIVATE_OTHER_USER;
	}

	@Override
	protected void validate(StravaActivity object) {
		StravaActivityTest.validateActivity(object);
	}
}
