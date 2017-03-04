package test.api.rest.athlete;

import javastrava.api.v3.model.StravaAthleteZones;
import test.api.model.StravaAthleteZonesTest;
import test.api.rest.APIGetTest;
import test.api.rest.TestGetCallback;
import test.service.standardtests.data.AthleteDataUtils;

public class GetAuthenticatedAthleteZonesTest extends APIGetTest<StravaAthleteZones, Integer> {

	@Override
	protected Integer invalidId() {
		return null;
	}

	@Override
	protected Integer privateId() {
		return null;
	}

	@Override
	protected Integer privateIdBelongsToOtherUser() {
		return null;
	}

	@Override
	protected Integer validId() {
		return AthleteDataUtils.ATHLETE_AUTHENTICATED_ID;
	}

	@Override
	protected Integer validIdBelongsToOtherUser() {
		return null;
	}

	@Override
	protected void validate(StravaAthleteZones result) throws Exception {
		StravaAthleteZonesTest.validate(result);

	}

	@Override
	protected TestGetCallback<StravaAthleteZones, Integer> getCallback() {
		return ((api, id) -> api.getAuthenticatedAthleteZones());
	}

}
