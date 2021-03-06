package test.api.challenge.async;

import javastrava.api.API;
import javastrava.model.StravaChallenge;
import test.api.callback.APIGetCallback;
import test.api.challenge.JoinChallengeTest;

/**
 * <p>
 * Specific tests and config for {@link API#joinChallengeAsync(Integer)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class JoinChallengeAsyncTest extends JoinChallengeTest {

	@Override
	protected APIGetCallback<StravaChallenge, Integer> callback() {
		return (api, id) -> {
			api.joinChallenge(id);
			return api.getChallengeAsync(id).get();
		};
	}

}
