package test.api.model.reference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import javastrava.api.v3.model.reference.StravaClubType;

/**
 * @author Dan Shannon
 *
 */
public class StravaClubTypeTest {
	/**
	 * Test returning the description
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetDescription() {
		for (final StravaClubType type : StravaClubType.values()) {
			assertNotNull(type.getDescription());
		}
	}

	/**
	 * Test returning the id
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetId() {
		for (final StravaClubType type : StravaClubType.values()) {
			assertNotNull(type.getId());
			assertEquals(type, StravaClubType.create(type.getId()));
		}
	}

}
