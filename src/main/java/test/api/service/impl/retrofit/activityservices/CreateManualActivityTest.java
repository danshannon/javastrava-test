package test.api.service.impl.retrofit.activityservices;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.reference.StravaActivityType;
import javastrava.api.v3.service.ActivityServices;
import javastrava.api.v3.service.exception.UnauthorizedException;
import javastrava.api.v3.service.impl.retrofit.ActivityServicesImpl;

import org.junit.Test;

import test.api.model.StravaActivityTest;
import test.utils.TestUtils;

public class CreateManualActivityTest {
	/**
	 * <p>
	 * Attempt to create a valid manual {@link StravaActivity} for the user associated with the security token
	 * </p>
	 * 
	 * <p>
	 * Should successfully create the activity, and the activity should be retrievable immediately and identical to the one used to create
	 * </p>
	 * 
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testCreateManualActivity_validActivity() {
		ActivityServices service = ActivityServicesImpl.implementation(TestUtils.getValidToken());
		StravaActivity activity = TestUtils.createDefaultActivity();
		activity.setName("testCreateManualActivity_validActivity");
		activity = service.createManualActivity(TestUtils.createDefaultActivity());

		assertNotNull(activity);

		// Load it from Strava
		StravaActivity stravaActivity = service.getActivity(activity.getId());
		assertNotNull(stravaActivity);
		StravaActivityTest.validateActivity(stravaActivity,stravaActivity.getId(),stravaActivity.getResourceState());

		// And delete it
		service.deleteActivity(activity.getId());
	}

	/**
	 * <p>
	 * Attempt to create a valid manual {@link StravaActivity} for the user associated with the security token, where the user has NOT granted write access via
	 * the OAuth process
	 * </p>
	 * 
	 * <p>
	 * Should fail to create the activity and throw an {@link UnauthorizedException}, which is trapped in the test because it it expected
	 * </p>
	 * 
	 * @throws UnauthorizedException
	 */
	@Test
	public void testCreateManualActivity_accessTokenDoesNotHaveWriteAccess() {
		ActivityServices service = ActivityServicesImpl.implementation(TestUtils.getValidTokenWithoutWriteAccess());
		StravaActivity activity = null;
		try {
			activity = TestUtils.createDefaultActivity();
			activity.setName("testCreateManualActivity_accessTokenDoesNotHaveWriteAccess");
			activity = service.createManualActivity(activity);
		} catch (UnauthorizedException e) {
			// This is the expected behaviour - creation has failed because there's no write access
			return;
		}

		service.deleteActivity(activity.getId());
		fail("Created a manual activity but should have failed and thrown an UnauthorizedException!");
	}

	/**
	 * <p>
	 * Attempt to create an incomplete manual {@link StravaActivity} for the user where not all required attributes are set
	 * </p>
	 * 
	 * <p>
	 * Should fail to create the activity in each case where a required attribute is missing
	 * </p>
	 * 
	 * @throws UnauthorizedException
	 */
	@Test
	public void testCreateManualActivity_noName() {
		ActivityServices service = ActivityServicesImpl.implementation(TestUtils.getValidToken());

		// Name is required
		StravaActivity activity = TestUtils.createDefaultActivity();
		StravaActivity stravaResponse = null;
		activity.setName(null);
		activity.setDescription("testCreateManualActivity_noName");
		try {
			stravaResponse = service.createManualActivity(activity);
		} catch (IllegalArgumentException e) {
			// Expected behaviour
			return;
		} catch (Exception e) {
			// Ignore ALL other exceptions
		}
		
		// If it did get created, delete it again
		if (stravaResponse != null) {
			service.deleteActivity(stravaResponse.getId());
		}

		fail("Created an activity with no type in error" + stravaResponse);
	}
	
	@Test
	public void testCreateManualActivity_noType() {
		ActivityServices service = ActivityServicesImpl.implementation(TestUtils.getValidToken());
		// Type is required
		StravaActivity activity = TestUtils.createDefaultActivity();
		StravaActivity stravaResponse = null;
		activity.setType(null);
		activity.setName("testCreateManualActivity_noType");
		try {
			stravaResponse = service.createManualActivity(activity);
		} catch (IllegalArgumentException e) {
			// Expected behaviour
			return;
		} catch (Exception e) {
			// Ignore ALL other exceptions
		}
		
		// If it did get created, delete it again
		if (stravaResponse != null) {
			service.deleteActivity(stravaResponse.getId());
		}

		fail("Created an activity with no type in error" + stravaResponse);
	}
	
	@Test
	public void testCreateManualActivity_invalidType() {
		ActivityServices service = ActivityServicesImpl.implementation(TestUtils.getValidToken());
		// Type must be one of the specified values
		StravaActivity activity = TestUtils.createDefaultActivity();
		StravaActivity stravaResponse = null;
		activity.setName("testCreateManualActivity_invalidType");
		activity.setType(StravaActivityType.UNKNOWN);
		try {
			stravaResponse = service.createManualActivity(activity);
		} catch (IllegalArgumentException e) {
			// Expected behaviour
			return;
		} catch (Exception e) {
			// Ignore ALL other exceptions
		}
		
		// If it did get created, delete it again
		if (stravaResponse != null) {
			service.deleteActivity(stravaResponse.getId());
		}

		fail("Created an activity with invalid type in error");
	}
	
	@Test
	public void testCreateManualActivity_noStartDate() {
		ActivityServices service = ActivityServicesImpl.implementation(TestUtils.getValidToken());
		StravaActivity activity = TestUtils.createDefaultActivity();
		StravaActivity stravaResponse = null;
		// Start date is required
		activity.setName("testCreateManualActivity_noStartDate");
		activity.setStartDateLocal(null);
		try {
			stravaResponse = service.createManualActivity(activity);
		} catch (IllegalArgumentException e) {
			// Expected behaviour
			return;
		} catch (Exception e) {
			// Ignore ALL other exceptions
		}
		
		// If it did get created, delete it again
		if (stravaResponse != null) {
			service.deleteActivity(stravaResponse.getId());
		}

		fail("Created an activity with no start date in error" + stravaResponse);
	}
	
	@Test
	public void testCreateManualActivity_noElapsedTime() {
		ActivityServices service = ActivityServicesImpl.implementation(TestUtils.getValidToken());
		StravaActivity activity = TestUtils.createDefaultActivity();
		StravaActivity stravaResponse = null;
		// Elapsed time is required
		activity.setName("testCreateManualActivity_noElapsedTime");
		activity.setElapsedTime(null);
		try {
			stravaResponse = service.createManualActivity(activity);
		} catch (IllegalArgumentException e) {
			// Expected behaviour
			return;
		} catch (Exception e) {
			// Ignore ALL other exceptions
		}
		
		// If it did get created, delete it again
		if (stravaResponse != null) {
			service.deleteActivity(stravaResponse.getId());
		}

		fail("Created an activity with no elapsed time in error" + stravaResponse);
	}

}
