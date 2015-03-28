package test.api.model.reference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import javastrava.api.v3.model.reference.StravaActivityZoneType;

import org.junit.Test;

/**
 * @author Dan Shannon
 *
 */
public class StravaActivityZoneTypeTest {

	@Test
	public void testGetDescription() {
		for (final StravaActivityZoneType type : StravaActivityZoneType.values()) {
			assertNotNull(type.getDescription());
		}
	}

	@Test
	public void testGetId() {
		for (final StravaActivityZoneType type : StravaActivityZoneType.values()) {
			assertNotNull(type.getId());
			assertEquals(type, StravaActivityZoneType.create(type.getId()));
		}
	}

}
