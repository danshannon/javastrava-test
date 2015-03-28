package test.api.model.reference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import javastrava.api.v3.model.reference.StravaLeaderboardDateRange;

import org.junit.Test;

/**
 * @author Dan Shannon
 *
 */
public class StravaLeaderboardDateRangeTest {
	@Test
	public void testGetDescription() {
		for (final StravaLeaderboardDateRange type : StravaLeaderboardDateRange.values()) {
			assertNotNull(type.getDescription());
		}
	}

	@Test
	public void testGetId() {
		for (final StravaLeaderboardDateRange type : StravaLeaderboardDateRange.values()) {
			assertNotNull(type.getId());
			assertEquals(type, StravaLeaderboardDateRange.create(type.getId()));
		}
	}

}
