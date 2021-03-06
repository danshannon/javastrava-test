/**
 *
 */
package test.service.impl.activityservice;

import javastrava.model.StravaAthlete;
import test.service.standardtests.ListMethodTest;
import test.service.standardtests.callbacks.ListCallback;
import test.service.standardtests.data.ActivityDataUtils;
import test.service.standardtests.data.AthleteDataUtils;

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
	protected Class<StravaAthlete> classUnderTest() {
		return StravaAthlete.class;
	}

	@Override
	protected Long idInvalid() {
		return ActivityDataUtils.ACTIVITY_INVALID;
	}

	@Override
	protected Long idPrivate() {
		return ActivityDataUtils.ACTIVITY_PRIVATE;
	}

	@Override
	protected Long idPrivateBelongsToOtherUser() {
		return ActivityDataUtils.ACTIVITY_PRIVATE_OTHER_USER;
	}

	@Override
	protected Long idValidWithEntries() {
		return ActivityDataUtils.ACTIVITY_WITH_KUDOS;
	}

	@Override
	protected Long idValidWithoutEntries() {
		return ActivityDataUtils.ACTIVITY_WITHOUT_KUDOS;
	}

	@Override
	protected ListCallback<StravaAthlete, Long> lister() {
		return ((strava, id) -> strava.listAllActivityKudoers(id));
	}

	@Override
	protected void validate(StravaAthlete athlete) {
		AthleteDataUtils.validateAthlete(athlete);
	}
}
