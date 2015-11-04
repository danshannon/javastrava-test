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

public class GiveKudosTest extends APICreateTest<StravaResponse, Integer> {
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
		// TODO This is a workaround for issue javastrava-api #29
		// (https://github.com/danshannon/javastravav3api/issues/29)
		if (new Issue29().isIssue()) {
			return;
		}
		// End of workaround

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
	 * @return
	 */
	private boolean hasGivenKudos(final Integer activityId, final Integer athleteId) {
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
	protected Integer invalidParentId() {
		return TestUtils.ACTIVITY_INVALID;
	}

	/**
	 * @see test.api.rest.APICreateTest#privateParentId()
	 */
	@Override
	protected Integer privateParentId() {
		return TestUtils.ACTIVITY_PRIVATE;
	}

	/**
	 * @see test.api.rest.APICreateTest#privateParentOtherUserId()
	 */
	@Override
	protected Integer privateParentOtherUserId() {
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
	protected Integer validParentId() {
		return TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER;
	}

	/**
	 * @see test.api.rest.APICreateTest#validParentOtherUserId()
	 */
	@Override
	protected Integer validParentOtherUserId() {
		return TestUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER;
	}

}
