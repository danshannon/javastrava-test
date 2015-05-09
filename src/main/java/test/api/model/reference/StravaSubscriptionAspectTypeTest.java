/**
 *
 */
package test.api.model.reference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import javastrava.api.v3.model.webhook.reference.StravaSubscriptionAspectType;

import org.junit.Test;

/**
 * @author danshannon
 *
 */
public class StravaSubscriptionAspectTypeTest {
	@Test
	public void testGetDescription() {
		for (final StravaSubscriptionAspectType type : StravaSubscriptionAspectType.values()) {
			assertNotNull(type.getDescription());
		}
	}

	@Test
	public void testGetId() {
		for (final StravaSubscriptionAspectType type : StravaSubscriptionAspectType.values()) {
			assertNotNull(type.getId());
			assertEquals(type, StravaSubscriptionAspectType.create(type.getId()));
		}
	}

}
