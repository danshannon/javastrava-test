package test.issues.strava;

import static org.junit.Assert.assertFalse;
import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaActivityUpdate;
import javastrava.api.v3.model.reference.StravaActivityType;
import javastrava.api.v3.rest.ActivityAPI;
import javastrava.api.v3.rest.API;
import javastrava.api.v3.service.exception.BadRequestException;
import javastrava.api.v3.service.exception.NotFoundException;

import org.junit.Test;

import test.utils.TestUtils;

public class Issue36 {
	@Test
	public void testIssue() throws NotFoundException, BadRequestException {
		final ActivityAPI retrofit = API.instance(ActivityAPI.class, TestUtils.getValidToken());
		StravaActivity activity = retrofit.createManualActivity(TestUtils.createDefaultActivity());
		final StravaActivityUpdate update = new StravaActivityUpdate();
		update.setCommute(Boolean.TRUE);
		update.setPrivateActivity(Boolean.TRUE);
		update.setDescription("Blah");
		update.setGearId(TestUtils.GEAR_VALID_ID);
		update.setName("Blah");
		update.setTrainer(Boolean.TRUE);
		update.setType(StravaActivityType.RIDE);
		activity = retrofit.updateActivity(activity.getId(), update);
		assertFalse(activity.getCommute());
	}

}
