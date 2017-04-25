package test.model.reference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import javastrava.model.reference.StravaActivityType;

/**
 * @author Dan Shannon
 *
 */
public class StravaActivityTypeTest {
	/**
	 * Test the create method
	 */
	@Test
	@SuppressWarnings("static-method")
	public void testCreate() {
		for (final StravaActivityType type : StravaActivityType.values()) {
			assertEquals(type, StravaActivityType.create(type.getValue()));
		}

	}

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
	 * Test the description
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetValue() {
		for (final StravaActivityType type : StravaActivityType.values()) {
			assertNotNull(type.getDescription());
		}

	}

}
