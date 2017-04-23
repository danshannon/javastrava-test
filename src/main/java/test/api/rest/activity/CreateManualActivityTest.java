package test.api.rest.activity;

import static org.junit.Assert.fail;

import org.junit.Test;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.reference.StravaActivityType;
import javastrava.api.v3.rest.API;
import javastrava.api.v3.service.exception.BadRequestException;
import test.api.rest.APICreateTest;
import test.api.rest.callback.APICreateCallback;
import test.service.standardtests.data.ActivityDataUtils;
import test.utils.RateLimitedTestRunner;

/**
 * <p>
 * Tests for {@link API#createManualActivity(StravaActivity)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class CreateManualActivityTest extends APICreateTest<StravaActivity, Integer> {
	@Override
	public void create_invalidParent() throws Exception {
		return;
	}

	@Override
	public void create_privateParentBelongsToOtherUser() throws Exception {
		return;
	}

	@Override
	public void create_privateParentWithoutViewPrivate() throws Exception {
		return;
	}

	@Override
	public void create_privateParentWithViewPrivate() throws Exception {
		return;
	}

	@Override
	protected StravaActivity createObject() {
		return ActivityDataUtils.createDefaultActivity("CreateManualActivityTest"); //$NON-NLS-1$
	}

	@Override
	protected APICreateCallback<StravaActivity, Integer> creator() {
		return ((api, activity, id) -> api.createManualActivity(activity));
	}

	@Override
	protected void forceDelete(final StravaActivity activity) {
		forceDeleteActivity(activity);
	}

	/**
	 * @see test.api.rest.APICreateTest#invalidParentId()
	 */
	@Override
	protected Integer invalidParentId() {
		return null;
	}

	/**
	 * @see test.api.rest.APICreateTest#privateParentId()
	 */
	@Override
	protected Integer privateParentId() {
		return null;
	}

	/**
	 * @see test.api.rest.APICreateTest#privateParentOtherUserId()
	 */
	@Override
	protected Integer privateParentOtherUserId() {
		return null;
	}

	/**
	 * Attempt to create a manual activity with an invalid type. Call to create API should return a {@link BadRequestException}
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Test
	public void testCreateManualActivity_invalidType() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Type must be one of the specified values
			final StravaActivity activity = createObject();
			StravaActivity stravaResponse = null;
			activity.setType(StravaActivityType.UNKNOWN);
			try {
				stravaResponse = creator().create(apiWithWriteAccess(), activity, null);
			} catch (final BadRequestException e1) {
				// Expected behaviour
				return;
			}
			// If it did get created, delete it again
			forceDelete(stravaResponse);
			fail("Created an activity with invalid type in error (was " + StravaActivityType.UNKNOWN + ", is " //$NON-NLS-1$ //$NON-NLS-2$
					+ stravaResponse.getType() + ")"); //$NON-NLS-1$
		});
	}

	/**
	 * Attempt to create a manual activity with no elapsed time specified. Call to create API should return a {@link BadRequestException}
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Test
	public void testCreateManualActivity_noElapsedTime() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaActivity activity = createObject();
			StravaActivity stravaResponse = null;
			// Elapsed time is required
			activity.setElapsedTime(null);
			try {
				stravaResponse = creator().create(apiWithWriteAccess(), activity, null);
			} catch (final BadRequestException e1) {
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
	 * Attempt to create an incomplete manual {@link StravaActivity} for the user without a name
	 * </p>
	 *
	 * <p>
	 * Should fail to create the activity in each case where a required attribute is missing
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Test
	public void testCreateManualActivity_noName() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Name is required
			final StravaActivity activity = createObject();
			StravaActivity stravaResponse = null;
			activity.setDescription(activity.getName());
			activity.setName(null);
			try {
				stravaResponse = creator().create(apiWithWriteAccess(), activity, null);
			} catch (final BadRequestException e1) {
				// Expected behaviour
				return;
			}

			// If it did get created, delete it again
			if (stravaResponse != null) {
				forceDelete(stravaResponse);
			}

			fail("Created an activity with no name in error" + stravaResponse); //$NON-NLS-1$
		});
	}

	/**
	 * Attempt to create a manual activity with no start time specified. Call to create API should return a {@link BadRequestException}
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Test
	public void testCreateManualActivity_noStartDate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaActivity activity = createObject();
			StravaActivity stravaResponse = null;
			// Start date is required
			activity.setStartDateLocal(null);
			try {
				stravaResponse = creator().create(apiWithWriteAccess(), activity, null);
			} catch (final BadRequestException e) {
				// Expected behaviour
				return;
			}

			// If it did get created, delete it again
			forceDelete(stravaResponse);

			fail("Created an activity with no start date in error" + stravaResponse); //$NON-NLS-1$
		});
	}

	/**
	 * Attempt to create a manual activity with no start time specified. Call to create API should return a {@link BadRequestException}
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Test
	public void testCreateManualActivity_noType() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Type is required
			final StravaActivity activity = createObject();
			StravaActivity stravaResponse = null;
			activity.setType(null);
			try {
				stravaResponse = creator().create(apiWithWriteAccess(), activity, null);
			} catch (final BadRequestException e1) {
				// Expected behaviour
				return;
			}

			// If it did get created, delete it again
			forceDelete(stravaResponse);

			fail("Created an activity with no type in error" + stravaResponse); //$NON-NLS-1$
		});
	}

	/**
	 * @see test.api.rest.APITest#validate(java.lang.Object)
	 */
	@Override
	protected void validate(final StravaActivity activity) throws Exception {
		ActivityDataUtils.validate(activity);
	}

	/**
	 * @see test.api.rest.APICreateTest#validParentId()
	 */
	@Override
	protected Integer validParentId() {
		return null;
	}

	/**
	 * @see test.api.rest.APICreateTest#validParentOtherUserId()
	 */
	@Override
	protected Integer validParentOtherUserId() {
		return null;
	}

}
