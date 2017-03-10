package test.api.rest.challenge.async;

import javastrava.api.v3.model.StravaChallenge;
import javastrava.api.v3.rest.API;
import test.api.rest.callback.APIListCallback;
import test.api.rest.challenge.ListJoinedChallengesTest;

/**
 * <p>
 * Specific tests and configuration for {@link API#listJoinedChallengesAsync()}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListJoinedChallengesAsyncTest extends ListJoinedChallengesTest {

	@Override
	protected APIListCallback<StravaChallenge, Integer> listCallback() {
		return ((api, id) -> api.listJoinedChallengesAsync().get());
	}

}
