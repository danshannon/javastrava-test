package test.api.model.reference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import javastrava.api.v3.model.reference.StravaGender;

import org.junit.Test;

/**
 * @author Dan Shannon
 *
 */
public class StravaGenderTest {
	@Test
	public void testGetDescription() {
		for (final StravaGender type : StravaGender.values()) {
			assertNotNull(type.getDescription());
		}
	}

	@Test
	public void testGetId() {
		for (final StravaGender type : StravaGender.values()) {
			assertNotNull(type.getId());
			assertEquals(type, StravaGender.create(type.getId()));
		}
	}

}
