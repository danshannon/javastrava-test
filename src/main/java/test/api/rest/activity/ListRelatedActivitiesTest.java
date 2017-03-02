package test.api.rest.activity;

import java.util.Arrays;

import javastrava.api.v3.model.StravaActivity;
import test.api.model.StravaActivityTest;
import test.api.rest.APIPagingListTest;
import test.api.rest.TestListArrayCallback;
import test.api.rest.util.ArrayCallback;
import test.utils.TestUtils;

public class ListRelatedActivitiesTest extends APIPagingListTest<StravaActivity, Long> {
	@Override
	protected ArrayCallback<StravaActivity> pagingCallback() {
		return paging -> api().listRelatedActivities(validId(), paging.getPage(), paging.getPageSize());
	}

	@Override
	protected TestListArrayCallback<StravaActivity, Long> listCallback() {
		return (api, id) -> api.listRelatedActivities(id, null, null);
	}

	/**
	 * @see test.api.rest.APIListTest#invalidId()
	 */
	@Override
	protected Long invalidId() {
		return TestUtils.ACTIVITY_INVALID;
	}

	/**
	 * @see test.api.rest.APIListTest#privateId()
	 */
	@Override
	protected Long privateId() {
		return TestUtils.ACTIVITY_PRIVATE_WITH_RELATED_ACTIVITIES;
	}

	/**
	 * @see test.api.rest.APIListTest#privateIdBelongsToOtherUser()
	 */
	@Override
	protected Long privateIdBelongsToOtherUser() {
		return TestUtils.ACTIVITY_PRIVATE_OTHER_USER;
	}

	@Override
	protected void validate(final StravaActivity activity) {
		StravaActivityTest.validateActivity(activity);
	}

	/**
	 * @see test.api.rest.APIListTest#validateArray(java.lang.Object[])
	 */
	@Override
	protected void validateArray(final StravaActivity[] list) {
		StravaActivityTest.validateList(Arrays.asList(list));
	}

	/**
	 * @see test.api.rest.APIListTest#validId()
	 */
	@Override
	protected Long validId() {
		return TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER;
	}

	/**
	 * @see test.api.rest.APIListTest#validIdBelongsToOtherUser()
	 */
	@Override
	protected Long validIdBelongsToOtherUser() {
		return TestUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER;
	}

	/**
	 * @see test.api.rest.APIListTest#validIdNoChildren()
	 */
	@Override
	protected Long validIdNoChildren() {
		return null;
	}

}
