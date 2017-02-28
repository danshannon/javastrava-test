/**
 *
 */
package test.service.impl.activityservice;

import javastrava.api.v3.model.StravaAthlete;
import test.api.model.StravaAthleteTest;
import test.service.standardtests.ListMethodTest;
import test.service.standardtests.callbacks.ListCallback;
import test.utils.TestUtils;

/**
 * <p>
 * Specific tests for methods that list all activity kudoers
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListAllActivityKudoersTest extends ListMethodTest<StravaAthlete, Long> {

	@Override
	protected ListCallback<StravaAthlete, Long> lister() {
		return ((strava, id) -> strava.listAllActivityKudoers(id));
	}

	@Override
	protected Long idPrivate() {
		return TestUtils.ACTIVITY_PRIVATE;
	}

	@Override
	protected Long idPrivateBelongsToOtherUser() {
		return TestUtils.ACTIVITY_PRIVATE_OTHER_USER;
	}

	@Override
	protected Long idValidWithEntries() {
		return TestUtils.ACTIVITY_WITH_KUDOS;
	}

	@Override
	protected Long idValidWithoutEntries() {
		return TestUtils.ACTIVITY_WITHOUT_KUDOS;
	}

	@Override
	protected Long idInvalid() {
		return TestUtils.ACTIVITY_INVALID;
	}

	@Override
	protected void validate(StravaAthlete athlete) {
		StravaAthleteTest.validateAthlete(athlete);
	}
}
