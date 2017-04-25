package test.service.standardtests.data;

import javastrava.model.StravaChallenge;
import test.api.callback.APIGetCallback;
import test.service.standardtests.callbacks.GetCallback;
import test.utils.TestUtils;

/**
 * <p>
 * Test data utilities for challenge data
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ChallengeDataUtils {
	/**
	 * Valid identifier for a challenge
	 */
	public static Integer CHALLENGE_VALID_ID;

	/**
	 * Invalid identifier for a challenge
	 */
	public static Integer CHALLENGE_INVALID_ID;

	static {
		CHALLENGE_VALID_ID = TestUtils.integerProperty("test.challengeId"); //$NON-NLS-1$
		CHALLENGE_INVALID_ID = TestUtils.integerProperty("test.challengeInvalidId"); //$NON-NLS-1$
	}

	/**
	 * <p>
	 * Callback for getting challenges from the Strava API directly
	 * </p>
	 *
	 * @return The callback
	 */
	public static APIGetCallback<StravaChallenge, Integer> apiGetter() {
		return (api, id) -> api.getChallenge(id);
	}

	/**
	 * <p>
	 * Callback for ansynchronously getting challenges from the Strava API directly
	 * </p>
	 *
	 * @return The callback
	 */
	public static APIGetCallback<StravaChallenge, Integer> apiGetterAsync() {
		return (api, id) -> api.getChallengeAsync(id).get();
	}

	/**
	 * <p>
	 * Callback for getting challenges from the Strava service
	 * </p>
	 *
	 * @return The callback
	 */
	public static GetCallback<StravaChallenge, Integer> getter() {
		return (strava, id) -> strava.getChallenge(id);
	}

	/**
	 * Validate an achievement matches the expected structure
	 *
	 * @param achievement
	 *            The achievement to validate
	 */
	public static void validate(final StravaChallenge achievement) {
		// TODO
	}
}
