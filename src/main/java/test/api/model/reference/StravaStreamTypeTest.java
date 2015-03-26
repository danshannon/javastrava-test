package test.api.model.reference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import javastrava.api.v3.model.reference.StravaStreamType;

import org.junit.Test;

/**
 * @author dshannon
 *
 */
public class StravaStreamTypeTest {
	@Test
	public void testGetDescription() {
		for (final StravaStreamType type : StravaStreamType.values()) {
			assertNotNull(type.getDescription());
		}
	}

	@Test
	public void testGetId() {
		for (final StravaStreamType type : StravaStreamType.values()) {
			assertNotNull(type.getId());
			assertEquals(type, StravaStreamType.create(type.getId()));
		}
	}

}
