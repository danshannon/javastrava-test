package test.api.rest.challenge;

import javastrava.api.v3.model.StravaChallenge;
import javastrava.api.v3.rest.API;
import javastrava.config.JavastravaApplicationConfig;
import test.api.rest.APIGetTest;
import test.api.rest.callback.TestGetCallback;

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
	protected TestGetCallback<StravaChallenge, Integer> getter() {
		return (api, id) -> {
			return api.getChallenge(id);
		};
	}

	@Override
	protected Integer invalidId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Integer privateId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Integer privateIdBelongsToOtherUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Integer validId() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	protected Integer validIdBelongsToOtherUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void validate(StravaChallenge result) throws Exception {
		// TODO Auto-generated method stub

	}

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

}
