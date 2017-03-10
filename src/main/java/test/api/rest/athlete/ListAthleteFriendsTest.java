package test.api.rest.athlete;

import static org.junit.Assert.fail;

import org.junit.Test;

import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.rest.API;
import javastrava.api.v3.service.exception.UnauthorizedException;
import test.api.model.StravaAthleteTest;
import test.api.rest.APIPagingListTest;
import test.api.rest.callback.APIListCallback;
import test.api.rest.util.ArrayCallback;
import test.issues.strava.Issue83;
import test.service.standardtests.data.AthleteDataUtils;
import test.utils.RateLimitedTestRunner;

/**
 * <p>
 * Specific tests for {@link API#listAthleteFriends(Integer, Integer, Integer)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListAthleteFriendsTest extends APIPagingListTest<StravaAthlete, Integer> {
	@Override
	protected ArrayCallback<StravaAthlete> pagingCallback() {
		return paging -> api().listAthleteFriends(validId(), paging.getPage(), paging.getPageSize());
	}

	@Override
	protected APIListCallback<StravaAthlete, Integer> listCallback() {
		return (api, id) -> api.listAthleteFriends(id, null, null);
	}

	@Override
	protected Integer invalidId() {
		return AthleteDataUtils.ATHLETE_INVALID_ID;
	}

	@Override
	protected Integer privateId() {
		return null;
	}

	@Override
	protected Integer privateIdBelongsToOtherUser() {
		return null;
	}

	/**
	 * <p>
	 * List friends of a private athlete - should throw an {@link UnauthorizedException}
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testListAthleteFriends_privateAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final Issue83 issue83 = new Issue83();
			if (issue83.isIssue()) {
				return;
			}

			try {
				api().listAthleteFriends(AthleteDataUtils.ATHLETE_PRIVATE_ID, null, null);
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}
			fail("Listed friends despite athlete being flagged as private"); //$NON-NLS-1$
		});
	}

	@Override
	protected void validate(final StravaAthlete athlete) {
		StravaAthleteTest.validateAthlete(athlete);

	}

	@Override
	protected void validateArray(final StravaAthlete[] athletes) {
		for (final StravaAthlete athlete : athletes) {
			StravaAthleteTest.validateAthlete(athlete);
		}
	}

	@Override
	protected Integer validId() {
		return AthleteDataUtils.ATHLETE_AUTHENTICATED_ID;
	}

	@Override
	protected Integer validIdBelongsToOtherUser() {
		return AthleteDataUtils.ATHLETE_VALID_ID;
	}

	@Override
	protected Integer validIdNoChildren() {
		return AthleteDataUtils.ATHLETE_WITHOUT_FRIENDS;
	}

}
