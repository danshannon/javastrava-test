package test.api.model.reference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import javastrava.api.v3.model.reference.StravaClubType;

import org.junit.Test;

/**
 * @author dshannon
 *
 */
public class StravaClubTypeTest {
	@Test
	public void testGetDescription() {
		for (final StravaClubType type : StravaClubType.values()) {
			assertNotNull(type.getDescription());
		}
	}

	@Test
	public void testGetId() {
		for (final StravaClubType type : StravaClubType.values()) {
			assertNotNull(type.getId());
			assertEquals(type, StravaClubType.create(type.getId()));
		}
	}

}
