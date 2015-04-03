package test.issues.strava;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaActivityUpdate;
import javastrava.api.v3.model.reference.StravaActivityType;
import javastrava.api.v3.rest.API;
import javastrava.api.v3.rest.ActivityAPI;
import test.utils.TestUtils;

public class Issue36 extends IssueTest {
	/**
	 * @see test.issues.strava.IssueTest#isIssue()
	 */
	@Override
	public boolean isIssue() throws Exception {
		final ActivityAPI api = API.instance(ActivityAPI.class, TestUtils.getValidTokenWithFullAccess());
		StravaActivity activity = api.createManualActivity(TestUtils.createDefaultActivity("Issue36.testIssue"));
		final StravaActivityUpdate update = new StravaActivityUpdate();
		update.setCommute(Boolean.TRUE);
		update.setPrivateActivity(Boolean.TRUE);
		update.setDescription("Blah");
		update.setGearId(TestUtils.GEAR_VALID_ID);
		update.setTrainer(Boolean.TRUE);
		update.setType(StravaActivityType.RIDE);
		try {
			activity = api.updateActivity(activity.getId(), update);
		} catch (final Throwable e) {
			api.deleteActivity(activity.getId());
			throw e;
		}
		api.deleteActivity(activity.getId());

		if (activity.getCommute().booleanValue()) {
			return false;
		}
		return true;
	}

	/**
	 * @see test.issues.strava.IssueTest#issueNumber()
	 */
	@Override
	public int issueNumber() {
		return 36;
	}

}
