/**
 *
 */
package test.api.model.reference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import javastrava.api.v3.model.webhook.reference.StravaSubscriptionAspectType;

/**
 * @author danshannon
 *
 */
public class StravaSubscriptionAspectTypeTest {
	/**
	 * Test returning the description
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetDescription() {
		for (final StravaSubscriptionAspectType type : StravaSubscriptionAspectType.values()) {
			assertNotNull(type.getDescription());
		}
	}

	/**
	 * Test returning the id
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetId() {
		for (final StravaSubscriptionAspectType type : StravaSubscriptionAspectType.values()) {
			assertNotNull(type.getId());
			assertEquals(type, StravaSubscriptionAspectType.create(type.getId()));
		}
	}

}
