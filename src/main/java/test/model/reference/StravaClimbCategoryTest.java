package test.model.reference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import javastrava.model.reference.StravaClimbCategory;

/**
 * @author Dan Shannon
 *
 */
public class StravaClimbCategoryTest {
	/**
	 * Test returning the description
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetDescription() {
		for (final StravaClimbCategory type : StravaClimbCategory.values()) {
			assertNotNull(type.getDescription());
		}
	}

	/**
	 * Test returning the id
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetId() {
		for (final StravaClimbCategory type : StravaClimbCategory.values()) {
			assertNotNull(type.getId());
			assertEquals(type, StravaClimbCategory.create(type.getId()));
		}
	}

}
