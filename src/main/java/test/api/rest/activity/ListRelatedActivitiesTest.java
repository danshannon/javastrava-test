package test.api.rest.activity;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.api.v3.service.exception.UnauthorizedException;

import org.junit.Test;

import test.api.model.StravaActivityTest;
import test.api.rest.util.ArrayCallback;
import test.api.rest.util.PagingArrayMethodTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class ListRelatedActivitiesTest extends PagingArrayMethodTest<StravaActivity, Integer> {
	@Override
	protected ArrayCallback<StravaActivity> callback() {
		return (paging -> api().listRelatedActivities(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER, paging.getPage(), paging.getPageSize()));
	}

	@Test
	public void testListRelatedActivities_invalidActivity() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().listRelatedActivities(TestUtils.ACTIVITY_INVALID, null, null);
			} catch (final NotFoundException e) {
				// Expected
				return;
			}
			fail("Returned related activities for an invalid activity!");
		});
	}

	@Test
	public void testListRelatedActivities_privateActivity() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().listRelatedActivities(TestUtils.ACTIVITY_PRIVATE_OTHER_USER, null, null);
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
			final StravaActivity[] activities = api().listRelatedActivities(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER, null, null);
			assertNotNull(activities);
			for (final StravaActivity activity : activities) {
				StravaActivityTest.validateActivity(activity, activity.getId(), activity.getResourceState());
			}

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
