package test.api.rest.activity;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.rest.API;
import test.api.model.StravaActivityTest;
import test.api.rest.APIDeleteTest;
import test.api.rest.callback.TestDeleteCallback;
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
	protected TestDeleteCallback<StravaActivity, Long> deleter() {
		return ((api, activity, id) -> api.deleteActivity(id));
	}

	/**
	 * @see test.api.rest.APIDeleteTest#createObject()
	 */
	@Override
	protected StravaActivity createObject() {
		return ActivityDataUtils.createDefaultActivity("DeleteActivityTest"); //$NON-NLS-1$
	}

	/**
	 * @see test.api.rest.APIDeleteTest#forceDelete(java.lang.Object)
	 */
	@Override
	protected void forceDelete(final StravaActivity activity) {
		forceDeleteActivity(activity);
	}

	/**
	 * @see test.api.rest.APIDeleteTest#invalidParentId()
	 */
	@Override
	protected Long invalidParentId() {
		return ActivityDataUtils.ACTIVITY_INVALID;
	}

	/**
	 * @see test.api.rest.APIDeleteTest#privateParentId()
	 */
	@Override
	protected Long privateParentId() {
		// Create a private activity
		StravaActivity activity = ActivityDataUtils.createDefaultActivity("DeleteActivityTest.privateParentId"); //$NON-NLS-1$
		activity.setPrivateActivity(Boolean.TRUE);
		activity = apiWithFullAccess().createManualActivity(activity);
		return activity.getId();
	}

	/**
	 * @see test.api.rest.APIDeleteTest#privateParentOtherUserId()
	 */
	@Override
	protected Long privateParentOtherUserId() {
		return ActivityDataUtils.ACTIVITY_PRIVATE_OTHER_USER;
	}

	/**
	 * @see test.api.rest.APITest#validate(java.lang.Object)
	 */
	@Override
	protected void validate(final StravaActivity result) throws Exception {
		StravaActivityTest.validate(result);

	}

	/**
	 * @see test.api.rest.APIDeleteTest#validParentId()
	 */
	@Override
	protected Long validParentId() {
		// Create a private activity
		StravaActivity activity = ActivityDataUtils.createDefaultActivity("DeleteActivityTest.privateParentId"); //$NON-NLS-1$
		activity = apiWithFullAccess().createManualActivity(activity);
		return activity.getId();
	}

}
