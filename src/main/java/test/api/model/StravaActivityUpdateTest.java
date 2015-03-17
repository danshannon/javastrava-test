package test.api.model;

import static org.junit.Assert.assertFalse;
import javastrava.api.v3.model.StravaActivityUpdate;
import javastrava.api.v3.model.reference.StravaActivityType;
import test.utils.BeanTest;

/**
 * @author Dan Shannon
 *
 */
public class StravaActivityUpdateTest extends BeanTest<StravaActivityUpdate> {

	/**
	 * @see test.utils.BeanTest#getClassUnderTest()
	 */
	@Override
	protected Class<StravaActivityUpdate> getClassUnderTest() {
		return StravaActivityUpdate.class;
	}
	
	public static void validate(StravaActivityUpdate update) {
		if (update.getType() != null) {
			assertFalse(StravaActivityType.UNKNOWN == update.getType());
		}
	}

}
