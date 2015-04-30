package test.api.rest.athlete;

import static org.junit.Assert.fail;

import java.util.Arrays;

import org.junit.Test;

import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.service.exception.UnauthorizedException;
import test.api.model.StravaAthleteTest;
import test.api.rest.APIListTest;
import test.issues.strava.Issue83;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class ListAthleteFriendsTest extends APIListTest<StravaAthlete, Integer> {
	/**
	 *
	 */
	public ListAthleteFriendsTest() {
		this.listCallback = (api, id) -> api.listAthleteFriends(id, null, null);
		this.pagingCallback = (paging) -> api().listAthleteFriends(validId(), paging.getPage(), paging.getPageSize());
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
	public void testListAthleteFriends_privateAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// TODO This is a workaround for issue javastravav3api#83
			final Issue83 issue83 = new Issue83();
			if (issue83.isIssue()) {
				return;
			}
			// End of workaround

			try {
				api().listAthleteFriends(TestUtils.ATHLETE_PRIVATE_ID, null, null);
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}
			fail("Listed friends despite athlete being flagged as private");
		} );
	}

	@Override
	protected void validate(final StravaAthlete athlete) {
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
