/**
 *
 */
package test.model.reference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import javastrava.model.webhook.reference.StravaSubscriptionObjectType;

/**
 * @author danshannon
 *
 */
public class StravaSubscriptionObjectTypeTest {
	/**
	 * Test returning the description
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetDescription() {
		for (final StravaSubscriptionObjectType type : StravaSubscriptionObjectType.values()) {
			assertNotNull(type.getDescription());
		}
	}

	/**
	 * Test returning the id
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetId() {
		for (final StravaSubscriptionObjectType type : StravaSubscriptionObjectType.values()) {
			assertNotNull(type.getId());
			assertEquals(type, StravaSubscriptionObjectType.create(type.getId()));
		}
	}

}
