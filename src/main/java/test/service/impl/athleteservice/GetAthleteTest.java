package test.service.impl.athleteservice;

import javastrava.api.v3.model.StravaAthlete;
import test.api.model.StravaAthleteTest;
import test.service.standardtests.GetMethodTest;
import test.service.standardtests.callbacks.GetCallback;
import test.service.standardtests.data.AthleteDataUtils;

/**
 * <p>
 * Specific test configs for get athlete methods
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class GetAthleteTest extends GetMethodTest<StravaAthlete, Integer> {

	@Override
	protected Integer getIdValid() {
		return AthleteDataUtils.ATHLETE_VALID_ID;
	}

	@Override
	protected Integer getIdInvalid() {
		return AthleteDataUtils.ATHLETE_INVALID_ID;
	}

	@Override
	protected Integer getIdPrivate() {
		return null;
	}

	@Override
	protected Integer getIdPrivateBelongsToOtherUser() {
		return AthleteDataUtils.ATHLETE_PRIVATE_ID;
	}

	@Override
	protected GetCallback<StravaAthlete, Integer> getter() throws Exception {
		return ((strava, id) -> strava.getAthlete(id));
	}

	@Override
	protected void validate(StravaAthlete object) {
		StravaAthleteTest.validateAthlete(object);
	}
}
