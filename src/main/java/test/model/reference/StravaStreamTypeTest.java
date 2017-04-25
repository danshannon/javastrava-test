package test.model.reference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import javastrava.model.reference.StravaStreamType;

/**
 * @author Dan Shannon
 *
 */
public class StravaStreamTypeTest {
	/**
	 * Test returning the description
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetDescription() {
		for (final StravaStreamType type : StravaStreamType.values()) {
			assertNotNull(type.getDescription());
		}
	}

	/**
	 * Test returning the id
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetId() {
		for (final StravaStreamType type : StravaStreamType.values()) {
			assertNotNull(type.getId());
			assertEquals(type, StravaStreamType.create(type.getId()));
		}
	}

}
