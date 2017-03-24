package test.service.impl.challengeservice;

import javastrava.api.v3.model.StravaChallenge;
import javastrava.api.v3.service.Strava;
import javastrava.config.JavastravaApplicationConfig;
import test.service.standardtests.GetMethodTest;
import test.service.standardtests.callbacks.GetCallback;
import test.service.standardtests.data.ChallengeDataUtils;

/**
 * <p>
 * Specific tests for {@link Strava#getChallenge(Integer)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class GetChallengeTest extends GetMethodTest<StravaChallenge, Integer> {

	@Override
	protected Integer getIdInvalid() {
		return ChallengeDataUtils.CHALLENGE_INVALID_ID;
	}

	@Override
	protected Integer getIdPrivate() {
		return null;
	}

	@Override
	protected Integer getIdPrivateBelongsToOtherUser() {
		return null;
	}

	@Override
	protected Integer getIdValid() {
		return ChallengeDataUtils.CHALLENGE_VALID_ID;
	}

	@Override
	protected GetCallback<StravaChallenge, Integer> getter() {
		return ChallengeDataUtils.getter();
	}

	@Override
	protected void validate(StravaChallenge object) {
		ChallengeDataUtils.validate(object);

	}

	@Override
	public void testGetInvalidId() throws Exception {
		// Can't run the test if we don't have permission to use the challenges endpoint
		if (JavastravaApplicationConfig.STRAVA_ALLOWS_CHALLENGES_ENDPOINT) {
			super.testGetInvalidId();
		}
	}

	@Override
	public void testGetNullId() throws Exception {
		// Can't run the test if we don't have permission to use the challenges endpoint
		if (JavastravaApplicationConfig.STRAVA_ALLOWS_CHALLENGES_ENDPOINT) {
			super.testGetNullId();
		}
	}

	@Override
	public void testGetValidId() throws Exception {
		// Can't run the test if we don't have permission to use the challenges endpoint
		if (JavastravaApplicationConfig.STRAVA_ALLOWS_CHALLENGES_ENDPOINT) {
			super.testGetValidId();
		}
	}

	@Override
	public void testInvalidId() throws Exception {
		// Can't run the test if we don't have permission to use the challenges endpoint
		if (JavastravaApplicationConfig.STRAVA_ALLOWS_CHALLENGES_ENDPOINT) {
			super.testInvalidId();
		}
	}

	@Override
	public void testPrivateBelongsToOtherUser() throws Exception {
		// Can't run the test if we don't have permission to use the challenges endpoint
		if (JavastravaApplicationConfig.STRAVA_ALLOWS_CHALLENGES_ENDPOINT) {
			super.testPrivateBelongsToOtherUser();
		}
	}

	@Override
	public void testPrivateWithNoViewPrivateScope() throws Exception {
		// Can't run the test if we don't have permission to use the challenges endpoint
		if (JavastravaApplicationConfig.STRAVA_ALLOWS_CHALLENGES_ENDPOINT) {
			super.testPrivateWithNoViewPrivateScope();
		}
	}

	@Override
	public void testPrivateWithViewPrivateScope() throws Exception {
		// Can't run the test if we don't have permission to use the challenges endpoint
		if (JavastravaApplicationConfig.STRAVA_ALLOWS_CHALLENGES_ENDPOINT) {
			super.testPrivateWithViewPrivateScope();
		}
	}

}
