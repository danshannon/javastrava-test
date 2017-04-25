package test.model.reference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import javastrava.model.reference.StravaWorkoutType;

/**
 * @author Dan Shannon
 *
 */
public class StravaWorkoutTypeTest {

	/**
	 * Test create method
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testCreate() {
		for (final StravaWorkoutType type : StravaWorkoutType.values()) {
			assertEquals(type, StravaWorkoutType.create(type.getValue()));
		}
	}

	/**
	 * Test returning the description
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetDescription() {
		for (final StravaWorkoutType type : StravaWorkoutType.values()) {
			assertNotNull(type.getDescription());
		}
	}

	/**
	 * Test returning the id
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetId() {
		for (final StravaWorkoutType type : StravaWorkoutType.values()) {
			assertNotNull(type.getId());
			assertEquals(type, StravaWorkoutType.create(type.getId()));
		}
	}

}
