package test.api.rest.challenge;

import static org.junit.Assert.fail;

import org.junit.Test;

import javastrava.api.v3.model.StravaChallenge;
import javastrava.api.v3.rest.API;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.api.v3.service.exception.UnauthorizedException;
import javastrava.config.JavastravaApplicationConfig;
import test.api.rest.APITest;
import test.api.rest.callback.APIGetCallback;
import test.service.standardtests.data.ChallengeDataUtils;

/**
 * <p>
 * Specific tests and configuration for {@link API#joinChallenge(Integer)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class JoinChallengeTest extends APITest<StravaChallenge> {

	@Override
	protected void validate(StravaChallenge result) throws Exception {
		ChallengeDataUtils.validate(result);
	}

	/**
	 * Callback used to join the challenge
	 *
	 * @return The callback
	 */
	@SuppressWarnings("static-method")
	protected APIGetCallback<StravaChallenge, Integer> callback() {
		return (api, id) -> {
			api.joinChallenge(id);
			return null;
		};
	}

	/**
	 * Test that you can successfully join a valid challenge using a token with write access
	 *
	 * @throws Exception
	 *             if the test fails for an unexpected reason
	 */
	@Test
	public void testJoinChallenge_validChallenge() throws Exception {
		// Can't run the test unless there's permission at the Strava end to use the challenges endpoint
		if (JavastravaApplicationConfig.STRAVA_ALLOWS_CHALLENGES_ENDPOINT) {

			// Leave the challenge first, to be sure
			final Integer id = ChallengeDataUtils.CHALLENGE_VALID_ID;
			final API api = apiWithWriteAccess();
			api.leaveChallenge(id);

			// Then join it
			callback().get(api, id);

			// If it works, it works.
		}
	}

	/**
	 * Test that you can't successfully join a valid challenge using a token without write access
	 *
	 * @throws Exception
	 *             if the test fails for an unexpected reason
	 */
	@Test
	public void testJoinChallenge_validChallengeNoWriteAccess() throws Exception {
		// Can't run the test unless there's permission at the Strava end to use the challenges endpoint
		if (JavastravaApplicationConfig.STRAVA_ALLOWS_CHALLENGES_ENDPOINT) {

			// Leave the challenge first, to be sure
			final Integer id = ChallengeDataUtils.CHALLENGE_VALID_ID;
			apiWithWriteAccess().leaveChallenge(id);

			// Then try to join it
			try {
				callback().get(api(), id);
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}

			// If it works, it fails
			fail("Succeeded in joining a challenge using a token that doesn't have WRITE scope"); //$NON-NLS-1$
		}
	}

	/**
	 * Test that you can't join a challenge that doesn't exist
	 *
	 * @throws Exception
	 *             if the test fails for an unexpected reason
	 */
	@Test
	public void testJoinChallenge_invalidChallenge() throws Exception {
		// Can't run the test unless there's permission at the Strava end to use the challenges endpoint
		if (JavastravaApplicationConfig.STRAVA_ALLOWS_CHALLENGES_ENDPOINT) {
			// Try to join it
			final Integer id = ChallengeDataUtils.CHALLENGE_INVALID_ID;
			try {
				callback().get(apiWithWriteAccess(), id);
			} catch (final NotFoundException e) {
				// Expected
				return;
			}

			// If it works, it fails
			fail("Succeeded in joining a non-existent challenge"); //$NON-NLS-1$
		}
	}

}
