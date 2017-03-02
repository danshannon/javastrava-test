package test.api.rest.athlete;

import static org.junit.Assert.fail;

import java.util.Arrays;

import org.junit.Test;

import javastrava.api.v3.model.StravaSegmentEffort;
import javastrava.api.v3.service.exception.UnauthorizedException;
import test.api.model.StravaSegmentEffortTest;
import test.api.rest.APIPagingListTest;
import test.api.rest.TestListArrayCallback;
import test.api.rest.util.ArrayCallback;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class ListAthleteKOMsTest extends APIPagingListTest<StravaSegmentEffort, Integer> {
	/**
	 * @see test.api.rest.APIPagingListTest#pagingCallback()
	 */
	@Override
	protected ArrayCallback<StravaSegmentEffort> pagingCallback() {
		return paging -> api().listAthleteKOMs(validId(), paging.getPage(), paging.getPageSize());
	}

	/**
	 * @see test.api.rest.APIListTest#listCallback()
	 */
	@Override
	protected TestListArrayCallback<StravaSegmentEffort, Integer> listCallback() {
		return (api, id) -> api.listAthleteKOMs(id, null, null);
	}

	/**
	 * @see test.api.rest.APIListTest#invalidId()
	 */
	@Override
	protected Integer invalidId() {
		return TestUtils.ATHLETE_INVALID_ID;
	}

	/**
	 * @see test.api.rest.APIListTest#privateId()
	 */
	@Override
	protected Integer privateId() {
		return null;
	}

	/**
	 * @see test.api.rest.APIListTest#privateIdBelongsToOtherUser()
	 */
	@Override
	protected Integer privateIdBelongsToOtherUser() {
		return null;
	}

	@Test
	public void testListAthleteKOMs_authenticatedAthletePrivateActivitiesWithoutViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentEffort[] koms = api().listAthleteKOMs(validId(), null, null);
			for (final StravaSegmentEffort kom : koms) {
				try {
					api().getActivity(kom.getActivity().getId(), null);
				} catch (final UnauthorizedException e) {
					fail("Returned KOM for a private segment!");
				}
			}
		});
	}

	@Test
	public void testListAthleteKOMs_authenticatedAthletePrivateActivitiesWithViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentEffort[] koms = apiWithViewPrivate().listAthleteKOMs(validId(), null, null);
			for (final StravaSegmentEffort kom : koms) {
				try {
					api().getActivity(kom.getActivity().getId(), null);
				} catch (final UnauthorizedException e) {
					fail("Returned KOM for a private activity!");
				}
			}
		});
	}

	@Test
	public void testListAthleteKOMs_authenticatedAthletePrivateSegmentsWithoutViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentEffort[] koms = api().listAthleteKOMs(validId(), null, null);
			for (final StravaSegmentEffort kom : koms) {
				try {
					api().getSegment(kom.getSegment().getId());
				} catch (final UnauthorizedException e) {
					fail("Returned KOM for a private segment!");
				}
			}
		});
	}

	@Override
	protected void validate(final StravaSegmentEffort effort) {
		StravaSegmentEffortTest.validateSegmentEffort(effort);
	}

	/**
	 * @see test.api.rest.APIListTest#validateArray(java.lang.Object[])
	 */
	@Override
	protected void validateArray(final StravaSegmentEffort[] list) {
		StravaSegmentEffortTest.validateList(Arrays.asList(list));

	}

	/**
	 * @see test.api.rest.APIListTest#validId()
	 */
	@Override
	protected Integer validId() {
		return TestUtils.ATHLETE_AUTHENTICATED_ID;
	}

	/**
	 * @see test.api.rest.APIListTest#validIdBelongsToOtherUser()
	 */
	@Override
	protected Integer validIdBelongsToOtherUser() {
		return TestUtils.ATHLETE_VALID_ID;
	}

	/**
	 * @see test.api.rest.APIListTest#validIdNoChildren()
	 */
	@Override
	protected Integer validIdNoChildren() {
		return TestUtils.ATHLETE_WITHOUT_FRIENDS;
	}

}
