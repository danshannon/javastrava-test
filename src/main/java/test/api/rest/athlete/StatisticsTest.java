package test.api.rest.athlete;

import static org.junit.Assert.fail;

import javastrava.api.v3.model.StravaStatistics;
import javastrava.api.v3.service.exception.UnauthorizedException;
import test.api.model.StravaStatisticsTest;
import test.api.rest.APIGetTest;
import test.api.rest.TestGetCallback;
import test.service.standardtests.data.AthleteDataUtils;
import test.utils.RateLimitedTestRunner;

public class StatisticsTest extends APIGetTest<StravaStatistics, Integer> {

	@Override
	public void get_validBelongsToOtherUser() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().statistics(validIdBelongsToOtherUser());
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}
			fail("Returned statistics for another athlete");
		});
	}

	/**
	 * @see test.api.rest.APIGetTest#invalidId()
	 */
	@Override
	protected Integer invalidId() {
		return AthleteDataUtils.ATHLETE_INVALID_ID;
	}

	/**
	 * @see test.api.rest.APIGetTest#privateId()
	 */
	@Override
	protected Integer privateId() {
		return null;
	}

	/**
	 * @see test.api.rest.APIGetTest#privateIdBelongsToOtherUser()
	 */
	@Override
	protected Integer privateIdBelongsToOtherUser() {
		return AthleteDataUtils.ATHLETE_PRIVATE_ID;
	}

	/**
	 * @see test.api.rest.APITest#validate(java.lang.Object)
	 */
	@Override
	protected void validate(final StravaStatistics result) throws Exception {
		StravaStatisticsTest.validate(result);

	}

	/**
	 * @see test.api.rest.APIGetTest#validId()
	 */
	@Override
	protected Integer validId() {
		return AthleteDataUtils.ATHLETE_AUTHENTICATED_ID;
	}

	/**
	 * @see test.api.rest.APIGetTest#validIdBelongsToOtherUser()
	 */
	@Override
	protected Integer validIdBelongsToOtherUser() {
		return AthleteDataUtils.ATHLETE_VALID_ID;
	}

	@Override
	protected TestGetCallback<StravaStatistics, Integer> getCallback() {
		return ((api, id) -> api.statistics(id));
	}
}
