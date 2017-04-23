package test.api.rest.activity;

import static org.junit.Assert.fail;
import static org.junit.Assume.assumeTrue;

import org.junit.Test;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.rest.API;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.api.v3.service.exception.UnauthorizedException;
import javastrava.config.JavastravaApplicationConfig;
import test.api.rest.APIDeleteTest;
import test.api.rest.callback.APIDeleteCallback;
import test.service.standardtests.data.ActivityDataUtils;

/**
 * <p>
 * Tests for {@link API#deleteActivity(Long)} methods
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class DeleteActivityTest extends APIDeleteTest<StravaActivity, Long> {
	@Override
	protected StravaActivity createObject(String name) {
		final StravaActivity activity = ActivityDataUtils.createDefaultActivity(name);
		final StravaActivity uploadActivity = apiWithFullAccess().createManualActivity(activity);
		return uploadActivity;
	}

	@Override
	@Test
	public void delete_invalidParent() throws Exception {
		// Can't execute the test unless we have Strava's application-level permission to delete activities
		assumeTrue(JavastravaApplicationConfig.STRAVA_ALLOWS_ACTIVITY_DELETE);

		try {
			apiWithFullAccess().deleteActivity(ActivityDataUtils.ACTIVITY_INVALID);
		} catch (final NotFoundException e) {
			// Expected
			return;
		}
		fail("Attempt to delete a non-existent activity with id = " + ActivityDataUtils.ACTIVITY_INVALID + " appears to have worked successfully!"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Override
	@Test
	public void delete_privateParentBelongsToOtherUser() throws Exception {
		// Can't execute the test unless we have Strava's application-level permission to delete activities
		assumeTrue(JavastravaApplicationConfig.STRAVA_ALLOWS_ACTIVITY_DELETE);

		try {
			apiWithFullAccess().deleteActivity(ActivityDataUtils.ACTIVITY_PRIVATE_OTHER_USER);
		} catch (final UnauthorizedException e) {
			// expected
			return;
		}
		fail("Attempt to delete an activity belonging to another user with id = " + ActivityDataUtils.ACTIVITY_PRIVATE_OTHER_USER + " appears to have worked successfully (which is bad)"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Override
	public void delete_privateParentWithoutViewPrivate() throws Exception {
		// Can't execute the test unless we have Strava's application-level permission to delete activities
		assumeTrue(JavastravaApplicationConfig.STRAVA_ALLOWS_ACTIVITY_DELETE);

		super.delete_privateParentWithoutViewPrivate();
	}

	@Override
	public void delete_privateParentWithViewPrivate() throws Exception {
		// Can't execute the test unless we have Strava's application-level permission to delete activities
		assumeTrue(JavastravaApplicationConfig.STRAVA_ALLOWS_ACTIVITY_DELETE);

		super.delete_privateParentWithViewPrivate();
	}

	@Override
	public void delete_valid() throws Exception {
		// Can't execute the test unless we have Strava's application-level permission to delete activities
		assumeTrue(JavastravaApplicationConfig.STRAVA_ALLOWS_ACTIVITY_DELETE);

		super.delete_valid();
	}

	@Override
	public void delete_validParentNoWriteAccess() throws Exception {
		// Can't execute the test unless we have Strava's application-level permission to delete activities
		assumeTrue(JavastravaApplicationConfig.STRAVA_ALLOWS_ACTIVITY_DELETE);

		super.delete_validParentNoWriteAccess();
	}

	@Override
	protected APIDeleteCallback<StravaActivity> deleter() {
		return ((api, activity) -> api.deleteActivity(activity.getId()));
	}

	@Override
	protected void forceDelete(final StravaActivity activity) {
		forceDeleteActivity(activity);
	}

	@Override
	protected Long invalidParentId() {
		return ActivityDataUtils.ACTIVITY_INVALID;
	}

	@Override
	protected Long privateParentId() {
		// Create a private activity
		StravaActivity activity = createPrivateActivity("DeleteActivityTest.privateParentId"); //$NON-NLS-1$
		activity.setPrivateActivity(Boolean.TRUE);
		activity = apiWithFullAccess().createManualActivity(activity);
		return activity.getId();
	}

	@Override
	protected Long privateParentOtherUserId() {
		return ActivityDataUtils.ACTIVITY_PRIVATE_OTHER_USER;
	}

	@Override
	protected void validate(final StravaActivity result) throws Exception {
		ActivityDataUtils.validate(result);

	}

	@Override
	protected Long validParentId() {
		// Create a non-private activity
		StravaActivity activity = ActivityDataUtils.createDefaultActivity("DeleteActivityTest.notPrivateParentId"); //$NON-NLS-1$
		activity = apiWithFullAccess().createManualActivity(activity);
		return activity.getId();
	}

	@Override
	protected StravaActivity createPrivateObject(String name) {
		final StravaActivity activity = ActivityDataUtils.createDefaultActivity(name);
		activity.setPrivateActivity(Boolean.TRUE);
		final StravaActivity uploadActivity = apiWithFullAccess().createManualActivity(activity);
		return uploadActivity;
	}

	@Override
	protected String classUnderTest() {
		return this.getClass().getName();
	}

}
