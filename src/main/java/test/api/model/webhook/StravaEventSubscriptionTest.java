/**
 *
 */
package test.api.model.webhook;

import static org.junit.Assert.assertNotNull;
import javastrava.api.v3.model.webhook.StravaEventSubscription;
import test.utils.BeanTest;

/**
 * @author danshannon
 *
 */
public class StravaEventSubscriptionTest extends BeanTest<StravaEventSubscription> {
	public static void validate(final StravaEventSubscription sub) {
		assertNotNull(sub.getApplicationId());
		assertNotNull(sub.getId());
		assertNotNull(sub.getAspectType());
		assertNotNull(sub.getCallbackURL());
		assertNotNull(sub.getCreatedAt());
		assertNotNull(sub.getObjectType());
		assertNotNull(sub.getUpdatedAt());
	}

	/**
	 * @see test.utils.BeanTest#getClassUnderTest()
	 */
	@Override
	protected Class<StravaEventSubscription> getClassUnderTest() {
		return StravaEventSubscription.class;
	}

}
