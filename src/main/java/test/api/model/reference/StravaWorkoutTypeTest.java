package test.api.model.reference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import javastrava.api.v3.model.reference.StravaWorkoutType;

import org.junit.Test;

/**
 * @author dshannon
 *
 */
public class StravaWorkoutTypeTest {
	@Test
	public void testGetDescription() {
		for (final StravaWorkoutType type : StravaWorkoutType.values()) {
			assertNotNull(type.getDescription());
		}
	}

	@Test
	public void testGetId() {
		for (final StravaWorkoutType type : StravaWorkoutType.values()) {
			assertNotNull(type.getId());
			assertEquals(type, StravaWorkoutType.create(type.getId()));
		}
	}

}
