package test.api.rest.activity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeTrue;

import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.model.StravaResponse;
import javastrava.api.v3.rest.API;
import javastrava.config.JavastravaApplicationConfig;
import test.api.rest.APICreateTest;
import test.api.rest.callback.APICreateCallback;
import test.issues.strava.Issue163;
import test.issues.strava.Issue29;
import test.service.standardtests.data.ActivityDataUtils;
import test.service.standardtests.data.AthleteDataUtils;
import test.utils.RateLimitedTestRunner;

/**
 * <p>
 * Specific tests for {@link API#giveKudos(Long)} methods
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class GiveKudosTest extends APICreateTest<StravaResponse, Long> {

	@Override
	public void create_invalidParent() throws Exception {
		// Can't execute the test unless we have Strava's application-level permission to delete activities
		assumeTrue(JavastravaApplicationConfig.STRAVA_ALLOWS_GIVE_KUDOS);
		super.create_invalidParent();

	}

	@Override
	public void create_privateParentBelongsToOtherUser() throws Exception {
		// Can't execute the test unless we have Strava's application-level permission to delete activities
		assumeTrue(JavastravaApplicationConfig.STRAVA_ALLOWS_GIVE_KUDOS);
		super.create_privateParentBelongsToOtherUser();
	}

	/**
	 * @see test.api.rest.APICreateTest#create_privateParentWithoutViewPrivate()
	 */
	@Override
	public void create_privateParentWithoutViewPrivate() throws Exception {
		// Can't execute the test unless we have Strava's application-level permission to delete activities
		assumeTrue(JavastravaApplicationConfig.STRAVA_ALLOWS_GIVE_KUDOS);
		super.create_privateParentWithoutViewPrivate();

	}

	@Override
	public void create_privateParentWithViewPrivate() throws Exception {
		assumeFalse(Issue163.issue);

		assumeTrue(JavastravaApplicationConfig.STRAVA_ALLOWS_GIVE_KUDOS);
		super.create_privateParentWithViewPrivate();
	}

	@Override
	public void create_valid() throws Exception {
		// If id is null, don't run
		if (validParentId() == null) {
			return;
		}

		// Can't execute the test unless we have Strava's application-level permission to delete activities
		assumeTrue(JavastravaApplicationConfig.STRAVA_ALLOWS_GIVE_KUDOS);
		super.create_valid();
		RateLimitedTestRunner.run(() -> {
			assertFalse(hasGivenKudos(validParentId(), AthleteDataUtils.ATHLETE_AUTHENTICATED_ID));
		});

	}

	@Override
	public void create_validParentBelongsToOtherUser() throws Exception {
		// Can't execute the test unless we have Strava's application-level permission to delete activities
		assumeTrue(JavastravaApplicationConfig.STRAVA_ALLOWS_GIVE_KUDOS);
		super.create_validParentBelongsToOtherUser();
		RateLimitedTestRunner.run(() -> {
			assertTrue(hasGivenKudos(validParentOtherUserId(), AthleteDataUtils.ATHLETE_AUTHENTICATED_ID));
		});

	}

	@Override
	public void create_validParentNoWriteAccess() throws Exception {
		// Can't execute the test unless we have Strava's application-level permission to delete activities
		assumeTrue(JavastravaApplicationConfig.STRAVA_ALLOWS_GIVE_KUDOS);
		assumeFalse(Issue29.issue);

		super.create_validParentNoWriteAccess();
	}

	@Override
	protected boolean createAPIResponseIsNull() {
		return true;
	}

	/**
	 * @see test.api.rest.APICreateTest#createObject()
	 */
	@Override
	protected StravaResponse createObject() {
		return null;
	}

	@Override
	protected APICreateCallback<StravaResponse, Long> creator() {
		return ((api, response, id) -> api.giveKudos(id));
	}

	@Override
	protected void forceDelete(final StravaResponse objectToDelete) {
		return;

	}

	/**
	 * @param activityId
	 *            Activity to check for kudos
	 * @param athleteId
	 *            Athlete who may have given kudos
	 * @return <code>true</code> if the athlete has given kudos to the activity
	 */
	@SuppressWarnings("static-method")
	private boolean hasGivenKudos(final Long activityId, final Integer athleteId) {
		final StravaAthlete[] kudoers = api().listActivityKudoers(activityId, null, null);

		boolean found = false;
		for (final StravaAthlete athlete : kudoers) {
			AthleteDataUtils.validateAthlete(athlete);
			if (athlete.getId().equals(athleteId)) {
				found = true;
			}
		}
		return found;
	}

	@Override
	protected Long invalidParentId() {
		return ActivityDataUtils.ACTIVITY_INVALID;
	}

	@Override
	protected Long privateParentId() {
		return ActivityDataUtils.ACTIVITY_PRIVATE;
	}

	@Override
	protected Long privateParentOtherUserId() {
		return ActivityDataUtils.ACTIVITY_PRIVATE_OTHER_USER;
	}

	@Override
	protected void validate(final StravaResponse result) throws Exception {
		return;
	}

	@Override
	protected Long validParentId() {
		return null;
	}

	@Override
	protected Long validParentOtherUserId() {
		return ActivityDataUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER;
	}

}
