package test.api.service.impl.activityservice;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.time.ZonedDateTime;
import java.util.List;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.service.exception.UnauthorizedException;
import javastrava.util.Paging;

import org.junit.Test;

import test.api.model.StravaActivityTest;
import test.api.service.impl.util.ListCallback;
import test.api.service.impl.util.PagingListMethodTest;

public class ListFriendsActivitiesTest extends PagingListMethodTest<StravaActivity, Integer> {
	/**
	 * <p>
	 * List latest {@link StravaActivity activities} for {@link StravaAthlete athletes} the currently authorised user is following
	 * </p>
	 *
	 * <p>
	 * Should return a list of rides in descending order of start date
	 * </p>
	 *
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testListFriendsActivities_hasFriends() {
		final List<StravaActivity> activities = service().listFriendsActivities(null);

		assertNotNull("Returned null array for latest friends' activities", activities);

		// Check that the activities are returned in descending order of start date
		ZonedDateTime lastStartDate = null;
		for (final StravaActivity activity : activities) {
			if (lastStartDate == null) {
				lastStartDate = activity.getStartDate();
			} else {
				if (activity.getStartDate().isAfter(lastStartDate)) {
					fail("Activities not returned in descending start date order");
				}
			}
			StravaActivityTest.validateActivity(activity, activity.getId(), activity.getResourceState());
		}
	}

	@Override
	protected void validate(final StravaActivity activity, final Integer id, final StravaResourceState state) {
		StravaActivityTest.validateActivity(activity, id, state);

	}

	@Override
	protected void validate(final StravaActivity activity) {
		validate(activity, activity.getId(), activity.getResourceState());

	}

	@Override
	protected ListCallback<StravaActivity> callback() {
		return (new ListCallback<StravaActivity>() {

			@Override
			public List<StravaActivity> getList(final Paging paging) {
				return service().listFriendsActivities(paging);
			}

		});
	}

}
