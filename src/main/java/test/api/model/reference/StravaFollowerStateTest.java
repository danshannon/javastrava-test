package test.api.model.reference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import javastrava.api.v3.model.reference.StravaFollowerState;

import org.junit.Test;

/**
 * @author Dan Shannon
 *
 */
public class StravaFollowerStateTest {
	@Test
	public void testGetDescription() {
		for (final StravaFollowerState type : StravaFollowerState.values()) {
			assertNotNull(type.getDescription());
		}
	}

	@Test
	public void testGetId() {
		for (final StravaFollowerState type : StravaFollowerState.values()) {
			assertNotNull(type.getId());
			assertEquals(type, StravaFollowerState.create(type.getId()));
		}
	}

}
