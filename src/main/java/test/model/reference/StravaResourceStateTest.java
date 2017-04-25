package test.model.reference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import javastrava.model.reference.StravaResourceState;

/**
 * @author Dan Shannon
 *
 */
public class StravaResourceStateTest {
	/**
	 * Test returning the description
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetDescription() {
		for (final StravaResourceState type : StravaResourceState.values()) {
			assertNotNull(type.getDescription());
		}
	}

	/**
	 * Test returning the id
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetId() {
		for (final StravaResourceState type : StravaResourceState.values()) {
			assertNotNull(type.getId());
			assertEquals(type, StravaResourceState.create(type.getId()));
		}
	}

}
