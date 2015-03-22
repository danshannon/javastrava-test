package test.api.service.impl.activityservice;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.service.exception.UnauthorizedException;

import org.junit.Test;

import test.api.service.StravaTest;
import test.utils.TestUtils;

public class DeleteActivityTest extends StravaTest {
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
	 */
	@Test
	public void testDeleteActivity_validActivity() {
		StravaActivity activity = TestUtils.createDefaultActivity();
		activity.setName("testDeleteActivity_validActivity");
		final StravaActivity stravaResponse = service().createManualActivity(activity);
		activity = service().getActivity(stravaResponse.getId());
		service().deleteActivity(activity.getId());

	}

	/**
	 * <p>
	 * Attempt to create an {@link StravaActivity} for the user, using a token which has not been granted write access through the OAuth process
	 * </p>
	 *
	 * <p>
	 * Should fail to create the activity and throw an {@link UnauthorizedException}
	 * </p>
	 */
	@Test
	public void testDeleteActivity_accessTokenDoesNotHaveWriteAccess() {
		// Create the activity using a service which DOES have write access
		StravaActivity activity = TestUtils.createDefaultActivity();
		activity.setName("testDeleteActivity_accessTokenDoesNotHaveWriteAccess");
		final StravaActivity stravaResponse = service().createManualActivity(activity);
		activity = service().getActivity(stravaResponse.getId());

		// Now get a token without write access and attempt to delete
		try {
			serviceWithoutWriteAccess().deleteActivity(activity.getId());
			fail("Succeeded in deleting an activity despite not having write access");
		} catch (final UnauthorizedException e) {
			// Expected behaviour
		}

		// Delete the activity using a token with write access
		service().deleteActivity(activity.getId());
	}

	/**
	 * <p>
	 * Attempt to delete an {@link StravaActivity} which does not exist
	 * </p>
	 *
	 * @throws UnauthorizedException
	 */
	@Test
	public void testDeleteActivity_invalidActivity() {
		final StravaActivity stravaResponse = service().deleteActivity(1);
		assertNull("deleted an activity that doesn't exist", stravaResponse);
	}

}
