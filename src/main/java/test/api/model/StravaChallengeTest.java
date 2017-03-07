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
	/**
	 * Validate an achievement matches the expected structure
	 *
	 * @param achievement
	 *            The achievement to validate
	 */
	public static void validate(final StravaChallenge achievement) {
		// TODO
	}

	@Override
	protected Class<StravaChallenge> getClassUnderTest() {
		return StravaChallenge.class;
	}

}
