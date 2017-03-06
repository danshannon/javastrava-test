/**
 *
 */
package test.api.model.reference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import javastrava.api.v3.model.reference.StravaAthleteType;

/**
 * @author Dan Shannon
 *
 */
public class StravaAthleteTypeTest {
	/**
	 * Test returning the description
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetDescription() {
		for (final StravaAthleteType type : StravaAthleteType.values()) {
			assertNotNull(type.getDescription());
		}
	}

	/**
	 * Test returning the id
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetId() {
		for (final StravaAthleteType type : StravaAthleteType.values()) {
			assertNotNull(type.getId());
			assertEquals(type, StravaAthleteType.create(type.getId()));
		}
	}

}
