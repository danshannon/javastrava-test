package test.api.model.reference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import javastrava.api.v3.model.reference.StravaFollowerState;

/**
 * @author Dan Shannon
 *
 */
public class StravaFollowerStateTest {
	/**
	 * Test returning the description
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetDescription() {
		for (final StravaFollowerState type : StravaFollowerState.values()) {
			assertNotNull(type.getDescription());
		}
	}

	/**
	 * Test returning the id
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetId() {
		for (final StravaFollowerState type : StravaFollowerState.values()) {
			assertNotNull(type.getId());
			assertEquals(type, StravaFollowerState.create(type.getId()));
		}
	}

}
