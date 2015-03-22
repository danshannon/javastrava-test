package test.api.service.impl.athleteservice;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import javastrava.api.v3.model.StravaStatistics;

import org.junit.Test;

import test.api.model.StravaStatisticsTest;
import test.api.service.StravaTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestCallback;
import test.utils.TestUtils;

public class StatisticsTest extends StravaTest {
	@Test
	public void testStatistics_authenticatedAthlete() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				final StravaStatistics stats = service().statistics(TestUtils.ATHLETE_AUTHENTICATED_ID);
				StravaStatisticsTest.validate(stats);
			}
		});
	}

	@Test
	public void testStatistics_otherAthlete() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				final StravaStatistics stats = service().statistics(TestUtils.ATHLETE_VALID_ID);
				assertNotNull(stats);
			}
		});
	}

	@Test
	public void testStatistics_invalidAthlete() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				final StravaStatistics stats = service().statistics(TestUtils.ATHLETE_INVALID_ID);
				assertNull(stats);
			}
		});
	}

	@Test
	public void testStatistics_privateAthlete() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				final StravaStatistics stats = service().statistics(TestUtils.ATHLETE_PRIVATE_ID);
				StravaStatisticsTest.validate(stats);
			}
		});
	}

}
