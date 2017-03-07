package test.api.model.reference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import javastrava.api.v3.model.reference.StravaActivityType;

/**
 * @author Dan Shannon
 *
 */
public class StravaChallengeTypeTest {
	/**
	 * Test the integrity of the id
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetId() {
		for (final StravaActivityType type : StravaActivityType.values()) {
			assertNotNull(type.getId());
			assertEquals(type, StravaActivityType.create(type.getId()));
		}
	}

	/**
	 * Test the getValue method
	 */
	@SuppressWarnings("static-method")
	public void testGetValue() {
		for (final StravaActivityType type : StravaActivityType.values()) {
			assertNotNull(type.getValue());
			assertEquals(type, StravaActivityType.create(type.getValue()));
		}
	}

	/**
	 * Test the create method
	 */
	@SuppressWarnings("static-method")
	public void testCreate() {
		for (final StravaActivityType type : StravaActivityType.values()) {
			assertEquals(type, StravaActivityType.create(type.getValue()));
		}

	}

}
