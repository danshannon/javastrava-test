package test.api.rest.activity;

import javastrava.api.v3.model.StravaActivity;
import test.api.model.StravaActivityTest;
import test.api.rest.APIDeleteTest;
import test.service.standardtests.data.ActivityDataUtils;
import test.utils.TestUtils;

public class DeleteActivityTest extends APIDeleteTest<StravaActivity, Long> {
	/**
	 *
	 */
	public DeleteActivityTest() {
		super();

		this.callback = (api, activity, activityId) -> api.deleteActivity(activityId);
	}

	// /**
	// * <p>
	// * Attempt to create an {@link StravaActivity} for the user, using a token
	// which has not been granted write access through the OAuth process
	// * </p>
	// *
	// * <p>
	// * Should fail to create the activity and throw an {@link
	// UnauthorizedException}
	// * </p>
	// *
	// * @throws Exception
	// */
	// @Test
	// public void testDeleteActivity_accessTokenDoesNotHaveWriteAccess() throws
	// Exception {
	// RateLimitedTestRunner.run(() -> {
	// // Create the activity using a service which DOES have write access
	// final StravaActivity activity =
	// TestUtils.createDefaultActivity("DeleteActivityTest.testDeleteActivity_accessTokenDoesNotHaveWriteAccess");
	// final StravaActivity stravaResponse =
	// apiWithWriteAccess().createManualActivity(activity);
	//
	// // Now get a token without write access and attempt to delete
	// try {
	// api().deleteActivity(stravaResponse.getId());
	// fail("Succeeded in deleting an activity despite not having write
	// access");
	// } catch (final UnauthorizedException e) {
	// // Expected behaviour
	// }
	//
	// // Delete the activity using a token with write access
	// forceDeleteActivity(stravaResponse);
	// });
	// }
	//
	// /**
	// * <p>
	// * Attempt to delete an {@link StravaActivity} which does not exist
	// * </p>
	// *
	// * @throws Exception
	// *
	// * @throws UnauthorizedException
	// */
	// @Test
	// public void testDeleteActivity_invalidActivity() throws Exception {
	// RateLimitedTestRunner.run(() -> {
	// try {
	// apiWithWriteAccess().deleteActivity(TestUtils.ACTIVITY_INVALID);
	// } catch (final NotFoundException e) {
	// // Expected
	// return;
	// }
	// fail("deleted an activity that doesn't exist");
	// });
	// }
	//
	// @Test
	// public void testDeleteActivity_privateActivity() throws Exception {
	// RateLimitedTestRunner.run(() -> {
	// final StravaActivity activity =
	// TestUtils.createDefaultActivity("DeleteActivityTest.testDeleteActivity_privateActivity");
	// activity.setPrivateActivity(Boolean.TRUE);
	// final StravaActivity createResponse =
	// apiWithFullAccess().createManualActivity(activity);
	// assertEquals(Boolean.TRUE, createResponse.getPrivateActivity());
	// StravaActivity deleteResponse = null;
	// try {
	// deleteResponse =
	// apiWithFullAccess().deleteActivity(createResponse.getId());
	// assertNull(deleteResponse);
	// } catch (final Exception e) {
	// forceDeleteActivity(createResponse);
	// fail("Could not delete private activity");
	// }
	// });
	// }
	//
	// @Test
	// public void testDeleteActivity_privateActivityNoViewPrivate() throws
	// Exception {
	// RateLimitedTestRunner.run(() -> {
	// final StravaActivity activity =
	// TestUtils.createDefaultActivity("DeleteActivityTest.testDeleteActivity_privateActivityNoViewPrivate");
	// activity.setPrivateActivity(Boolean.TRUE);
	// final StravaActivity createResponse =
	// apiWithFullAccess().createManualActivity(activity);
	// try {
	// apiWithWriteAccess().deleteActivity(createResponse.getId());
	// } catch (final UnauthorizedException e) {
	// // Expected
	// forceDeleteActivity(createResponse);
	// return;
	// } catch (final Exception e) {
	// // Do nothing (just carry on and fail)
	// }
	// forceDeleteActivity(createResponse);
	// fail("Deleted private activity despite not having view_private access");
	// });
	// }
	//
	// /**
	// * <p>
	// * Attempt to delete an existing {@link StravaActivity} for the user
	// * </p>
	// *
	// * <p>
	// * In order to avoid deleting genuine data, this test creates the activity
	// first, checks that it has been successfully written (i.e. that it can be
	// read
	// * back from the API) and then deletes it again
	// * </p>
	// *
	// * <p>
	// * Should successfully delete the activity; it should no longer be able to
	// be retrieved via the API
	// * </p>
	// *
	// * @throws Exception
	// */
	// @Test
	// public void testDeleteActivity_validActivity() throws Exception {
	// RateLimitedTestRunner.run(() -> {
	// final StravaActivity activity =
	// TestUtils.createDefaultActivity("DeleteActivityTest.testDeleteActivity_validActivity");
	// final StravaActivity createResponse =
	// apiWithWriteAccess().createManualActivity(activity);
	// StravaActivity deleteResponse = null;
	// try {
	// deleteResponse =
	// apiWithWriteAccess().deleteActivity(createResponse.getId());
	// } catch (final Exception e) {
	// deleteResponse = forceDeleteActivity(createResponse);
	// }
	// try {
	// api().getActivity(createResponse.getId(), null);
	// } catch (final NotFoundException e) {
	// // Expected
	// }
	// assertNull(deleteResponse);
	//
	// });
	// }

	/**
	 * @see test.api.rest.APIDeleteTest#createObject()
	 */
	@Override
	protected StravaActivity createObject() {
		return null;
	}

	/**
	 * @see test.api.rest.APIDeleteTest#forceDelete(java.lang.Object)
	 */
	@Override
	protected void forceDelete(final StravaActivity activity) {
		forceDeleteActivity(activity);
	}

	/**
	 * @see test.api.rest.APIDeleteTest#invalidParentId()
	 */
	@Override
	protected Long invalidParentId() {
		return TestUtils.ACTIVITY_INVALID;
	}

	/**
	 * @see test.api.rest.APIDeleteTest#privateParentId()
	 */
	@Override
	protected Long privateParentId() {
		// Create a private activity
		StravaActivity activity = ActivityDataUtils.createDefaultActivity("DeleteActivityTest.privateParentId");
		activity.setPrivateActivity(Boolean.TRUE);
		activity = apiWithFullAccess().createManualActivity(activity);
		return activity.getId();
	}

	/**
	 * @see test.api.rest.APIDeleteTest#privateParentOtherUserId()
	 */
	@Override
	protected Long privateParentOtherUserId() {
		return TestUtils.ACTIVITY_PRIVATE_OTHER_USER;
	}

	/**
	 * @see test.api.rest.APITest#validate(java.lang.Object)
	 */
	@Override
	protected void validate(final StravaActivity result) throws Exception {
		StravaActivityTest.validate(result);

	}

	/**
	 * @see test.api.rest.APIDeleteTest#validParentId()
	 */
	@Override
	protected Long validParentId() {
		// Create a private activity
		StravaActivity activity = ActivityDataUtils.createDefaultActivity("DeleteActivityTest.privateParentId");
		activity = apiWithFullAccess().createManualActivity(activity);
		return activity.getId();
	}

}
