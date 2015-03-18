package test.api.service.impl.activityservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaSegmentEffort;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.service.ActivityService;
import javastrava.api.v3.service.exception.UnauthorizedException;
import javastrava.api.v3.service.impl.ActivityServiceImpl;

import org.junit.Test;

import test.api.model.StravaActivityTest;
import test.utils.TestUtils;

public class GetActivityTest {
	/**
	 * <p>
	 * Test retrieval of a known {@link StravaActivity}, complete with all
	 * {@link StravaSegmentEffort efforts}
	 * </p>
	 *
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testGetActivity_knownActivityWithEfforts() {
		final StravaActivity activity = service().getActivity(TestUtils.ACTIVITY_WITH_EFFORTS, Boolean.TRUE);

		assertNotNull("Returned null StravaActivity for known activity with id " + TestUtils.ACTIVITY_WITH_EFFORTS,
				activity);
		assertNotNull("StravaActivity " + TestUtils.ACTIVITY_WITH_EFFORTS + " was returned but segmentEfforts is null",
				activity.getSegmentEfforts());
		assertNotEquals("StravaActivity " + TestUtils.ACTIVITY_WITH_EFFORTS
				+ " was returned but segmentEfforts is empty", 0, activity.getSegmentEfforts().size());
		StravaActivityTest.validateActivity(activity);
	}

	/**
	 * <p>
	 * Test retrieval of a known {@link StravaActivity} that belongs to the
	 * authenticated user; it should be a detailed {@link StravaResourceState
	 * representation}
	 * </p>
	 *
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testGetActivity_knownActivityBelongsToAuthenticatedUser() {
		final StravaActivity activity = service().getActivity(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER);

		assertNotNull("Returned null StravaActivity for known activity with id "
				+ TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER, activity);
		assertEquals("Returned activity is not a detailed representation as expected - " + activity.getResourceState(),
				StravaResourceState.DETAILED, activity.getResourceState());
		StravaActivityTest.validateActivity(activity, TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER,
				StravaResourceState.DETAILED);
	}

	/**
	 * <p>
	 * Test retrieval of a known {@link StravaActivity} that DOES NOT belong to
	 * the authenticated user; it should be a summary
	 * {@link StravaResourceState representation}
	 * </p>
	 *
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testGetActivity_knownActivityBelongsToUnauthenticatedUser() {
		final StravaActivity activity = service().getActivity(TestUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER);

		assertNotNull("Returned null StravaActivity for known activity with id "
				+ TestUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER, activity);
		assertEquals("Returned activity is not a summary representation as expected - " + activity.getResourceState(),
				StravaResourceState.SUMMARY, activity.getResourceState());
		StravaActivityTest.validateActivity(activity, TestUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER,
				StravaResourceState.SUMMARY);
	}

	/**
	 * <p>
	 * Test retrieval of a known {@link StravaActivity}, without the
	 * non-important/hidden efforts being returned (i.e. includeAllEfforts =
	 * false)
	 * </p>
	 *
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testGetActivity_knownActivityWithoutEfforts() throws UnauthorizedException {
		final StravaActivity activity = service().getActivity(TestUtils.ACTIVITY_WITH_EFFORTS, Boolean.FALSE);

		assertNotNull("Returned null StravaActivity for known activity with id " + TestUtils.ACTIVITY_WITH_EFFORTS,
				activity);
		assertNotNull("Returned null segment efforts for known activity, when they were expected",
				activity.getSegmentEfforts());
		StravaActivityTest.validateActivity(activity, TestUtils.ACTIVITY_WITH_EFFORTS, activity.getResourceState());
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
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testGetActivity_unknownActivity() {
		final ActivityService service = ActivityServiceImpl.instance(TestUtils.getValidToken());
		final StravaActivity activity = service.getActivity(TestUtils.ACTIVITY_INVALID);

		assertNull("Got an activity for an invalid activity id " + TestUtils.ACTIVITY_INVALID, activity);
	}

	@Test
	public void testGetActivity_privateBelongsToOtherUser() {
		final StravaActivity activity = service().getActivity(TestUtils.ACTIVITY_PRIVATE_OTHER_USER);

		// Should get an activity which only has an id
		assertNotNull(activity);
		final StravaActivity comparisonActivity = new StravaActivity();
		comparisonActivity.setId(TestUtils.ACTIVITY_PRIVATE_OTHER_USER);
		comparisonActivity.setResourceState(StravaResourceState.META);
		assertEquals(comparisonActivity, activity);
		StravaActivityTest.validateActivity(activity);
	}

	private ActivityService service() {
		return ActivityServiceImpl.instance(TestUtils.getValidToken());
	}


}
