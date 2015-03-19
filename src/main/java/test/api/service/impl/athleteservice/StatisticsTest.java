package test.api.service.impl.athleteservice;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import javastrava.api.v3.model.StravaStatistics;

import org.junit.Test;

import test.api.model.StravaStatisticsTest;
import test.api.service.StravaTest;
import test.utils.TestUtils;

public class StatisticsTest extends StravaTest {
	@Test
	public void testStatistics_authenticatedAthlete() {
		final StravaStatistics stats = service().statistics(TestUtils.ATHLETE_AUTHENTICATED_ID);
		StravaStatisticsTest.validate(stats);
	}

	@Test
	public void testStatistics_otherAthlete() {
		final StravaStatistics stats = service().statistics(TestUtils.ATHLETE_VALID_ID);
		assertNotNull(stats);
	}

	@Test
	public void testStatistics_invalidAthlete() {
		final StravaStatistics stats = service().statistics(TestUtils.ATHLETE_INVALID_ID);
		assertNull(stats);
	}

	@Test
	public void testStatistics_privateAthlete() {
		final StravaStatistics stats = service().statistics(TestUtils.ATHLETE_PRIVATE_ID);
		StravaStatisticsTest.validate(stats);
	}

}
