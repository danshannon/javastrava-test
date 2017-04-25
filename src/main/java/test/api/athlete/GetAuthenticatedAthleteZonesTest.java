package test.api.athlete;

import javastrava.api.API;
import javastrava.model.StravaAthleteZones;
import test.api.APIGetTest;
import test.api.callback.APIGetCallback;
import test.service.standardtests.data.AthleteDataUtils;

/**
 * <p>
 * Specific tests for {@link API#getAuthenticatedAthleteZones()}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class GetAuthenticatedAthleteZonesTest extends APIGetTest<StravaAthleteZones, Integer> {

	@Override
	protected APIGetCallback<StravaAthleteZones, Integer> getter() {
		return ((api, id) -> api.getAuthenticatedAthleteZones());
	}

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
	protected void validate(StravaAthleteZones result) throws Exception {
		AthleteDataUtils.validateAthleteZones(result);

	}

	@Override
	protected Integer validId() {
		return AthleteDataUtils.ATHLETE_AUTHENTICATED_ID;
	}

	@Override
	protected Integer validIdBelongsToOtherUser() {
		return null;
	}

}
