package test.api.model.reference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import javastrava.api.v3.model.reference.StravaGender;

/**
 * @author Dan Shannon
 *
 */
public class StravaGenderTest {
	/**
	 * Test returning the description
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetDescription() {
		for (final StravaGender type : StravaGender.values()) {
			assertNotNull(type.getDescription());
		}
	}

	/**
	 * Test returning the id
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetId() {
		for (final StravaGender type : StravaGender.values()) {
			assertNotNull(type.getId());
			assertEquals(type, StravaGender.create(type.getId()));
		}
	}

}
