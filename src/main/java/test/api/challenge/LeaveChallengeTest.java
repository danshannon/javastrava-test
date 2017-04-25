package test.api.challenge;

import static org.junit.Assert.fail;

import org.junit.Test;

import javastrava.api.API;
import javastrava.config.JavastravaApplicationConfig;
import javastrava.model.StravaChallenge;
import javastrava.service.exception.NotFoundException;
import javastrava.service.exception.UnauthorizedException;
import test.api.APITest;
import test.api.callback.APIGetCallback;
import test.service.standardtests.data.ChallengeDataUtils;

/**
 * <p>
 * Specific tests and configuration for {@link API#joinChallenge(Integer)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class LeaveChallengeTest extends APITest<StravaChallenge> {

	/**
	 * Callback used to join the challenge
	 *
	 * @return The callback
	 */
	@SuppressWarnings("static-method")
	protected APIGetCallback<StravaChallenge, Integer> callback() {
		return (api, id) -> {
			api.leaveChallenge(id);
			return null;
		};
	}

	/**
	 * Test that you can't leave a challenge that doesn't exist
	 *
	 * @throws Exception
	 *             if the test fails for an unexpected reason
	 */
	@Test
	public void testLeaveChallenge_invalidChallenge() throws Exception {
		// Can't run the test unless there's permission at the Strava end to use the challenges endpoint
		if (JavastravaApplicationConfig.STRAVA_ALLOWS_CHALLENGES_ENDPOINT) {
			// Try to leave it
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

	/**
	 * Test that you can successfully leave a valid challenge using a token with write access
	 *
	 * @throws Exception
	 *             if the test fails for an unexpected reason
	 */
	@Test
	public void testLeaveChallenge_validChallenge() throws Exception {
		// Can't run the test unless there's permission at the Strava end to use the challenges endpoint
		if (JavastravaApplicationConfig.STRAVA_ALLOWS_CHALLENGES_ENDPOINT) {

			// Join the challenge first, to be sure
			final Integer id = ChallengeDataUtils.CHALLENGE_VALID_ID;
			final API api = apiWithWriteAccess();
			api.joinChallenge(id);

			// Then leave it
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
	public void testLeaveChallenge_validChallengeNoWriteAccess() throws Exception {
		// Can't run the test unless there's permission at the Strava end to use the challenges endpoint
		if (JavastravaApplicationConfig.STRAVA_ALLOWS_CHALLENGES_ENDPOINT) {

			// Join the challenge first, to be sure
			final Integer id = ChallengeDataUtils.CHALLENGE_VALID_ID;
			apiWithWriteAccess().joinChallenge(id);

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

	@Override
	protected void validate(StravaChallenge result) throws Exception {
		ChallengeDataUtils.validate(result);
	}

}
