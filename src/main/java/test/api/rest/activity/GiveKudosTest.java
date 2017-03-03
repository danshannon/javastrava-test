package test.api.rest.activity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.model.StravaResponse;
import test.api.model.StravaAthleteTest;
import test.api.rest.APICreateTest;
import test.issues.strava.Issue29;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * <p>
 * Test giveKudos methods
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class GiveKudosTest extends APICreateTest<StravaResponse, Long> {
	/**
	 *
	 */
	public GiveKudosTest() {
		super();
		this.creationCallback = (api, response, activityId) -> api.giveKudos(activityId);
		this.createAPIResponseIsNull = true;
	}

	/**
	 * @see test.api.rest.APICreateTest#create_privateParentWithoutViewPrivate()
	 */
	@Override
	public void create_privateParentWithoutViewPrivate() throws Exception {
		super.create_privateParentWithoutViewPrivate();
	}

	@Override
	public void create_valid() throws Exception {
		super.create_valid();
		RateLimitedTestRunner.run(() -> {
			assertFalse(hasGivenKudos(validParentId(), TestUtils.ATHLETE_AUTHENTICATED_ID));
		});
	}

	@Override
	public void create_validParentBelongsToOtherUser() throws Exception {
		super.create_validParentBelongsToOtherUser();
		RateLimitedTestRunner.run(() -> {
			assertTrue(hasGivenKudos(validParentOtherUserId(), TestUtils.ATHLETE_AUTHENTICATED_ID));
		});
	}

	@Override
	public void create_validParentNoWriteAccess() throws Exception {
		if (new Issue29().isIssue()) {
			return;
		}

		super.create_validParentNoWriteAccess();
	}

	/**
	 * @see test.api.rest.APICreateTest#createObject()
	 */
	@Override
	protected StravaResponse createObject() {
		return null;
	}

	/**
	 * @see test.api.rest.APICreateTest#forceDelete(java.lang.Object)
	 */
	@Override
	protected void forceDelete(final StravaResponse objectToDelete) {
		return;

	}

	/**
	 * @param activityId
	 * @param athleteId
	 * @return true if the athlete has given kudos to the activity
	 */
	@SuppressWarnings("static-method")
	private boolean hasGivenKudos(final Long activityId, final Integer athleteId) {
		final StravaAthlete[] kudoers = api().listActivityKudoers(activityId, null, null);

		boolean found = false;
		for (final StravaAthlete athlete : kudoers) {
			StravaAthleteTest.validateAthlete(athlete);
			if (athlete.getId().equals(athleteId)) {
				found = true;
			}
		}
		return found;
	}

	/**
	 * @see test.api.rest.APICreateTest#invalidParentId()
	 */
	@Override
	protected Long invalidParentId() {
		return TestUtils.ACTIVITY_INVALID;
	}

	/**
	 * @see test.api.rest.APICreateTest#privateParentId()
	 */
	@Override
	protected Long privateParentId() {
		return TestUtils.ACTIVITY_PRIVATE;
	}

	/**
	 * @see test.api.rest.APICreateTest#privateParentOtherUserId()
	 */
	@Override
	protected Long privateParentOtherUserId() {
		return TestUtils.ACTIVITY_PRIVATE_OTHER_USER;
	}

	/**
	 * @see test.api.rest.APITest#validate(java.lang.Object)
	 */
	@Override
	protected void validate(final StravaResponse result) throws Exception {
		return;
	}

	/**
	 * @see test.api.rest.APICreateTest#validParentId()
	 */
	@Override
	protected Long validParentId() {
		return TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER;
	}

	/**
	 * @see test.api.rest.APICreateTest#validParentOtherUserId()
	 */
	@Override
	protected Long validParentOtherUserId() {
		return TestUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER;
	}

}
