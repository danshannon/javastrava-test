package test.service.impl.activityservice;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.reference.StravaActivityType;
import javastrava.api.v3.service.exception.UnauthorizedException;
import javastrava.config.JavastravaApplicationConfig;
import test.api.model.StravaActivityTest;
import test.service.standardtests.CreateMethodTest;
import test.service.standardtests.callbacks.CreateCallback;
import test.service.standardtests.callbacks.DeleteCallback;
import test.service.standardtests.callbacks.GetCallback;
import test.service.standardtests.data.ActivityDataUtils;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * <p>
 * Tests for the creation of manual activities
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class CreateManualActivityTest extends CreateMethodTest<StravaActivity, Long> {
	@Override
	protected CreateCallback<StravaActivity> creator() {
		return ActivityDataUtils.creator();
	}

	@Override
	protected DeleteCallback<StravaActivity> deleter() {
		return ActivityDataUtils.deleter();
	}

	@Override
	protected void forceDelete(StravaActivity activity) throws Exception {
		ActivityDataUtils.deleter().delete(TestUtils.stravaWithFullAccess(), activity);

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
	protected GetCallback<StravaActivity, Long> getter() {
		return ActivityDataUtils.getter();
	}

	/**
	 * <p>
	 * Check that it is not possible to create a manual activity with an invalid type
	 * </p>
	 *
	 * @throws Exception
	 *             if the test failed in some way
	 */
	@Test
	public void testCreateManualActivity_invalidType() throws Exception {
		// Can't run the test if the application doesn't have Strava's permission to delete activities
		if (JavastravaApplicationConfig.STRAVA_ALLOWS_ACTIVITY_DELETE) {

			RateLimitedTestRunner.run(() -> {
				// Type must be one of the specified values
				final StravaActivity activity = ActivityDataUtils.createDefaultActivity("CreateManualActivityTest.testCreateManualActivity_invalidType"); //$NON-NLS-1$
				StravaActivity stravaResponse = null;
				activity.setType(StravaActivityType.UNKNOWN);
				try {
					stravaResponse = creator().create(TestUtils.stravaWithFullAccess(), activity);
				} catch (final IllegalArgumentException e1) {
					// Expected behaviour
					return;
				}

				// If it did get created, delete it again
				forceDelete(stravaResponse);

				fail("Created an activity with invalid type in error"); //$NON-NLS-1$
			});
		}
	}

	/**
	 * <p>
	 * Check that it is not possible to create a manual activity without an elapsed time
	 * </p>
	 *
	 * @throws Exception
	 *             if the test failed
	 */
	@Test
	public void testCreateManualActivity_noElapsedTime() throws Exception {
		// Can't run the test if the application doesn't have Strava's permission to delete activities
		if (JavastravaApplicationConfig.STRAVA_ALLOWS_ACTIVITY_DELETE) {

			RateLimitedTestRunner.run(() -> {
				final StravaActivity activity = ActivityDataUtils.createDefaultActivity("CreateManualActivityTest.testCreateManualActivity_noElapsedTime"); //$NON-NLS-1$
				StravaActivity stravaResponse = null;
				// Elapsed time is required
				activity.setElapsedTime(null);
				try {
					stravaResponse = creator().create(TestUtils.stravaWithFullAccess(), activity);
				} catch (final IllegalArgumentException e1) {
					// Expected behaviour
					return;
				}

				// If it did get created, delete it again
				forceDelete(stravaResponse);

				fail("Created an activity with no elapsed time in error" + stravaResponse); //$NON-NLS-1$
			});
		}
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
	 * @throws Exception
	 *             if the test fails in some way
	 */
	@Test
	public void testCreateManualActivity_noName() throws Exception {
		// Can't run the test if the application doesn't have Strava's permission to delete activities
		if (JavastravaApplicationConfig.STRAVA_ALLOWS_ACTIVITY_DELETE) {

			RateLimitedTestRunner.run(() -> {
				// Name is required
				final StravaActivity activity = ActivityDataUtils.createDefaultActivity("CreateManualActivityTest.testCreateManualActivity_noName"); //$NON-NLS-1$
				StravaActivity stravaResponse = null;
				activity.setDescription(activity.getName());
				activity.setName(null);
				try {
					stravaResponse = creator().create(TestUtils.stravaWithFullAccess(), activity);
				} catch (final IllegalArgumentException e1) {
					// Expected behaviour
					return;
				}

				// If it did get created, delete it again
				if (stravaResponse != null) {
					forceDelete(stravaResponse);
				}

				fail("Created an activity with no type in error" + stravaResponse); //$NON-NLS-1$
			});
		}
	}

	/**
	 * <p>
	 * Check that it is not possible to create a manual activity without a start date
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in some unexpected way
	 */
	@Test
	public void testCreateManualActivity_noStartDate() throws Exception {
		// Can't run the test if the application doesn't have Strava's permission to delete activities
		if (JavastravaApplicationConfig.STRAVA_ALLOWS_ACTIVITY_DELETE) {

			RateLimitedTestRunner.run(() -> {
				final StravaActivity activity = ActivityDataUtils.createDefaultActivity("CreateManualActivityTest.testCreateManualActivity_noStartDate"); //$NON-NLS-1$
				StravaActivity stravaResponse = null;
				// Start date is required
				activity.setStartDateLocal(null);
				try {
					stravaResponse = creator().create(TestUtils.stravaWithFullAccess(), activity);
				} catch (final IllegalArgumentException e1) {
					// Expected behaviour
					return;
				}

				// If it did get created, delete it again
				forceDelete(stravaResponse);

				fail("Created an activity with no start date in error" + stravaResponse); //$NON-NLS-1$
			});
		}
	}

	/**
	 * <p>
	 * Check that you can't create a manual activity without a type specified
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in some unexpected way
	 */
	@Test
	public void testCreateManualActivity_noType() throws Exception {
		// Can't run the test if the application doesn't have Strava's permission to delete activities
		if (JavastravaApplicationConfig.STRAVA_ALLOWS_ACTIVITY_DELETE) {

			RateLimitedTestRunner.run(() -> {
				// Type is required
				final StravaActivity activity = ActivityDataUtils.createDefaultActivity("CreateManualActivityTest.testCreateManualActivity_noType"); //$NON-NLS-1$
				StravaActivity stravaResponse = null;
				activity.setType(null);
				try {
					stravaResponse = creator().create(TestUtils.stravaWithFullAccess(), activity);
				} catch (final IllegalArgumentException e1) {
					// Expected behaviour
					return;
				}

				// If it did get created, delete it again
				forceDelete(stravaResponse);

				fail("Created an activity with no type in error" + stravaResponse); //$NON-NLS-1$
			});
		}
	}

	@Override
	@Test
	public void testCreateNonExistentParent() throws Exception {
		// There's no parent involved for an activity
		return;
	}

	/**
	 * @see test.service.standardtests.spec.StandardTests#testInvalidId()
	 */
	@Override
	@Test
	public void testInvalidId() throws Exception {
		// For creating objects, you don't specify an id
		return;
	}

	/**
	 * @see test.service.standardtests.spec.PrivacyTests#testPrivateBelongsToOtherUser()
	 */
	@Override
	@Test
	public void testPrivateBelongsToOtherUser() throws Exception {
		// Can't create an activity that belongs to someone else
		return;
	}

	@Override
	@Test
	public void testPrivateWithNoViewPrivateScope() throws Exception {
		// Can't run the test if the application doesn't have Strava's permission to delete activities
		if (JavastravaApplicationConfig.STRAVA_ALLOWS_ACTIVITY_DELETE) {

			RateLimitedTestRunner.run(() -> {
				// Create the object test data
				final StravaActivity activity = generateValidObject();
				activity.setPrivateActivity(Boolean.TRUE);

				// Create it in Strava
				final StravaActivity createdActivity;

				try {
					createdActivity = creator().create(TestUtils.stravaWithWriteAccess(), activity);
				} catch (final UnauthorizedException e) {
					// Expected
					return;
				}

				// Finally, delete it
				forceDelete(createdActivity);
				fail("Created a manual activity set to private, but don't have view_private scope in token"); //$NON-NLS-1$
			});
		}
	}

	@Override
	@Test
	public void testPrivateWithViewPrivateScope() throws Exception {
		// Can't run the test if the application doesn't have Strava's permission to delete activities
		if (JavastravaApplicationConfig.STRAVA_ALLOWS_ACTIVITY_DELETE) {

			RateLimitedTestRunner.run(() -> {
				// Create the object test data
				final StravaActivity activity = generateValidObject();
				activity.setPrivateActivity(Boolean.TRUE);

				// Create it in Strava
				final StravaActivity createdActivity = creator().create(TestUtils.stravaWithFullAccess(), activity);
				assertTrue(createdActivity.getPrivateActivity().booleanValue());

				// Validate
				StravaActivityTest.validate(createdActivity);

				// Finally, delete it
				forceDelete(createdActivity);
			});
		}
	}

	@Override
	protected void validate(StravaActivity object) {
		StravaActivityTest.validate(object);
	}

}
