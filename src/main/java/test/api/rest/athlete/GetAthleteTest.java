package test.api.rest.athlete;

<<<<<<< HEAD
=======
import javastrava.api.v3.model.StravaAthlete;

>>>>>>> branch 'master' of https://github.com/danshannon/javastrava-test.git
import org.junit.Test;

import javastrava.api.v3.model.StravaAthlete;
import test.api.model.StravaAthleteTest;
import test.api.rest.APIGetTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class GetAthleteTest extends APIGetTest<StravaAthlete, Integer> {
	/**
<<<<<<< HEAD
	 *
	 */
	public GetAthleteTest() {
		this.getCallback = (api, id) -> api.getAthlete(id);
	}

	/**
	 * @see test.api.rest.APIGetTest#invalidId()
	 */
	@Override
	protected Integer invalidId() {
		return TestUtils.ATHLETE_INVALID_ID;
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
		return null;
=======
	 * No-args constructor provides the required callbacks
	 */
	public GetAthleteTest() {
		this.getCallback = (api, id) -> api.getAthlete(id);
>>>>>>> branch 'master' of https://github.com/danshannon/javastrava-test.git
	}

	@Test
	public void testGetAthlete_privateAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaAthlete athlete = api().getAthlete(TestUtils.ATHLETE_PRIVATE_ID);
			StravaAthleteTest.validateAthlete(athlete);
		} );
	}

	/**
<<<<<<< HEAD
	 * @see test.api.rest.APITest#validate(java.lang.Object)
	 */
	@Override
	protected void validate(final StravaAthlete result) throws Exception {
		StravaAthleteTest.validateAthlete(result);

	}

	/**
	 * @see test.api.rest.APIGetTest#validId()
	 */
	@Override
	protected Integer validId() {
		return TestUtils.ATHLETE_AUTHENTICATED_ID;
	}

	/**
	 * @see test.api.rest.APIGetTest#validIdBelongsToOtherUser()
	 */
	@Override
	protected Integer validIdBelongsToOtherUser() {
		return TestUtils.ATHLETE_VALID_ID;
=======
	 * @see test.api.rest.APIGetTest#invalidId()
	 */
	@Override
	protected Integer invalidId() {
		return TestUtils.ATHLETE_INVALID_ID;
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
		return null;
	}

	/**
	 * @see test.api.rest.APIGetTest#validId()
	 */
	@Override
	protected Integer validId() {
		return TestUtils.ATHLETE_AUTHENTICATED_ID;
	}

	/**
	 * @see test.api.rest.APIGetTest#validIdBelongsToOtherUser()
	 */
	@Override
	protected Integer validIdBelongsToOtherUser() {
		return TestUtils.ATHLETE_VALID_ID;
	}

	/**
	 * @see test.api.rest.APITest#validate(java.lang.Object)
	 */
	@Override
	protected void validate(final StravaAthlete result) throws Exception {
		StravaAthleteTest.validateAthlete(result);

>>>>>>> branch 'master' of https://github.com/danshannon/javastrava-test.git
	}

}
