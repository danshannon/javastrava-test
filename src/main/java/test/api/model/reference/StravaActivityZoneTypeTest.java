package test.api.model.reference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import javastrava.api.v3.model.reference.StravaActivityZoneType;

/**
 * @author Dan Shannon
 *
 */
public class StravaActivityZoneTypeTest {

	/**
	 * Test returning the description
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetDescription() {
		for (final StravaActivityZoneType type : StravaActivityZoneType.values()) {
			assertNotNull(type.getDescription());
		}
	}

	/**
	 * Test returning the id
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetId() {
		for (final StravaActivityZoneType type : StravaActivityZoneType.values()) {
			assertNotNull(type.getId());
			assertEquals(type, StravaActivityZoneType.create(type.getId()));
		}
	}

}
