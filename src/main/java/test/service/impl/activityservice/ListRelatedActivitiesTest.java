package test.service.impl.activityservice;

import javastrava.api.v3.model.StravaActivity;
import test.service.standardtests.PagingListMethodTest;
import test.service.standardtests.callbacks.ListCallback;
import test.service.standardtests.callbacks.PagingListCallback;
import test.service.standardtests.data.ActivityDataUtils;

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
	protected Long idInvalid() {
		return ActivityDataUtils.ACTIVITY_INVALID;
	}

	@Override
	protected Long idPrivate() {
		return ActivityDataUtils.ACTIVITY_PRIVATE_WITH_RELATED_ACTIVITIES;
	}

	@Override
	protected Long idPrivateBelongsToOtherUser() {
		return ActivityDataUtils.ACTIVITY_PRIVATE_OTHER_USER;
	}

	@Override
	protected Long idValidWithEntries() {
		return ActivityDataUtils.ACTIVITY_WITH_RELATED_ACTIVITIES;
	}

	@Override
	protected Long idValidWithoutEntries() {
		return ActivityDataUtils.ACTIVITY_WITHOUT_RELATED_ACTIVITIES;
	}

	@Override
	protected ListCallback<StravaActivity, Long> lister() {
		return ((strava, id) -> strava.listRelatedActivities(id));
	}

	@Override
	protected PagingListCallback<StravaActivity, Long> pagingLister() {
		return ((strava, paging, id) -> strava.listRelatedActivities(id, paging));
	}

	@Override
	protected void validate(final StravaActivity activity) {
		ActivityDataUtils.validate(activity);
	}

	@Override
	protected Class<StravaActivity> classUnderTest() {
		return StravaActivity.class;
	}

}
