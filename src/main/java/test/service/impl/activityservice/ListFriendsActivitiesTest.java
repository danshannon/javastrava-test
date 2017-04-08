package test.service.impl.activityservice;

import javastrava.api.v3.model.StravaActivity;
import test.service.standardtests.PagingListMethodTest;
import test.service.standardtests.callbacks.ListCallback;
import test.service.standardtests.callbacks.PagingListCallback;
import test.service.standardtests.data.ActivityDataUtils;
import test.service.standardtests.data.AthleteDataUtils;

/**
 * <p>
 * Specific tests for list friends activities methods
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListFriendsActivitiesTest extends PagingListMethodTest<StravaActivity, Integer> {
	@Override
	protected Integer idInvalid() {
		return null;
	}

	@Override
	protected Integer idPrivate() {
		return null;
	}

	@Override
	protected Integer idPrivateBelongsToOtherUser() {
		return null;
	}

	@Override
	protected Integer idValidWithEntries() {
		return AthleteDataUtils.ATHLETE_AUTHENTICATED_ID;
	}

	@Override
	protected Integer idValidWithoutEntries() {
		return null;
	}

	@Override
	protected ListCallback<StravaActivity, Integer> lister() {
		return ((strava, id) -> strava.listFriendsActivities());
	}

	@Override
	protected PagingListCallback<StravaActivity, Integer> pagingLister() {
		return ((strava, paging, id) -> strava.listFriendsActivities(paging));
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
