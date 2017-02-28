package test.service.impl.athleteservice;

import javastrava.api.v3.model.StravaAthleteZones;
import javastrava.api.v3.service.Strava;
import test.api.model.StravaAthleteZonesTest;
import test.service.standardtests.GetMethodTest;
import test.service.standardtests.callbacks.GetCallback;
import test.utils.TestUtils;

/**
 * <p>
 * Specific tests for {@link Strava#getAuthenticatedAthleteZones()} methods
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class GetAuthenticatedAthleteZonesTest extends GetMethodTest<StravaAthleteZones, Integer> {

	@Override
	protected Integer getIdValid() {
		return TestUtils.ATHLETE_AUTHENTICATED_ID;
	}

	@Override
	protected Integer getIdInvalid() {
		return null;
	}

	@Override
	protected Integer getIdPrivate() {
		return null;
	}

	@Override
	protected Integer getIdPrivateBelongsToOtherUser() {
		return null;
	}

	@Override
	protected GetCallback<StravaAthleteZones, Integer> getter() throws Exception {
		return ((strava, id) -> strava.getAuthenticatedAthleteZones());
	}

	@Override
	protected void validate(StravaAthleteZones object) {
		StravaAthleteZonesTest.validate(object);

	}

}
