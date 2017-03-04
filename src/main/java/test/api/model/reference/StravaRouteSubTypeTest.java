package test.api.model.reference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import javastrava.api.v3.model.reference.StravaRouteSubType;

/**
 * <p>
 * tests for {@link StravaRouteSubType}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class StravaRouteSubTypeTest {
	/**
	 * Test retrieval of description
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetDescription() {
		for (final StravaRouteSubType type : StravaRouteSubType.values()) {
			assertNotNull(type.getDescription());
		}
	}

	/**
	 * Test retrieval of id works as expected
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetId() {
		for (final StravaRouteSubType type : StravaRouteSubType.values()) {
			assertNotNull(type.getId());
			assertEquals(type, StravaRouteSubType.create(type.getId()));
		}
	}

}
