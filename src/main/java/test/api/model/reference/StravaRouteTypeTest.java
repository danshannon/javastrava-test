package test.api.model.reference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import javastrava.api.v3.model.reference.StravaRouteType;

/**
 * <p>
 * tests for {@link StravaRouteType}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class StravaRouteTypeTest {
	/**
	 * Test retrieval of description
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetDescription() {
		for (final StravaRouteType type : StravaRouteType.values()) {
			assertNotNull(type.getDescription());
		}
	}

	/**
	 * Test retrieval of id works as expected
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetId() {
		for (final StravaRouteType type : StravaRouteType.values()) {
			assertNotNull(type.getId());
			assertEquals(type, StravaRouteType.create(type.getId()));
		}
	}

}
