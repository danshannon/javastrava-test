package test.service.impl.challengeservice;

import java.util.List;

import javastrava.config.JavastravaApplicationConfig;
import javastrava.model.StravaChallenge;
import javastrava.service.Strava;
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
	protected Class<StravaChallenge> classUnderTest() {
		return StravaChallenge.class;
	}

	@Override
	protected Integer idInvalid() {
		return null;
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
	protected ListCallback<StravaChallenge, Integer> lister() {
		return (strava, id) -> strava.listJoinedChallenges();
	}

	@Override
	public void testInvalidId() throws Exception {
		// Can't run the test unless there's permission at the Strava end to use the challenges endpoint
		if (JavastravaApplicationConfig.STRAVA_ALLOWS_CHALLENGES_ENDPOINT) {
			super.testInvalidId();
		}
	}

	@Override
	public void testPrivateBelongsToOtherUser() throws Exception {
		// Can't run the test unless there's permission at the Strava end to use the challenges endpoint
		if (JavastravaApplicationConfig.STRAVA_ALLOWS_CHALLENGES_ENDPOINT) {
			super.testPrivateBelongsToOtherUser();
		}
	}

	@Override
	public void testPrivateWithNoViewPrivateScope() throws Exception {
		// Can't run the test unless there's permission at the Strava end to use the challenges endpoint
		if (JavastravaApplicationConfig.STRAVA_ALLOWS_CHALLENGES_ENDPOINT) {
			super.testPrivateWithNoViewPrivateScope();
		}
	}

	@Override
	public void testPrivateWithViewPrivateScope() throws Exception {
		// Can't run the test unless there's permission at the Strava end to use the challenges endpoint
		if (JavastravaApplicationConfig.STRAVA_ALLOWS_CHALLENGES_ENDPOINT) {
			super.testPrivateWithViewPrivateScope();
		}
	}

	@Override
	public void testValidParentWithEntries() throws Exception {
		// Can't run the test unless there's permission at the Strava end to use the challenges endpoint
		if (JavastravaApplicationConfig.STRAVA_ALLOWS_CHALLENGES_ENDPOINT) {
			super.testValidParentWithEntries();
		}
	}

	@Override
	public void testValidParentWithNoEntries() throws Exception {
		// Can't run the test unless there's permission at the Strava end to use the challenges endpoint
		if (JavastravaApplicationConfig.STRAVA_ALLOWS_CHALLENGES_ENDPOINT) {
			super.testValidParentWithNoEntries();
		}
	}

	@Override
	protected void validate(StravaChallenge object) {
		ChallengeDataUtils.validate(object);
	}

	@Override
	protected void validateList(List<StravaChallenge> list) {
		// Can't run the test unless there's permission at the Strava end to use the challenges endpoint
		if (JavastravaApplicationConfig.STRAVA_ALLOWS_CHALLENGES_ENDPOINT) {
			super.validateList(list);
		}
	}

}
