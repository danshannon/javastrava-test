package test.api.rest.challenge;

import javastrava.api.v3.model.StravaChallenge;
import javastrava.api.v3.rest.API;
import javastrava.config.JavastravaApplicationConfig;
import test.api.rest.APIGetTest;
import test.api.rest.callback.APIGetCallback;
import test.service.standardtests.data.ChallengeDataUtils;

/**
 * <p>
 * Specific tests for {@link API} get challenge methods
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class GetChallengeTest extends APIGetTest<StravaChallenge, Integer> {

	@Override
	public void get_invalid() throws Exception {
		// Can't run the test if we don't have permission to use the challenges endpoint
		if (JavastravaApplicationConfig.STRAVA_ALLOWS_CHALLENGES_ENDPOINT) {
			super.get_invalid();
		}
	}

	@Override
	public void get_private() throws Exception {
		// Can't run the test if we don't have permission to use the challenges endpoint
		if (JavastravaApplicationConfig.STRAVA_ALLOWS_CHALLENGES_ENDPOINT) {
			super.get_private();
		}
	}

	@Override
	public void get_privateBelongsToOtherUser() throws Exception {
		// Can't run the test if we don't have permission to use the challenges endpoint
		if (JavastravaApplicationConfig.STRAVA_ALLOWS_CHALLENGES_ENDPOINT) {
			super.get_privateBelongsToOtherUser();
		}
	}

	@Override
	public void get_privateWithoutViewPrivate() throws Exception {
		// Can't run the test if we don't have permission to use the challenges endpoint
		if (JavastravaApplicationConfig.STRAVA_ALLOWS_CHALLENGES_ENDPOINT) {
			super.get_privateWithoutViewPrivate();
		}
	}

	@Override
	public void get_valid() throws Exception {
		// Can't run the test if we don't have permission to use the challenges endpoint
		if (JavastravaApplicationConfig.STRAVA_ALLOWS_CHALLENGES_ENDPOINT) {
			super.get_valid();
		}
	}

	@Override
	public void get_validBelongsToOtherUser() throws Exception {
		// Can't run the test if we don't have permission to use the challenges endpoint
		if (JavastravaApplicationConfig.STRAVA_ALLOWS_CHALLENGES_ENDPOINT) {
			super.get_validBelongsToOtherUser();
		}
	}

	@Override
	protected APIGetCallback<StravaChallenge, Integer> getter() {
		return ChallengeDataUtils.apiGetter();
	}

	@Override
	protected Integer invalidId() {
		return ChallengeDataUtils.CHALLENGE_INVALID_ID;
	}

	@Override
	protected Integer privateId() {
		return null;
	}

	@Override
	protected Integer privateIdBelongsToOtherUser() {
		return null;
	}

	@Override
	protected void validate(StravaChallenge result) throws Exception {
		ChallengeDataUtils.validate(result);

	}

	@Override
	protected Integer validId() {
		return ChallengeDataUtils.CHALLENGE_VALID_ID;
	}

	@Override
	protected Integer validIdBelongsToOtherUser() {
		return null;
	}

}
