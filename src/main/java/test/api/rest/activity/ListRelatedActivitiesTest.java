package test.api.rest.activity;

import java.util.Arrays;

import javastrava.api.v3.model.StravaActivity;
import test.api.model.StravaActivityTest;
import test.api.rest.APIListTest;
import test.utils.TestUtils;

public class ListRelatedActivitiesTest extends APIListTest<StravaActivity, Integer> {
	/**
	 *
	 */
	public ListRelatedActivitiesTest() {
		this.listCallback = (api, id) -> api.listRelatedActivities(id, null, null);
		this.pagingCallback = paging -> api().listRelatedActivities(validId(), paging.getPage(), paging.getPageSize());
	}

	/**
	 * @see test.api.rest.APIListTest#invalidId()
	 */
	@Override
	protected Integer invalidId() {
		return TestUtils.ACTIVITY_INVALID;
	}

	/**
	 * @see test.api.rest.APIListTest#privateId()
	 */
	@Override
	protected Integer privateId() {
		return TestUtils.ACTIVITY_PRIVATE_WITH_RELATED_ACTIVITIES;
	}

	/**
	 * @see test.api.rest.APIListTest#privateIdBelongsToOtherUser()
	 */
	@Override
	protected Integer privateIdBelongsToOtherUser() {
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
	protected Integer validId() {
		return TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER;
	}

	/**
	 * @see test.api.rest.APIListTest#validIdBelongsToOtherUser()
	 */
	@Override
	protected Integer validIdBelongsToOtherUser() {
		return TestUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER;
	}

	/**
	 * @see test.api.rest.APIListTest#validIdNoChildren()
	 */
	@Override
	protected Integer validIdNoChildren() {
		return null;
	}

}
