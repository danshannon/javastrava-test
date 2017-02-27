package test.api.rest.activity;

import java.util.Arrays;

import javastrava.api.v3.model.StravaLap;
import test.api.model.StravaLapTest;
import test.api.rest.APIListTest;
import test.issues.strava.Issue105;
import test.utils.TestUtils;

public class ListActivityLapsTest extends APIListTest<StravaLap, Long> {
	/**
	 *
	 */
	public ListActivityLapsTest() {
		this.listCallback = (api, id) -> api.listActivityLaps(id);
		this.pagingCallback = null;
		this.suppressPagingTests = true;
		this.listOtherReturns401Unauthorised = true;
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
		return TestUtils.ACTIVITY_PRIVATE_WITH_LAPS;
	}

	/**
	 * @see test.api.rest.APIListTest#privateIdBelongsToOtherUser()
	 */
	@Override
	protected Long privateIdBelongsToOtherUser() {
		return TestUtils.ACTIVITY_PRIVATE_OTHER_USER;
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
		return TestUtils.ACTIVITY_WITH_LAPS;
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
		return TestUtils.ACTIVITY_WITHOUT_LAPS;
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
