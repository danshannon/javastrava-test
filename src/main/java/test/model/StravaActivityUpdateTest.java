package test.model;

import static org.junit.Assert.assertFalse;

import javastrava.model.StravaActivityUpdate;
import javastrava.model.reference.StravaActivityType;
import test.utils.BeanTest;

/**
 * @author Dan Shannon
 *
 */
public class StravaActivityUpdateTest extends BeanTest<StravaActivityUpdate> {

	/**
	 * <p>
	 * Validate the update structure and content
	 * </p>
	 *
	 * @param update
	 *            Update to be validated
	 */
	public static void validate(final StravaActivityUpdate update) {
		if (update.getType() != null) {
			assertFalse(StravaActivityType.UNKNOWN == update.getType());
		}
	}

	/**
	 * @see test.utils.BeanTest#getClassUnderTest()
	 */
	@Override
	protected Class<StravaActivityUpdate> getClassUnderTest() {
		return StravaActivityUpdate.class;
	}

}
