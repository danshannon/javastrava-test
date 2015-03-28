package test.api.model.reference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import javastrava.api.v3.model.reference.StravaResourceState;

import org.junit.Test;

/**
 * @author Dan Shannon
 *
 */
public class StravaResourceStateTest {
	@Test
	public void testGetDescription() {
		for (final StravaResourceState type : StravaResourceState.values()) {
			assertNotNull(type.getDescription());
		}
	}

	@Test
	public void testGetId() {
		for (final StravaResourceState type : StravaResourceState.values()) {
			assertNotNull(type.getId());
			assertEquals(type, StravaResourceState.create(type.getId()));
		}
	}

}
