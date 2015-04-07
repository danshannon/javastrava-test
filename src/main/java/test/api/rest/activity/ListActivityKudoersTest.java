package test.api.rest.activity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.api.v3.service.exception.UnauthorizedException;

import org.junit.Test;

import test.api.model.StravaAthleteTest;
import test.api.rest.util.ArrayCallback;
import test.api.rest.util.PagingArrayMethodTest;
import test.issues.strava.Issue95;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class ListActivityKudoersTest extends PagingArrayMethodTest<StravaAthlete, Integer> {
	@Override
	protected ArrayCallback<StravaAthlete> callback() {
		return (paging -> api().listActivityKudoers(TestUtils.ACTIVITY_WITH_KUDOS, paging.getPage(), paging.getPageSize()));
	}

	/**
	 * <p>
	 * List {@link StravaAthlete athletes} giving kudos for an {@link StravaActivity} which has >0 kudos
	 * </p>
	 *
	 * @throws Exception
	 *
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testListActivityKudoers_hasKudoers() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaAthlete[] kudoers = api().listActivityKudoers(TestUtils.ACTIVITY_WITH_KUDOS, null, null);

			assertNotNull("Returned null kudos array for activity with kudos", kudoers);
			assertNotEquals("Returned empty kudos array for activity with kudos", 0, kudoers.length);
			for (final StravaAthlete athlete : kudoers) {
				StravaAthleteTest.validateAthlete(athlete);
			}
		});
	}

	/**
	 * <p>
	 * List {@link StravaAthlete athletes} giving kudos for an {@link StravaActivity} which has NO kudos
	 * </p>
	 *
	 * <p>
	 * Should return an empty array of {@link StravaAthlete athletes}
	 * </p>
	 *
	 * @throws Exception
	 *
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 * @throws NotFoundException
	 */
	@Test
	public void testListActivityKudoers_hasNoKudoers() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaAthlete[] kudoers = api().listActivityKudoers(TestUtils.ACTIVITY_WITHOUT_KUDOS, null, null);

			assertNotNull("Returned null kudos array for activity without kudos", kudoers);
			assertEquals("Did not return empty kudos array for activity with no kudos", 0, kudoers.length);
		});
	}

	/**
	 * <p>
	 * Attempt to list {@link StravaAthlete athletes} giving kudos for an {@link StravaActivity} which does not exist
	 * </p>
	 *
	 * <p>
	 * Should return <code>null</code>
	 * </p>
	 *
	 * @throws Exception
	 *
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testListActivityKudoers_invalidActivity() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().listActivityKudoers(TestUtils.ACTIVITY_INVALID, null, null);
			} catch (final NotFoundException e) {
				// Expected
				return;
			}
			fail("Returned a non-null array of kudoers for an invalid activity");
		});
	}

	@Test
	public void testListActivityKudoers_privateActivity() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().listActivityKudoers(TestUtils.ACTIVITY_PRIVATE_OTHER_USER, null, null);
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}
			fail("Returned kudoers for a private activity belonging to other user");
		});
	}

	@Test
	public void testListActivityKudoers_privateActivityWithoutViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			if (new Issue95().isIssue()) {
				return;
			}
			try {
				api().listActivityKudoers(TestUtils.ACTIVITY_PRIVATE_WITH_KUDOS, null, null);
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}
			fail("Returned kudoers for a private activity, but activity is private and token does not have view_private scope");
		});
	}

	@Test
	public void testListActivityKudoers_privateActivityWithViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaAthlete[] kudoers = apiWithViewPrivate().listActivityKudoers(TestUtils.ACTIVITY_PRIVATE_WITH_KUDOS, null, null);
			assertNotNull(kudoers);
			assertNotEquals(0, kudoers.length);
		});
	}

	@Override
	protected void validate(final StravaAthlete athlete) {
		validate(athlete, athlete.getId(), athlete.getResourceState());

	}

	@Override
	protected void validate(final StravaAthlete athlete, final Integer id, final StravaResourceState state) {
		StravaAthleteTest.validateAthlete(athlete, id, state);

	}
}
