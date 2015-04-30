package test.api.rest.athlete;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;

import org.junit.Test;

import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.model.reference.StravaResourceState;
import test.api.model.StravaAthleteTest;
import test.api.rest.APIListTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class ListAuthenticatedAthleteFriendsTest extends APIListTest<StravaAthlete, Integer> {
	/**
	 *
	 */
	public ListAuthenticatedAthleteFriendsTest() {
		this.listCallback = (api, id) -> api.listAuthenticatedAthleteFriends(null, null);
		this.pagingCallback = (paging) -> api().listAuthenticatedAthleteFriends(paging.getPage(), paging.getPageSize());
	}

	/**
	 * @see test.api.rest.APIListTest#invalidId()
	 */
	@Override
	protected Integer invalidId() {
		return null;
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
	public void testListAuthenticatedAthleteFriends_friends() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaAthlete[] friends = api().listAuthenticatedAthleteFriends(null, null);
			assertNotNull(friends);
			assertFalse(friends.length == 0);
			for (final StravaAthlete athlete : friends) {
				StravaAthleteTest.validateAthlete(athlete, athlete.getId(), StravaResourceState.SUMMARY);
			}
		} );
	}

	/**
	 * @see test.api.rest.APITest#validate(java.lang.Object)
	 */
	@Override
	protected void validate(final StravaAthlete athlete) throws Exception {
		StravaAthleteTest.validateAthlete(athlete);
	}

	/**
	 * @see test.api.rest.APIListTest#validateArray(java.lang.Object[])
	 */
	@Override
	protected void validateArray(final StravaAthlete[] list) {
		StravaAthleteTest.validateList(Arrays.asList(list));
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
		return null;
	}

	/**
	 * @see test.api.rest.APIListTest#validIdNoChildren()
	 */
	@Override
	protected Integer validIdNoChildren() {
		return null;
	}

}
