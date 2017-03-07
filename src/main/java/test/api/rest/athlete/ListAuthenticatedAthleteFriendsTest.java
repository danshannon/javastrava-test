package test.api.rest.athlete;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.service.Strava;
import test.api.model.StravaAthleteTest;
import test.api.rest.APIPagingListTest;
import test.api.rest.callback.TestListArrayCallback;
import test.api.rest.util.ArrayCallback;
import test.service.standardtests.data.AthleteDataUtils;
import test.utils.RateLimitedTestRunner;

/**
 * <p>
 * Tests for {@link Strava#listAuthenticatedAthleteFriends()} methods
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListAuthenticatedAthleteFriendsTest extends APIPagingListTest<StravaAthlete, Integer> {
	/**
	 * @see test.api.rest.APIPagingListTest#pagingCallback()
	 */
	@Override
	protected ArrayCallback<StravaAthlete> pagingCallback() {
		return paging -> api().listAuthenticatedAthleteFriends(paging.getPage(), paging.getPageSize());
	}

	/**
	 * @see test.api.rest.APIListTest#listCallback()
	 */
	@Override
	protected TestListArrayCallback<StravaAthlete, Integer> listCallback() {
		return (api, id) -> api.listAuthenticatedAthleteFriends(null, null);
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

	/**
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testListAuthenticatedAthleteFriends_friends() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaAthlete[] friends = api().listAuthenticatedAthleteFriends(null, null);
			assertNotNull(friends);
			assertFalse(friends.length == 0);
			for (final StravaAthlete athlete : friends) {
				StravaAthleteTest.validateAthlete(athlete, athlete.getId(), StravaResourceState.SUMMARY);
			}
		});
	}

	/**
	 * @see test.api.rest.APITest#validate(java.lang.Object)
	 */
	@Override
	protected void validate(final StravaAthlete athlete) throws Exception {
		StravaAthleteTest.validateAthlete(athlete);
	}

	@Override
	protected void validateArray(final StravaAthlete[] athletes) {
		for (final StravaAthlete athlete : athletes) {
			StravaAthleteTest.validateAthlete(athlete);
		}
	}

	/**
	 * @see test.api.rest.APIListTest#validId()
	 */
	@Override
	protected Integer validId() {
		return AthleteDataUtils.ATHLETE_AUTHENTICATED_ID;
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
