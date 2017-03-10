package test.api.rest.athlete;

import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.rest.API;
import test.api.model.StravaAthleteTest;
import test.api.rest.APIGetTest;
import test.api.rest.callback.APIGetCallback;
import test.service.standardtests.data.AthleteDataUtils;

/**
 * <p>
 * Specific tests for {@link API#getAuthenticatedAthlete()}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class GetAuthenticatedAthleteTest extends APIGetTest<StravaAthlete, Integer> {
	@Override
	protected APIGetCallback<StravaAthlete, Integer> getter() {
		return ((api, id) -> api.getAuthenticatedAthlete());
	}

	/**
	 * @see test.api.rest.APIGetTest#invalidId()
	 */
	@Override
	protected Integer invalidId() {
		return null;
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
		return AthleteDataUtils.ATHLETE_AUTHENTICATED_ID;
	}

	/**
	 * @see test.api.rest.APIGetTest#validIdBelongsToOtherUser()
	 */
	@Override
	protected Integer validIdBelongsToOtherUser() {
		return null;
	}

}
