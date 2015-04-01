package test.api.rest.athlete;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import javastrava.api.v3.model.StravaStatistics;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.api.v3.service.exception.UnauthorizedException;

import org.junit.Test;

import test.api.model.StravaStatisticsTest;
import test.api.rest.APITest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class StatisticsTest extends APITest {
	@Test
	public void testStatistics_authenticatedAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaStatistics stats = api().statistics(TestUtils.ATHLETE_AUTHENTICATED_ID);
			StravaStatisticsTest.validate(stats);
		});
	}

	@Test
	public void testStatistics_invalidAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().statistics(TestUtils.ATHLETE_INVALID_ID);
			} catch (final NotFoundException e) {
				// Expected
				return;
			}
			fail("Returned statistics for a non-existent athlete!");
		});
	}

	@Test
	public void testStatistics_otherAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaStatistics stats = api().statistics(TestUtils.ATHLETE_VALID_ID);
			assertNotNull(stats);
			StravaStatisticsTest.validate(stats);
		});
	}

	@Test
	public void testStatistics_privateAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().statistics(TestUtils.ATHLETE_PRIVATE_ID);
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}
			fail("Returned statistics for a private athlete!");
		});
	}

}
