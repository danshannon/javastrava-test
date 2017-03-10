package test.service.impl.challengeservice;

import javastrava.api.v3.model.StravaChallenge;
import javastrava.api.v3.service.Strava;
import test.service.standardtests.ListMethodTest;
import test.service.standardtests.callbacks.ListCallback;
import test.service.standardtests.data.ChallengeDataUtils;

/**
 * <p>
 * Specific tests and configuration for {@link Strava#listJoinedChallenges()}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListJoinedChallengesTest extends ListMethodTest<StravaChallenge, Integer> {

	@Override
	protected ListCallback<StravaChallenge, Integer> lister() {
		return (strava, id) -> strava.listJoinedChallenges();
	}

	@Override
	protected Integer idPrivate() {
		return null;
	}

	@Override
	protected Integer idPrivateBelongsToOtherUser() {
		return null;
	}

	@Override
	protected Integer idValidWithEntries() {
		return ChallengeDataUtils.CHALLENGE_VALID_ID;
	}

	@Override
	protected Integer idValidWithoutEntries() {
		return null;
	}

	@Override
	protected Integer idInvalid() {
		return null;
	}

	@Override
	protected void validate(StravaChallenge object) {
		ChallengeDataUtils.validate(object);
	}

}
