package test.api.service.impl.activityservice;

import static org.junit.Assert.fail;

import org.junit.Test;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.reference.StravaActivityType;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.service.exception.UnauthorizedException;
import test.api.model.StravaActivityTest;
import test.api.service.standardtests.CreateMethodTest;
import test.api.service.standardtests.callbacks.CreateCallback;
import test.api.service.standardtests.callbacks.DeleteCallback;
import test.api.service.standardtests.callbacks.GetCallback;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class CreateManualActivityTest extends CreateMethodTest<StravaActivity, Integer, Void> {
	@Test
	public void testCreateManualActivity_invalidType() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Type must be one of the specified values
			final StravaActivity activity = TestUtils.createDefaultActivity("CreateManualActivityTest.testCreateManualActivity_invalidType");
			StravaActivity stravaResponse = null;
			activity.setType(StravaActivityType.UNKNOWN);
			try {
				stravaResponse = stravaWithWriteAccess().createManualActivity(activity);
			} catch (final IllegalArgumentException e1) {
				// Expected behaviour
				return;
			} catch (final Exception e2) {
				// Ignore ALL other exceptions
			}

			// If it did get created, delete it again
			forceDelete(stravaResponse);

			fail("Created an activity with invalid type in error");
		});
	}

	@Test
	public void testCreateManualActivity_noElapsedTime() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaActivity activity = TestUtils.createDefaultActivity("CreateManualActivityTest.testCreateManualActivity_noElapsedTime");
			StravaActivity stravaResponse = null;
			// Elapsed time is required
			activity.setElapsedTime(null);
			try {
				stravaResponse = stravaWithWriteAccess().createManualActivity(activity);
			} catch (final IllegalArgumentException e1) {
				// Expected behaviour
				return;
			} catch (final Exception e2) {
				// Ignore ALL other exceptions
			}

			// If it did get created, delete it again
			forceDelete(stravaResponse);

			fail("Created an activity with no elapsed time in error" + stravaResponse);
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
	 *
	 * @throws UnauthorizedException
	 */
	@Test
	public void testCreateManualActivity_noName() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Name is required
			final StravaActivity activity = TestUtils.createDefaultActivity("CreateManualActivityTest.testCreateManualActivity_noName");
			StravaActivity stravaResponse = null;
			activity.setDescription(activity.getName());
			activity.setName(null);
			try {
				stravaResponse = stravaWithWriteAccess().createManualActivity(activity);
			} catch (final IllegalArgumentException e1) {
				// Expected behaviour
				return;
			} catch (final Exception e2) {
				// Ignore ALL other exceptions
			}

			// If it did get created, delete it again
			if (stravaResponse != null) {
				forceDelete(stravaResponse);
			}

			fail("Created an activity with no type in error" + stravaResponse);
		});
	}

	@Test
	public void testCreateManualActivity_noStartDate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaActivity activity = TestUtils.createDefaultActivity("CreateManualActivityTest.testCreateManualActivity_noStartDate");
			StravaActivity stravaResponse = null;
			// Start date is required
			activity.setStartDateLocal(null);
			try {
				stravaResponse = stravaWithWriteAccess().createManualActivity(activity);
			} catch (final IllegalArgumentException e1) {
				// Expected behaviour
				return;
			} catch (final Exception e2) {
				// Ignore ALL other exceptions
			}

			// If it did get created, delete it again
			forceDelete(stravaResponse);

			fail("Created an activity with no start date in error" + stravaResponse);
		});
	}

	@Test
	public void testCreateManualActivity_noType() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Type is required
			final StravaActivity activity = TestUtils.createDefaultActivity("CreateMannualActivityTest.testCreateManualActivity_noType");
			StravaActivity stravaResponse = null;
			activity.setType(null);
			try {
				stravaResponse = stravaWithWriteAccess().createManualActivity(activity);
			} catch (final IllegalArgumentException e1) {
				// Expected behaviour
				return;
			} catch (final Exception e2) {
				// Ignore ALL other exceptions
			}

			// If it did get created, delete it again
			forceDelete(stravaResponse);

			fail("Created an activity with no type in error" + stravaResponse);
		});
	}

	/**
	 * @see test.api.service.standardtests.CreateMethodTest#callback()
	 */
	@Override
	protected CreateCallback<StravaActivity, Void> createCallback() {
		return ((strava, object, parentId) -> {
			return strava.createManualActivity(object);
		});
	}

	/**
	 * @see test.api.service.standardtests.CreateMethodTest#generateValidObject(java.lang.Object)
	 */
	@Override
	protected StravaActivity generateValidObject(final Void parentId) {
		return TestUtils.createDefaultActivity("CreateManualActivityTest.validActivity");
	}

	/**
	 * @see test.api.service.standardtests.CreateMethodTest#generateInvalidObject(java.lang.Object)
	 */
	@Override
	protected StravaActivity generateInvalidObject(final Void parentId) {
		final StravaActivity activity = TestUtils.createDefaultActivity("CreateManualActivityTest.invalidActivity");
		// Start date is required
		activity.setStartDateLocal(null);
		return activity;
	}

	/**
	 * @see test.utils.TestDataUtils#getInvalidId()
	 */
	@Override
	protected Integer getInvalidId() {
		return null;
	}

	/**
	 * @see test.utils.TestDataUtils#getValidId()
	 */
	@Override
	protected Integer getValidId() {
		return null;
	}

	/**
	 * @see test.utils.TestDataUtils#getValidParentId()
	 */
	@Override
	protected Void getValidParentId() {
		return null;
	}

	/**
	 * @see test.utils.TestDataUtils#getInvalidParentId()
	 */
	@Override
	protected Void getInvalidParentId() {
		return null;
	}

	/**
	 * @see test.utils.TestDataUtils#getIdPrivateBelongsToOtherUser()
	 */
	@Override
	protected Integer getIdPrivateBelongsToOtherUser() {
		return null;
	}

	/**
	 * @see test.utils.TestDataUtils#getIdPrivateBelongsToAuthenticatedUser()
	 */
	@Override
	protected Integer getIdPrivateBelongsToAuthenticatedUser() {
		return null;
	}

	/**
	 * @see test.utils.TestDataUtils#getParentIdPrivateBelongsToOtherUser()
	 */
	@Override
	protected Void getParentIdPrivateBelongsToOtherUser() {
		return null;
	}

	/**
	 * @see test.utils.TestDataUtils#getParentIdPrivateBelongsToAuthenticatedUser()
	 */
	@Override
	protected Void getParentIdPrivateBelongsToAuthenticatedUser() {
		return null;
	}

	/**
	 * @see test.utils.TestDataUtils#getValidParentWithEntries()
	 */
	@Override
	protected Void getValidParentWithEntries() {
		return null;
	}

	/**
	 * @see test.utils.TestDataUtils#getValidParentWithNoEntries()
	 */
	@Override
	protected Void getValidParentWithNoEntries() {
		return null;
	}

	/**
	 * @see test.utils.TestDataUtils#validate(javastrava.api.v3.model.StravaEntity, java.lang.Object, javastrava.api.v3.model.reference.StravaResourceState)
	 */
	@Override
	protected void validate(final StravaActivity object, final Integer id, final StravaResourceState state) {
		StravaActivityTest.validateActivity(object, id, state);

	}

	/**
	 * @see test.utils.TestDataUtils#deleter()
	 */
	@Override
	protected DeleteCallback<StravaActivity, Integer, Void> deleteCallback() {
		return ((strava, id, parentId) -> {
			return strava.deleteActivity(id);
		});
	}

	/**
	 * @see test.utils.TestDataUtils#getter()
	 */
	@Override
	protected GetCallback<StravaActivity, Integer> getCallback() {
		return ((strava, id) -> {
			return strava.getActivity(id);
		});
	}

	/**
	 * @see test.utils.TestDataUtils#isTransient()
	 */
	@Override
	protected boolean isTransient() {
		return true;
	}

	/**
	 * @see test.utils.TestDataUtils#generateTestObject(java.lang.Object, java.lang.Object)
	 */
	@Override
	protected StravaActivity generateTestObject(final Integer id, final Void parentId) {
		return generateValidObject(parentId);
	}

}
