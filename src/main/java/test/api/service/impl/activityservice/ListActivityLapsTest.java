package test.api.service.impl.activityservice;

import javastrava.api.v3.model.StravaLap;
import javastrava.api.v3.model.reference.StravaResourceState;
import test.api.model.StravaLapTest;
import test.api.service.standardtests.ListMethodTest;
import test.api.service.standardtests.callbacks.ListCallback;
import test.issues.strava.Issue105;
import test.utils.TestUtils;

public class ListActivityLapsTest extends ListMethodTest<StravaLap, Integer> {

	/**
	 * @see test.api.service.standardtests.spec.ListMethodTests#getValidParentWithEntries()
	 */
	@Override
	public Integer getValidParentWithEntries() {
		return TestUtils.ACTIVITY_WITH_LAPS;
	}

	/**
	 * @see test.api.service.standardtests.spec.ListMethodTests#getValidParentWithNoEntries()
	 */
	@Override
	public Integer getValidParentWithNoEntries() {
		return TestUtils.ACTIVITY_WITHOUT_LAPS;
	}

	/**
	 * @see test.api.service.standardtests.spec.ListMethodTests#getIdPrivateBelongsToOtherUser()
	 */
	@Override
	public Integer getIdPrivateBelongsToOtherUser() {
		return TestUtils.ACTIVITY_PRIVATE_OTHER_USER;
	}

	/**
	 * @see test.api.service.standardtests.spec.ListMethodTests#getIdPrivateBelongsToAuthenticatedUser()
	 */
	@Override
	public Integer getIdPrivateBelongsToAuthenticatedUser() {
		return TestUtils.ACTIVITY_PRIVATE_WITH_LAPS;
	}

	/**
	 * @see test.api.service.standardtests.spec.ListMethodTests#getIdInvalid()
	 */
	@Override
	public Integer getInvalidId() {
		return TestUtils.ACTIVITY_INVALID;
	}

	/**
	 * @see test.api.service.standardtests.ListMethodTest#callback(javastrava.api.v3.service.Strava)
	 */
	@Override
	protected ListCallback<StravaLap, Integer> callback() {
		return ((strava, parentId) -> {
			return strava.listActivityLaps(parentId);
		});
	}

	/**
	 * @see test.api.service.standardtests.ListMethodTest#testValidParentWithNoEntries()
	 */
	@Override
	public void testValidParentWithNoEntries() throws Exception {
		if (new Issue105().isIssue()) {
			return;
		}
		super.testValidParentWithNoEntries();
	}

	/**
	 * @see test.api.service.StravaTest#validate(java.lang.Object)
	 */
	@Override
	protected void validate(final StravaLap object) {
		StravaLapTest.validateLap(object);
	}

	/**
	 * @see test.api.service.StravaTest#validate(java.lang.Object, java.lang.Object, javastrava.api.v3.model.reference.StravaResourceState)
	 */
	@Override
	protected void validate(final StravaLap object, final Integer id, final StravaResourceState state) {
		StravaLapTest.validateLap(object, id, state);
	}

	/**
	 * @see test.api.service.standardtests.spec.StandardTests#getValidId()
	 */
	@Override
	public Integer getValidId() {
		return TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER;
	}

}
