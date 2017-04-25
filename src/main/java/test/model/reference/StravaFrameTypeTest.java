package test.model.reference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import javastrava.model.reference.StravaFrameType;

/**
 * @author Dan Shannon
 *
 */
public class StravaFrameTypeTest {
	/**
	 * Test returning the description
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetDescription() {
		for (final StravaFrameType type : StravaFrameType.values()) {
			assertNotNull(type.getDescription());
		}
	}

	/**
	 * Test returning the id
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetId() {
		for (final StravaFrameType type : StravaFrameType.values()) {
			assertNotNull(type.getId());
			assertEquals(type, StravaFrameType.create(type.getId()));
		}
	}

}
