package test.model.reference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import javastrava.model.reference.StravaStreamResolutionType;

/**
 * @author Dan Shannon
 *
 */
public class StravaStreamResolutionTypeTest {
	/**
	 * Test returning the description
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetDescription() {
		for (final StravaStreamResolutionType type : StravaStreamResolutionType.values()) {
			assertNotNull(type.getDescription());
		}
	}

	/**
	 * Test returning the id
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetId() {
		for (final StravaStreamResolutionType type : StravaStreamResolutionType.values()) {
			assertNotNull(type.getId());
			assertEquals(type, StravaStreamResolutionType.create(type.getId()));
		}
	}

}
