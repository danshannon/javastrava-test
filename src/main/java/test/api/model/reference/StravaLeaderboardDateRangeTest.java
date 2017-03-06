package test.api.model.reference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import javastrava.api.v3.model.reference.StravaLeaderboardDateRange;

/**
 * @author Dan Shannon
 *
 */
public class StravaLeaderboardDateRangeTest {
	/**
	 * Test returning the description
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetDescription() {
		for (final StravaLeaderboardDateRange type : StravaLeaderboardDateRange.values()) {
			assertNotNull(type.getDescription());
		}
	}

	/**
	 * Test returning the id
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetId() {
		for (final StravaLeaderboardDateRange type : StravaLeaderboardDateRange.values()) {
			assertNotNull(type.getId());
			assertEquals(type, StravaLeaderboardDateRange.create(type.getId()));
		}
	}

}
