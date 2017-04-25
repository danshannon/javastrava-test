package test.service.impl.athleteservice;

import javastrava.model.StravaAthleteZones;
import javastrava.service.Strava;
import test.service.standardtests.GetMethodTest;
import test.service.standardtests.callbacks.GetCallback;
import test.service.standardtests.data.AthleteDataUtils;

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
	protected Integer getIdValid() {
		return AthleteDataUtils.ATHLETE_AUTHENTICATED_ID;
	}

	@Override
	protected GetCallback<StravaAthleteZones, Integer> getter() {
		return ((strava, id) -> strava.getAuthenticatedAthleteZones());
	}

	@Override
	public void testGetNullId() throws Exception {
		// Test doesn't make sense as there's no id
		return;
	}

	@Override
	protected void validate(StravaAthleteZones object) {
		AthleteDataUtils.validateAthleteZones(object);

	}

}
