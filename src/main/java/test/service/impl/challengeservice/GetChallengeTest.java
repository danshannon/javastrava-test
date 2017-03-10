package test.service.impl.challengeservice;

import javastrava.api.v3.model.StravaChallenge;
import javastrava.api.v3.service.Strava;
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
	protected Integer getIdValid() {
		return ChallengeDataUtils.CHALLENGE_VALID_ID;
	}

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
	protected GetCallback<StravaChallenge, Integer> getter() throws Exception {
		return ChallengeDataUtils.getter();
	}

	@Override
	protected void validate(StravaChallenge object) {
		ChallengeDataUtils.validate(object);

	}

}
