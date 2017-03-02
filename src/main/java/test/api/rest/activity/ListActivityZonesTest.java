package test.api.rest.activity;

import java.util.Arrays;

import javastrava.api.v3.model.StravaActivityZone;
import test.api.model.StravaActivityZoneTest;
import test.api.rest.APIListTest;
import test.api.rest.TestListArrayCallback;
import test.utils.TestUtils;

public class ListActivityZonesTest extends APIListTest<StravaActivityZone, Long> {
	@Override
	protected TestListArrayCallback<StravaActivityZone, Long> listCallback() {
		return ((api, id) -> api.listActivityZones(id));
	}

	/**
	 *
	 */
	public ListActivityZonesTest() {
		super();
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
	 * @see test.api.rest.APITest#validate(java.lang.Object)
	 */
	@Override
	protected void validate(final StravaActivityZone zone) throws Exception {
		StravaActivityZoneTest.validate(zone);

	}

	/**
	 * @see test.api.rest.APIListTest#validateArray(java.lang.Object[])
	 */
	@Override
	protected void validateArray(final StravaActivityZone[] list) {
		StravaActivityZoneTest.validateList(Arrays.asList(list));

	}

	/**
	 * @see test.api.rest.APIListTest#validId()
	 */
	@Override
	protected Long validId() {
		return TestUtils.ACTIVITY_WITH_ZONES;
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
		return TestUtils.ACTIVITY_WITHOUT_ZONES;
	}

}
