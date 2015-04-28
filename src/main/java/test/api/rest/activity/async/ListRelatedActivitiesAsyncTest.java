package test.api.rest.activity.async;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.api.v3.service.exception.UnauthorizedException;

import org.junit.Test;

import test.api.model.StravaActivityTest;
import test.api.rest.util.ArrayCallback;
import test.api.rest.util.PagingArrayMethodAsyncTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class ListRelatedActivitiesAsyncTest extends PagingArrayMethodAsyncTest<StravaActivity, Integer> {
	@Override
	protected ArrayCallback<StravaActivity> pagingCallback() {
		return (paging -> api().listRelatedActivities(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER, paging.getPage(), paging.getPageSize()).get());
	}

	@Test
	public void testListRelatedActivities_invalidActivity() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().listRelatedActivities(TestUtils.ACTIVITY_INVALID, null, null).get();
			} catch (final NotFoundException e) {
				// Expected
				return;
			}
			fail("Returned related activities for an invalid activity!");
		});
	}

	@Test
	public void testListRelatedActivities_privateActivityOtherUser() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().listRelatedActivities(TestUtils.ACTIVITY_PRIVATE_OTHER_USER, null, null).get();
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}
			fail("Returned related activities for a private activity belonging to another user!");
		});
	}

	@Test
	public void testListRelatedActivities_validActivity() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaActivity[] activities = api().listRelatedActivities(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER, null, null).get();
			assertNotNull(activities);
			for (final StravaActivity activity : activities) {
				StravaActivityTest.validateActivity(activity, activity.getId(), activity.getResourceState());
			}

		});
	}

	@Test
	public void testListRelatedActivities_privateActivityWithoutViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().listRelatedActivities(TestUtils.ACTIVITY_PRIVATE_WITH_RELATED_ACTIVITIES, null, null).get();
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}
			fail("Returned related activities for a private activity belonging to the authenticated user, but do not have view_private access!");
		});
	}

	@Test
	public void testListRelatedActivities_privateActivityWithViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaActivity[] activities = apiWithViewPrivate().listRelatedActivities(TestUtils.ACTIVITY_PRIVATE_WITH_RELATED_ACTIVITIES, null, null)
					.get();
			assertNotNull(activities);
			assertNotEquals(0, activities.length);
		});
	}

	@Override
	protected void validate(final StravaActivity activity) {
		validate(activity, activity.getId(), activity.getResourceState());

	}

	@Override
	protected void validate(final StravaActivity activity, final Integer id, final StravaResourceState state) {
		StravaActivityTest.validateActivity(activity, id, state);

	}

}
