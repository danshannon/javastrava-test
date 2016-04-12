package test.api.service.impl.activityservice.async;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.api.v3.service.exception.UnauthorizedException;

import org.junit.Test;

import test.api.service.StravaTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class DeleteActivityAsyncTest extends StravaTest {
	/**
	 * <p>
	 * Attempt to create an {@link StravaActivity} for the user, using a token which has not been granted write access through the OAuth process
	 * </p>
	 *
	 * <p>
	 * Should fail to create the activity and throw an {@link UnauthorizedException}
	 * </p>
	 *
	 * @throws Exception
	 */
	@Test
	public void testDeleteActivity_accessTokenDoesNotHaveWriteAccess() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Create the activity using a service which DOES have write access
				final StravaActivity activity = TestUtils.createDefaultActivity("DeleteActivityAsyncTest.testDeleteActivity_accessTokenDoesNotHaveWriteAccess");
				final StravaActivity stravaResponse = stravaWithWriteAccess().createManualActivity(activity);

				// Now get a token without write access and attempt to delete
				try {
					strava().deleteActivityAsync(stravaResponse.getId()).get();
					fail("Succeeded in deleting an activity despite not having write access");
				} catch (final UnauthorizedException e) {
					// Expected behaviour
				}

				// Delete the activity using a token with write access
				forceDeleteActivity(stravaResponse);
			});
	}

	/**
	 * <p>
	 * Attempt to delete an {@link StravaActivity} which does not exist
	 * </p>
	 *
	 * @throws Exception
	 *
	 * @throws UnauthorizedException
	 */
	@Test
	public void testDeleteActivity_invalidActivity() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				stravaWithWriteAccess().deleteActivityAsync(TestUtils.ACTIVITY_INVALID).get();
			} catch (final NotFoundException e) {
				// Expected
				return;
			}
			fail("deleted an activity that doesn't exist");
		});
	}

	@Test
	public void testDeleteActivity_privateActivity() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaActivity activity = TestUtils.createDefaultActivity("DeleteActivityAsyncTest.testDeleteActivity_privateActivity");
			activity.setPrivateActivity(Boolean.TRUE);
			final StravaActivity createResponse = stravaWithFullAccess().createManualActivity(activity);
			assertEquals(Boolean.TRUE, createResponse.getPrivateActivity());
			StravaActivity deleteResponse = null;
			try {
				deleteResponse = stravaWithFullAccess().deleteActivityAsync(createResponse.getId()).get();
				assertNull(deleteResponse);
			} catch (final Exception e) {
				forceDeleteActivity(createResponse);
				fail("Could not delete private activity");
			}
		});
	}

	@Test
	public void testDeleteActivity_privateActivityNoViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaActivity activity = TestUtils.createDefaultActivity("DeleteActivityAsyncTest.testDeleteActivity_privateActivityNoViewPrivate");
			activity.setPrivateActivity(Boolean.TRUE);
			final StravaActivity createResponse = stravaWithFullAccess().createManualActivity(activity);
			try {
				stravaWithWriteAccess().deleteActivityAsync(createResponse.getId()).get();
			} catch (final UnauthorizedException e) {
				// Expected
				forceDeleteActivity(createResponse);
				return;
			} catch (final Exception e) {
				// Do nothing (just carry on and fail)
			}
			forceDeleteActivity(createResponse);
			fail("Deleted private activity despite not having view_private access");
		});
	}

	/**
	 * <p>
	 * Attempt to delete an existing {@link StravaActivity} for the user
	 * </p>
	 *
	 * <p>
	 * In order to avoid deleting genuine data, this test creates the activity first, checks that it has been successfully written (i.e. that it can be read
	 * back from the API) and then deletes it again
	 * </p>
	 *
	 * <p>
	 * Should successfully delete the activity; it should no longer be able to be retrieved via the API
	 * </p>
	 *
	 * @throws Exception
	 */
	@Test
	public void testDeleteActivity_validActivity() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaActivity activity = TestUtils.createDefaultActivity("DeleteActivityAsyncTest.testDeleteActivity_validActivity");
			final StravaActivity createResponse = stravaWithWriteAccess().createManualActivity(activity);
			StravaActivity deleteResponse = null;
			try {
				deleteResponse = stravaWithWriteAccess().deleteActivityAsync(createResponse.getId()).get();
			} catch (final Exception e) {
				deleteResponse = forceDeleteActivity(createResponse);
			}
			final StravaActivity getResponse = strava().getActivity(createResponse.getId());
			assertNull(deleteResponse);
			assertNull(getResponse);

		});
	}

}
