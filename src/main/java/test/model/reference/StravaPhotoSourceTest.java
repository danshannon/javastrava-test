package test.model.reference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import javastrava.model.reference.StravaPhotoSource;

/**
 * @author Dan Shannon
 *
 */
public class StravaPhotoSourceTest {
	/**
	 * Test returning the description
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetDescription() {
		for (final StravaPhotoSource type : StravaPhotoSource.values()) {
			assertNotNull(type.getDescription());
		}
	}

	/**
	 * Test returning the id
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetId() {
		for (final StravaPhotoSource type : StravaPhotoSource.values()) {
			assertNotNull(type.getId());
			assertEquals(type, StravaPhotoSource.create(type.getId()));
		}
	}

}
