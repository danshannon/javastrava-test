package test.api.service.impl.activityservice;

import javastrava.api.v3.model.StravaActivity;
import test.api.model.StravaActivityTest;
import test.api.service.standardtests.PagingListMethodTest;
import test.api.service.standardtests.callbacks.ListCallback;
import test.api.service.standardtests.callbacks.PagingListCallback;
import test.utils.TestUtils;

/**
 * <p>
 * Specific tests for list related activities methods
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListRelatedActivitiesTest extends PagingListMethodTest<StravaActivity, Long> {
	@Override
	protected PagingListCallback<StravaActivity, Long> pagingLister() {
		return ((strava, paging, id) -> strava.listRelatedActivities(id, paging));
	}

	@Override
	protected void validate(final StravaActivity activity) {
		StravaActivityTest.validateActivity(activity);
	}

	@Override
	protected ListCallback<StravaActivity, Long> lister() {
		return ((strava, id) -> strava.listRelatedActivities(id));
	}

	@Override
	protected Long idPrivate() {
		return TestUtils.ACTIVITY_PRIVATE_WITH_RELATED_ACTIVITIES;
	}

	@Override
	protected Long idPrivateBelongsToOtherUser() {
		return TestUtils.ACTIVITY_PRIVATE_OTHER_USER;
	}

	@Override
	protected Long idValidWithEntries() {
		return TestUtils.ACTIVITY_WITH_RELATED_ACTIVITIES;
	}

	@Override
	protected Long idValidWithoutEntries() {
		return TestUtils.ACTIVITY_WITHOUT_RELATED_ACTIVITIES;
	}

	@Override
	protected Long idInvalid() {
		return TestUtils.ACTIVITY_INVALID;
	}

}
