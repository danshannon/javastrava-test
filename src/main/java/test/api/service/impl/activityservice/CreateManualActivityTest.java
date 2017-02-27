package test.api.service.impl.activityservice;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.reference.StravaActivityType;
import javastrava.api.v3.service.exception.UnauthorizedException;
import test.api.model.StravaActivityTest;
import test.api.service.standardtests.CreateMethodTest;
import test.api.service.standardtests.callbacks.CreateCallback;
import test.api.service.standardtests.callbacks.DeleteCallback;
import test.api.service.standardtests.callbacks.GetCallback;
import test.api.service.standardtests.data.ActivityDataUtils;
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
		RateLimitedTestRunner.run(() -> {
			// Type must be one of the specified values
			final StravaActivity activity = TestUtils
					.createDefaultActivity("CreateManualActivityTest.testCreateManualActivity_invalidType"); //$NON-NLS-1$
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
		RateLimitedTestRunner.run(() -> {
			final StravaActivity activity = TestUtils
					.createDefaultActivity("CreateManualActivityTest.testCreateManualActivity_noElapsedTime"); //$NON-NLS-1$
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
		RateLimitedTestRunner.run(() -> {
			// Name is required
			final StravaActivity activity = TestUtils
					.createDefaultActivity("CreateManualActivityTest.testCreateManualActivity_noName"); //$NON-NLS-1$
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
		RateLimitedTestRunner.run(() -> {
			final StravaActivity activity = TestUtils
					.createDefaultActivity("CreateManualActivityTest.testCreateManualActivity_noStartDate"); //$NON-NLS-1$
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
		RateLimitedTestRunner.run(() -> {
			// Type is required
			final StravaActivity activity = TestUtils
					.createDefaultActivity("CreateManualActivityTest.testCreateManualActivity_noType"); //$NON-NLS-1$
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

	@Override
	@Test
	public void testCreateNonExistentParent() throws Exception {
		// There's no parent involved for an activity
		return;
	}

	@Override
	@Test
	public void testPrivateWithViewPrivateScope() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Create the object test data
			final StravaActivity activity = generateValidObject();
			activity.setPrivateActivity(Boolean.TRUE);

			// Create it in Strava
			final StravaActivity createdActivity = creator().create(TestUtils.stravaWithFullAccess(), activity);
			assertTrue(createdActivity.getPrivateActivity().booleanValue());

			// Validate
			StravaActivityTest.validateActivity(createdActivity);

			// Finally, delete it
			forceDelete(createdActivity);
		});
	}

	/**
	 * @see test.api.service.standardtests.spec.StandardTests#testInvalidId()
	 */
	@Override
	@Test
	public void testInvalidId() throws Exception {
		// For creating objects, you don't specify an id
		return;
	}

	/**
	 * @see test.api.service.standardtests.spec.PrivacyTests#testPrivateBelongsToOtherUser()
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

	@Override
	protected void forceDelete(StravaActivity activity) throws Exception {
		ActivityDataUtils.deleter().delete(TestUtils.stravaWithFullAccess(), activity);

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
	protected void validate(StravaActivity object) {
		StravaActivityTest.validateActivity(object);
	}

}
