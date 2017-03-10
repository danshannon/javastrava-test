package test.api.model;

import javastrava.api.v3.model.StravaChallenge;
import test.utils.BeanTest;

/**
 * <p>
 * Tests for {@link StravaChallenge}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class StravaChallengeTest extends BeanTest<StravaChallenge> {
	@Override
	protected Class<StravaChallenge> getClassUnderTest() {
		return StravaChallenge.class;
	}

}
