package test.api.rest.challenge.async;

import javastrava.api.v3.model.StravaChallenge;
import javastrava.api.v3.rest.API;
import test.api.rest.callback.APIGetCallback;
import test.api.rest.challenge.JoinChallengeTest;

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
