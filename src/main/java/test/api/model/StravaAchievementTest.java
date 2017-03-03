package test.api.model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javastrava.api.v3.model.StravaAchievement;
import test.utils.BeanTest;

/**
 * <p>
 * Tests for {@link StravaAchievement}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class StravaAchievementTest extends BeanTest<StravaAchievement> {
	/**
	 * Validate an achievement matches the expected structure
	 * 
	 * @param achievement
	 *            The achievement to validate
	 */
	@SuppressWarnings("boxing")
	public static void validate(final StravaAchievement achievement) {
		assertNotNull(achievement.getTypeId());
		assertNotNull(achievement.getType());
		assertNotNull(achievement.getRank());
		assertTrue(achievement.getRank() > 0);
	}

	@Override
	protected Class<StravaAchievement> getClassUnderTest() {
		return StravaAchievement.class;
	}

}
