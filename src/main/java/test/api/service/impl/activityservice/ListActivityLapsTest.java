package test.api.service.impl.activityservice;

import javastrava.api.v3.model.StravaLap;
import test.api.model.StravaLapTest;
import test.api.service.standardtests.ListMethodTest;
import test.api.service.standardtests.callbacks.ListCallback;
import test.issues.strava.Issue105;
import test.utils.TestUtils;

/**
 * <p>
 * Specific tests for list activity laps methods
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListActivityLapsTest extends ListMethodTest<StravaLap, Long> {

	@Override
	public Long idValidWithEntries() {
		return TestUtils.ACTIVITY_WITH_LAPS;
	}

	@Override
	public Long idValidWithoutEntries() {
		return TestUtils.ACTIVITY_WITHOUT_LAPS;
	}

	@Override
	public Long idPrivateBelongsToOtherUser() {
		return TestUtils.ACTIVITY_PRIVATE_OTHER_USER;
	}

	@Override
	public void testValidParentWithNoEntries() throws Exception {
		if (new Issue105().isIssue()) {
			return;
		}
		super.testValidParentWithNoEntries();
	}

	@Override
	protected void validate(final StravaLap object) {
		StravaLapTest.validateLap(object);
	}

	@Override
	protected ListCallback<StravaLap, Long> lister() {
		return ((strava, id) -> strava.listActivityLaps(id));
	}

	@Override
	protected Long idPrivate() {
		return TestUtils.ACTIVITY_PRIVATE;
	}

	@Override
	protected Long idInvalid() {
		return TestUtils.ACTIVITY_INVALID;
	}

}
