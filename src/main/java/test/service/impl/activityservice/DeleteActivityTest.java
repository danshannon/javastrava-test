package test.service.impl.activityservice;

import static org.junit.Assert.fail;

import org.junit.Test;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.api.v3.service.exception.UnauthorizedException;
import test.api.model.StravaActivityTest;
import test.service.standardtests.DeleteMethodTest;
import test.service.standardtests.callbacks.CreateCallback;
import test.service.standardtests.callbacks.DeleteCallback;
import test.service.standardtests.callbacks.GetCallback;
import test.service.standardtests.data.ActivityDataUtils;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * <p>
 * Tests related to deleting activities
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class DeleteActivityTest extends DeleteMethodTest<StravaActivity, Long> {

	@Override
	@Test
	public void testDeleteNonExistentParent() throws Exception {
		// Activities don't have parents so this isn't relevant
		return;
	}

	@Override
	@Test
	public void testInvalidId() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Fake activity
			final StravaActivity activity = generateValidObject();
			activity.setId(TestUtils.ACTIVITY_INVALID);

			// Attempt to delete
			final StravaActivity deletedActivity;
			try {
				deletedActivity = deleter().delete(TestUtils.stravaWithFullAccess(), activity);
			} catch (final NotFoundException e) {
				// Expected
				return;
			}

			// Failure
			fail("Successfully deleted an activity " + deletedActivity.getId() + " that does not exist!"); //$NON-NLS-1$ //$NON-NLS-2$
		});

	}

	@Override
	@Test
	public void testPrivateBelongsToOtherUser() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Fake activity
			final StravaActivity activity = generateValidObject();
			activity.setId(TestUtils.ACTIVITY_PRIVATE_OTHER_USER);

			// Attempt to delete
			final StravaActivity deletedActivity;
			try {
				deletedActivity = deleter().delete(TestUtils.stravaWithFullAccess(), activity);
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}

			// Failure
			fail("Successfully deleted an activity " + deletedActivity.getId() + " that is private and belongs to another user!"); //$NON-NLS-1$ //$NON-NLS-2$
		});
	}

	@Override
	@Test
	public void testPrivateWithViewPrivateScope() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Generate test data
			final StravaActivity activity = generateValidObject();
			activity.setPrivateActivity(Boolean.TRUE);
			final StravaActivity createdActivity = creator().create(TestUtils.stravaWithFullAccess(), activity);

			// Now delete it
			deleter().delete(TestUtils.stravaWithFullAccess(), createdActivity);

			// If that worked, it's all good
			return;
		});
	}

	@Override
	@Test
	public void testPrivateWithNoViewPrivateScope() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Generate test data
			final StravaActivity activity = generateValidObject();
			activity.setPrivateActivity(Boolean.TRUE);
			final StravaActivity createdActivity = creator().create(TestUtils.stravaWithFullAccess(), activity);

			// Now try to delete it
			try {
				deleter().delete(TestUtils.stravaWithWriteAccess(), createdActivity);
			} catch (final UnauthorizedException e) {
				// Expected
				deleter().delete(TestUtils.stravaWithFullAccess(), createdActivity);
				return;
			}

			// Fail
			fail("Successfully deleted a private activity, but don't have view_private scope in token!"); //$NON-NLS-1$

			// If that worked, it's all good
			return;
		});
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
		StravaActivityTest.validate(object);
	}

}
