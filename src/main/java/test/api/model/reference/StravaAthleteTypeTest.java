/**
 *
 */
package test.api.model.reference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import javastrava.api.v3.model.reference.StravaAthleteType;

import org.junit.Test;

/**
 * @author Dan Shannon
 *
 */
public class StravaAthleteTypeTest {
	@Test
	public void testGetDescription() {
		for (final StravaAthleteType type : StravaAthleteType.values()) {
			assertNotNull(type.getDescription());
		}
	}

	@Test
	public void testGetId() {
		for (final StravaAthleteType type : StravaAthleteType.values()) {
			assertNotNull(type.getId());
			assertEquals(type, StravaAthleteType.create(type.getId()));
		}
	}

}
