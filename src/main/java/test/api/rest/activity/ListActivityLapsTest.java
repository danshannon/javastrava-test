package test.api.rest.activity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;

import org.junit.Test;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaLap;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.service.exception.UnauthorizedException;
import test.api.model.StravaLapTest;
import test.api.rest.APIListTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class ListActivityLapsTest extends APIListTest<StravaLap, Integer> {
	/**
	 *
	 */
	public ListActivityLapsTest() {
		this.listCallback = (api, id) -> api.listActivityLaps(id);
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
		return TestUtils.ACTIVITY_PRIVATE_WITH_LAPS;
	}

	/**
	 * @see test.api.rest.APIListTest#privateIdBelongsToOtherUser()
	 */
	@Override
	protected Integer privateIdBelongsToOtherUser() {
		return TestUtils.ACTIVITY_PRIVATE_OTHER_USER;
	}

	/**
	 * <p>
	 * Attempt to list the {@link StravaLap laps} in an {@link StravaActivity}
	 * which has NO laps
	 * </p>
	 *
	 * <p>
	 * Should return an empty array of {@link StravaLap}
	 * </p>
	 *
	 * @throws Exception
	 *
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testListActivityLaps_hasNoLaps() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaLap[] laps = api().listActivityLaps(TestUtils.ACTIVITY_WITHOUT_LAPS);

			assertNotNull("Laps not returned for an activity which should have them", laps);
			assertNotEquals("No laps returned for an activity which should have them", 0, laps.length);
			for (final StravaLap lap : laps) {
				if (lap.getResourceState() != StravaResourceState.META) {
					assertEquals(TestUtils.ACTIVITY_WITHOUT_LAPS, lap.getActivity().getId());
				}
				StravaLapTest.validateLap(lap, lap.getId(), lap.getResourceState());
			}
		} );
	}

	/**
	 * @see test.api.rest.APIListTest#validate(java.lang.Object)
	 */
	@Override
	protected void validate(final StravaLap lap) {
		StravaLapTest.validateLap(lap);

	}

	/**
	 * @see test.api.rest.APIListTest#validateList(java.lang.Object[])
	 */
	@Override
	protected void validateList(final StravaLap[] list) {
		StravaLapTest.validateList(Arrays.asList(list));

	}

	/**
	 * @see test.api.rest.APIListTest#validId()
	 */
	@Override
	protected Integer validId() {
		return TestUtils.ACTIVITY_WITH_LAPS;
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
		return TestUtils.ACTIVITY_WITHOUT_LAPS;
	}

}
