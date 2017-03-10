package test.api.rest.challenge.async;

import javastrava.api.v3.model.StravaChallenge;
import javastrava.api.v3.rest.API;
import test.api.rest.callback.APIGetCallback;
import test.api.rest.challenge.GetChallengeTest;
import test.service.standardtests.data.ChallengeDataUtils;

/**
 * <p>
 * Specific tests for {@link API#getChallengeAsync(Integer)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class GetChallengeAsyncTest extends GetChallengeTest {

	@Override
	protected APIGetCallback<StravaChallenge, Integer> getter() {
		return ChallengeDataUtils.apiGetterAsync();
	}

}
