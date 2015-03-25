package test.issues.strava;

import static org.junit.Assert.assertFalse;
import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaActivityUpdate;
import javastrava.api.v3.model.reference.StravaActivityType;
import javastrava.api.v3.rest.API;
import javastrava.api.v3.rest.ActivityAPI;
import javastrava.api.v3.service.exception.BadRequestException;
import javastrava.api.v3.service.exception.NotFoundException;

import org.junit.Test;

import test.utils.TestUtils;

public class Issue36 {
	@Test
	public void testIssue() throws NotFoundException, BadRequestException {
		final ActivityAPI api = API.instance(ActivityAPI.class, TestUtils.getValidTokenWithFullAccess());
		StravaActivity activity = api.createManualActivity(TestUtils.createDefaultActivity("Issue36.testIssue"));
		final StravaActivityUpdate update = new StravaActivityUpdate();
		update.setCommute(Boolean.TRUE);
		update.setPrivateActivity(Boolean.TRUE);
		update.setDescription("Blah");
		update.setGearId(TestUtils.GEAR_VALID_ID);
		update.setName("Blah");
		update.setTrainer(Boolean.TRUE);
		update.setType(StravaActivityType.RIDE);
		try {
			activity = api.updateActivity(activity.getId(), update);
		} catch (final Throwable e) {
			api.deleteActivity(activity.getId());
			throw e;
		}
		api.deleteActivity(activity.getId());

		assertFalse(activity.getCommute());
	}

}
