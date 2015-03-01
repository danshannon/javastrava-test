package test.api.service.impl.retrofit.activityservices;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.List;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.service.ActivityServices;
import javastrava.api.v3.service.exception.UnauthorizedException;
import javastrava.api.v3.service.impl.retrofit.ActivityServicesImpl;
import javastrava.util.Paging;

import org.junit.Test;

import test.api.model.StravaActivityTest;
import test.api.service.impl.ListCallback;
import test.api.service.impl.PagingListMethodTest;
import test.utils.TestUtils;

public class ListFriendsActivitiesTest extends PagingListMethodTest<StravaActivity,Integer>{
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
		Date lastStartDate = null;
		for (final StravaActivity activity : activities) {
			if (lastStartDate == null) {
				lastStartDate = activity.getStartDate();
			} else {
				if (activity.getStartDate().after(lastStartDate)) {
					fail("Activities not returned in descending start date order");
				}
			}
			StravaActivityTest.validateActivity(activity, activity.getId(), activity.getResourceState());
		}
	}

	private ActivityServices service() {
		return ActivityServicesImpl.implementation(TestUtils.getValidToken());
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

	@Override
	@Test
	// TODO This is only here as a TEST workaround for issue #18 (https://github.com/danshannon/javastravav3api/issues/18). When the issue is fixed, remove this override altogether!
	public void testPageNumberAndSize() {
		final List<StravaActivity> bothPages = callback().getList(new Paging(1,3));
		assertNotNull(bothPages);
		assertEquals(3,bothPages.size());
		validateList(bothPages);
		final List<StravaActivity> firstPage = callback().getList(new Paging(1,1));
		assertNotNull(firstPage);
		assertEquals(1,firstPage.size());
		validateList(firstPage);
		final List<StravaActivity> secondPage = callback().getList(new Paging(2,1));
		assertNotNull(secondPage);
		assertEquals(1,secondPage.size());
		validateList(secondPage);
		final List<StravaActivity> thirdPage = callback().getList(new Paging(3,1));
		assertNotNull(thirdPage);
		assertEquals(1,thirdPage.size());

		// assertTrue(firstPage.get(0).getStartDate().after(secondPage.get(0).getStartDate()));
		// assertTrue(secondPage.get(0).getStartDate().after(thirdPage.get(0).getStartDate()));

		// The first entry in bothPages should be the same as the first entry in firstPage
		// assertEquals(bothPages.get(0),firstPage.get(0));

		// The second entry in bothPages should be the same as the first entry in secondPage
		// assertEquals(bothPages.get(1),secondPage.get(0));
	}


}
