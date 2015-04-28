package test.api.rest.activity;

import java.util.Arrays;

import javastrava.api.v3.model.StravaActivityZone;
import test.api.model.StravaActivityZoneTest;
import test.api.rest.APIListTest;
import test.utils.TestUtils;

public class ListActivityZonesTest extends APIListTest<StravaActivityZone, Integer> {
	/**
	 *
	 */
	public ListActivityZonesTest() {
		super();
		this.listCallback = (api, id) -> api.listActivityZones(id);
		this.pagingCallback = null;
		this.suppressPagingTests = true;
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
		return TestUtils.ACTIVITY_PRIVATE;
	}

	/**
	 * @see test.api.rest.APIListTest#privateIdBelongsToOtherUser()
	 */
	@Override
	protected Integer privateIdBelongsToOtherUser() {
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
	 * @see test.api.rest.APIListTest#validateList(java.lang.Object[])
	 */
	@Override
	protected void validateList(final StravaActivityZone[] list) {
		StravaActivityZoneTest.validateList(Arrays.asList(list));

	}

	/**
	 * @see test.api.rest.APIListTest#validId()
	 */
	@Override
	protected Integer validId() {
		return TestUtils.ACTIVITY_WITH_ZONES;
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
		return TestUtils.ACTIVITY_WITHOUT_ZONES;
	}
}
