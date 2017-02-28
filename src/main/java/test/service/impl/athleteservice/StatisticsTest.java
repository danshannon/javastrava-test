package test.service.impl.athleteservice;

import org.junit.Test;

import javastrava.api.v3.model.StravaStatistics;
import test.api.model.StravaStatisticsTest;
import test.service.standardtests.GetMethodTest;
import test.service.standardtests.callbacks.GetCallback;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * <p>
 * Specific tests for statistics methods
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class StatisticsTest extends GetMethodTest<StravaStatistics, Integer> {
	/**
	 * <p>
	 * Test that we can get statistics for the authenticated athlete
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Test
	public void testStatistics_authenticatedAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaStatistics stats = TestUtils.strava().statistics(TestUtils.ATHLETE_AUTHENTICATED_ID);
			StravaStatisticsTest.validate(stats);
		});
	}

	@Override
	protected Integer getIdValid() {
		return TestUtils.ATHLETE_VALID_ID;
	}

	@Override
	protected Integer getIdInvalid() {
		return TestUtils.ATHLETE_INVALID_ID;
	}

	@Override
	protected Integer getIdPrivate() {
		return null;
	}

	@Override
	protected Integer getIdPrivateBelongsToOtherUser() {
		return TestUtils.ATHLETE_PRIVATE_ID;
	}

	@Override
	protected GetCallback<StravaStatistics, Integer> getter() throws Exception {
		return ((strava, id) -> strava.statistics(id));
	}

	@Override
	protected void validate(StravaStatistics object) {
		StravaStatisticsTest.validate(object);
	}

}
