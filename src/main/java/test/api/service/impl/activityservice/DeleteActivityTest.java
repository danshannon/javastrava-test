package test.api.service.impl.activityservice;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.service.ActivityService;
import javastrava.api.v3.service.exception.UnauthorizedException;
import javastrava.api.v3.service.impl.ActivityServiceImpl;

import org.junit.Test;

import test.utils.TestUtils;

public class DeleteActivityTest {
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
		ActivityService service = ActivityServiceImpl.instance(TestUtils.getValidToken());
		StravaActivity activity = TestUtils.createDefaultActivity();
		activity.setName("testDeleteActivity_validActivity");
		StravaActivity stravaResponse = service.createManualActivity(activity);
		activity = service.getActivity(stravaResponse.getId());
		assertNotNull(activity);
		service.deleteActivity(activity.getId());

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
		ActivityService service = ActivityServiceImpl.instance(TestUtils.getValidToken());
		StravaActivity activity = TestUtils.createDefaultActivity();
		activity.setName("testDeleteActivity_accessTokenDoesNotHaveWriteAccess");
		StravaActivity stravaResponse = service.createManualActivity(activity);
		activity = service.getActivity(stravaResponse.getId());
		assertNotNull(activity);

		// Now get a token without write access and attempt to delete
		service = ActivityServiceImpl.instance(TestUtils.getValidTokenWithoutWriteAccess());
		try {
			service.deleteActivity(activity.getId());
			fail("Succeeded in deleting an activity despite not having write access");
		} catch (UnauthorizedException e) {
			// Expected behaviour
		}

		// Delete the activity using a token with write access
		service = ActivityServiceImpl.instance(TestUtils.getValidToken());
		service.deleteActivity(activity.getId());
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
		ActivityService service = ActivityServiceImpl.instance(TestUtils.getValidToken());
		StravaActivity stravaResponse = service.deleteActivity(1);
		assertNull("deleted an activity that doesn't exist", stravaResponse);
	}

}
