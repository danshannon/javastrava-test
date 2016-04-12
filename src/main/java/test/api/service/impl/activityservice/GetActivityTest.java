package test.api.service.impl.activityservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaSegmentEffort;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.service.exception.UnauthorizedException;
import javastrava.api.v3.service.impl.StravaServiceImpl;
import test.api.model.StravaActivityTest;
import test.api.rest.APITest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class GetActivityTest extends GetMethodTest {
	@Test
	public void testGetActivity_caching() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaActivity activity = strava().getActivity(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER);
			final int requests = StravaServiceImpl.requestRate;
			final StravaActivity activity2 = strava().getActivity(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER);
			assertEquals(StravaServiceImpl.requestRate, requests);
			assertEquals(activity, activity2);
		});
	}

	/**
	 * <p>
	 * Test retrieval of a known {@link StravaActivity} that belongs to the authenticated user; it should be a detailed {@link StravaResourceState
	 * representation}
	 * </p>
	 *
	 * @throws Exception
	 *
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testGetActivity_knownActivityBelongsToAuthenticatedUser() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaActivity activity = strava().getActivity(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER);

			assertNotNull("Returned null StravaActivity for known activity with id " + TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER, activity);
			assertEquals("Returned activity is not a detailed representation as expected - " + activity.getResourceState(), StravaResourceState.DETAILED,
					activity.getResourceState());
			StravaActivityTest.validateActivity(activity, TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER, StravaResourceState.DETAILED);
		});
	}

	/**
	 * <p>
	 * Test retrieval of a known {@link StravaActivity} that DOES NOT belong to the authenticated user; it should be a summary {@link StravaResourceState
	 * representation}
	 * </p>
	 *
	 * @throws Exception
	 *
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testGetActivity_knownActivityBelongsToUnauthenticatedUser() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaActivity activity = strava().getActivity(TestUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER);

			assertNotNull("Returned null StravaActivity for known activity with id " + TestUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER, activity);
			assertEquals("Returned activity is not a summary representation as expected - " + activity.getResourceState(), StravaResourceState.SUMMARY,
					activity.getResourceState());
			StravaActivityTest.validateActivity(activity, TestUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER, StravaResourceState.SUMMARY);
		});
	}

	/**
	 * <p>
	 * Test retrieval of a known {@link StravaActivity}, complete with all {@link StravaSegmentEffort efforts}
	 * </p>
	 *
	 * @throws Exception
	 *
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testGetActivity_knownActivityWithEfforts() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaActivity activity = strava().getActivity(TestUtils.ACTIVITY_WITH_EFFORTS, Boolean.TRUE);

			assertNotNull("Returned null StravaActivity for known activity with id " + TestUtils.ACTIVITY_WITH_EFFORTS, activity);
			assertNotNull("StravaActivity " + TestUtils.ACTIVITY_WITH_EFFORTS + " was returned but segmentEfforts is null", activity.getSegmentEfforts());
			assertNotEquals("StravaActivity " + TestUtils.ACTIVITY_WITH_EFFORTS + " was returned but segmentEfforts is empty", 0,
					activity.getSegmentEfforts().size());
			StravaActivityTest.validateActivity(activity);
		});
	}

	/**
	 * <p>
	 * Test retrieval of a known {@link StravaActivity}, without the non-important/hidden efforts being returned (i.e. includeAllEfforts = false)
	 * </p>
	 *
	 * @throws Exception
	 */
	@Test
	public void testGetActivity_knownActivityWithoutEfforts() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaActivity activity = strava().getActivity(TestUtils.ACTIVITY_WITH_EFFORTS, Boolean.FALSE);

			assertNotNull("Returned null StravaActivity for known activity with id " + TestUtils.ACTIVITY_WITH_EFFORTS, activity);
			assertNotNull("Returned null segment efforts for known activity, when they were expected", activity.getSegmentEfforts());
			StravaActivityTest.validateActivity(activity, TestUtils.ACTIVITY_WITH_EFFORTS, activity.getResourceState());
		});
	}

	/**
	 * Can we get a private activity with VIEW_PRIVATE scope
	 *
	 * @throws Exception
	 */
	@Test
	public void testGetActivity_privateAuthenticatedUser() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaActivity activity = APITest.createPrivateActivity("GetActivityTest.testGetActivity_privateAuthenticatedUser()");
			StravaActivity response = null;
			try {
				response = stravaWithViewPrivate().getActivity(activity.getId());
			} finally {
				forceDeleteActivity(response);
			}
			StravaActivityTest.validateActivity(response);

		});
	}

	@Test
	public void testGetActivity_privateBelongsToOtherUser() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaActivity activity = strava().getActivity(TestUtils.ACTIVITY_PRIVATE_OTHER_USER);

			// Should get an activity which only has an id
			assertNotNull(activity);
			final StravaActivity comparisonActivity = new StravaActivity();
			comparisonActivity.setId(TestUtils.ACTIVITY_PRIVATE_OTHER_USER);
			comparisonActivity.setResourceState(StravaResourceState.PRIVATE);
			assertEquals(comparisonActivity, activity);
			StravaActivityTest.validateActivity(activity);
		});
	}

	/**
	 * Can we get a private activity belonging to the authenticated user, without VIEW_PRIVATE scope?
	 *
	 * @throws Exception
	 */
	@Test
	public void testGetActivity_privateNoViewPrivateScope() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaActivity activity = APITest.createPrivateActivity("GetActivityTest.testGetActivity_privateNoViewPrivateScope()");
			StravaActivity response = null;
			try {
				response = strava().getActivity(activity.getId());
			} catch (final UnauthorizedException e) {
				// expected
				forceDeleteActivity(activity.getId());
				return;
			}
			forceDeleteActivity(activity.getId());
			StravaActivityTest.validateActivity(response, response.getId(), StravaResourceState.PRIVATE);
		});
	}

	@Test
	public void testGetActivity_run() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaActivity activity = strava().getActivity(TestUtils.ACTIVITY_RUN_OTHER_USER);
			assertNotNull(activity);
			StravaActivityTest.validateActivity(activity);
		});
	}

	/**
	 * <p>
	 * Test retrieval of a non-existent {@link StravaActivity}
	 * </p>
	 *
	 * <p>
	 * Should return <code>null</code>
	 * </p>
	 *
	 * @throws Exception
	 *
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testGetActivity_unknownActivity() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaActivity activity = strava().getActivity(TestUtils.ACTIVITY_INVALID);

			assertNull("Got an activity for an invalid activity id " + TestUtils.ACTIVITY_INVALID, activity);
		});
	}
}
