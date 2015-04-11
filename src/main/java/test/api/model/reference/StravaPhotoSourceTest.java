package test.api.model.reference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import javastrava.api.v3.model.reference.StravaPhotoSource;

import org.junit.Test;

/**
 * @author Dan Shannon
 *
 */
public class StravaPhotoSourceTest {
	@Test
	public void testGetDescription() {
		for (final StravaPhotoSource type : StravaPhotoSource.values()) {
			assertNotNull(type.getDescription());
		}
	}

	@Test
	public void testGetId() {
		for (final StravaPhotoSource type : StravaPhotoSource.values()) {
			assertNotNull(type.getId());
			assertEquals(type, StravaPhotoSource.create(type.getId()));
		}
	}

}
