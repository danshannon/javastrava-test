package test.api.model.reference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import javastrava.api.v3.model.reference.StravaAgeGroup;

/**
 * @author Dan Shannon
 *
 */
public class StravaAgeGroupTest {
	/**
	 * Test returning the description
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetDescription() {
		for (final StravaAgeGroup type : StravaAgeGroup.values()) {
			assertNotNull(type.getDescription());
		}
	}

	/**
	 * Test returning the id
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetId() {
		for (final StravaAgeGroup type : StravaAgeGroup.values()) {
			assertNotNull(type.getId());
			assertEquals(type, StravaAgeGroup.create(type.getId()));
		}
	}

}
