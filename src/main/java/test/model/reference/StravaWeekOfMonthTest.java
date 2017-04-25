package test.model.reference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import javastrava.model.reference.StravaWeekOfMonth;

/**
 * @author Dan Shannon
 */
public class StravaWeekOfMonthTest {
	/**
	 * Test create method
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testCreate() {
		for (final StravaWeekOfMonth week : StravaWeekOfMonth.values()) {
			assertEquals(week, StravaWeekOfMonth.create(week.getValue()));
		}
	}

	/**
	 * Test description
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetDescription() {
		for (final StravaWeekOfMonth week : StravaWeekOfMonth.values()) {
			assertNotNull(week.getDescription());
		}
	}

	/**
	 * Test integrity of the id
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetId() {
		for (final StravaWeekOfMonth week : StravaWeekOfMonth.values()) {
			assertNotNull(week.getId());
			assertEquals(week, StravaWeekOfMonth.create(week.getId()));
		}
	}

}
