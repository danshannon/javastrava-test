package test.api.rest.activity;

import java.util.Arrays;

import javastrava.api.v3.model.StravaLap;
import test.api.model.StravaLapTest;
import test.api.rest.APIListTest;
import test.api.rest.TestListArrayCallback;
import test.issues.strava.Issue105;
import test.service.standardtests.data.ActivityDataUtils;

public class ListActivityLapsTest extends APIListTest<StravaLap, Long> {
	@Override
	protected TestListArrayCallback<StravaLap, Long> listCallback() {
		return ((api, id) -> api.listActivityLaps(id));
	}

	/**
	 *
	 */
	public ListActivityLapsTest() {
		this.listOtherReturns401Unauthorised = true;
	}

	/**
	 * @see test.api.rest.APIListTest#invalidId()
	 */
	@Override
	protected Long invalidId() {
		return ActivityDataUtils.ACTIVITY_INVALID;
	}

	/**
	 * @see test.api.rest.APIListTest#privateId()
	 */
	@Override
	protected Long privateId() {
		return ActivityDataUtils.ACTIVITY_PRIVATE_WITH_LAPS;
	}

	/**
	 * @see test.api.rest.APIListTest#privateIdBelongsToOtherUser()
	 */
	@Override
	protected Long privateIdBelongsToOtherUser() {
		return ActivityDataUtils.ACTIVITY_PRIVATE_OTHER_USER;
	}

	/**
	 * @see test.api.rest.APIListTest#validate(java.lang.Object)
	 */
	@Override
	protected void validate(final StravaLap lap) {
		StravaLapTest.validateLap(lap);

	}

	/**
	 * @see test.api.rest.APIListTest#validateArray(java.lang.Object[])
	 */
	@Override
	protected void validateArray(final StravaLap[] list) {
		StravaLapTest.validateList(Arrays.asList(list));

	}

	/**
	 * @see test.api.rest.APIListTest#validId()
	 */
	@Override
	protected Long validId() {
		return ActivityDataUtils.ACTIVITY_WITH_LAPS;
	}

	/**
	 * @see test.api.rest.APIListTest#validIdBelongsToOtherUser()
	 */
	@Override
	protected Long validIdBelongsToOtherUser() {
		return ActivityDataUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER;
	}

	/**
	 * @see test.api.rest.APIListTest#validIdNoChildren()
	 */
	@Override
	protected Long validIdNoChildren() {
		return ActivityDataUtils.ACTIVITY_WITHOUT_LAPS;
	}

	/**
	 * @see test.api.rest.APIListTest#list_validParentNoChildren()
	 */
	@Override
	public void list_validParentNoChildren() throws Exception {
		if (new Issue105().isIssue()) {
			return;
		}
		super.list_validParentNoChildren();
	}

}
