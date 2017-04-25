package test.api.challenge.async;

import javastrava.api.API;
import javastrava.model.StravaChallenge;
import test.api.callback.APIGetCallback;
import test.api.challenge.GetChallengeTest;
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
