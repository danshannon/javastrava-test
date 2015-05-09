/**
 *
 */
package test.api.model.reference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import javastrava.api.v3.model.webhook.reference.StravaSubscriptionObjectType;

import org.junit.Test;

/**
 * @author danshannon
 *
 */
public class StravaSubscriptionObjectTypeTest {
	@Test
	public void testGetDescription() {
		for (final StravaSubscriptionObjectType type : StravaSubscriptionObjectType.values()) {
			assertNotNull(type.getDescription());
		}
	}

	@Test
	public void testGetId() {
		for (final StravaSubscriptionObjectType type : StravaSubscriptionObjectType.values()) {
			assertNotNull(type.getId());
			assertEquals(type, StravaSubscriptionObjectType.create(type.getId()));
		}
	}

}
