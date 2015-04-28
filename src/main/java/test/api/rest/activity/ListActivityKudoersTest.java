package test.api.rest.activity;

import java.util.Arrays;

import javastrava.api.v3.model.StravaAthlete;
import test.api.model.StravaAthleteTest;
import test.api.rest.APIListTest;
import test.issues.strava.Issue95;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class ListActivityKudoersTest extends APIListTest<StravaAthlete, Integer> {
	/**
	 *
	 */
	public ListActivityKudoersTest() {
		this.listCallback = (api, id) -> api.listActivityKudoers(id, null, null);
		this.pagingCallback = paging -> api().listActivityKudoers(validId(), paging.getPage(), paging.getPageSize());
	}

	/**
	 * @see test.api.rest.APIListTest#invalidId()
	 */
	@Override
	protected Integer invalidId() {
		return TestUtils.ACTIVITY_INVALID;
	}

	/*
	 * Workaround method for issue #95 - remove this override when issue is
	 * resolved!
	 */
	@Override
	public void list_privateWithoutViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			if (new Issue95().isIssue()) {
				return;
			}
		} );
		RateLimitedTestRunner.run(() -> {
			super.list_privateWithoutViewPrivate();
		} );
	}

	/**
	 * @see test.api.rest.APIListTest#privateId()
	 */
	@Override
	protected Integer privateId() {
		return TestUtils.ACTIVITY_PRIVATE;
	}

	/**
	 * @see test.api.rest.APIListTest#privateIdBelongsToOtherUser()
	 */
	@Override
	protected Integer privateIdBelongsToOtherUser() {
		return TestUtils.ACTIVITY_PRIVATE_OTHER_USER;
	}

	@Override
	protected void validate(final StravaAthlete athlete) {
		StravaAthleteTest.validateAthlete(athlete);

	}

	/**
	 * @see test.api.rest.APIListTest#validateArray(java.lang.Object[])
	 */
	@Override
	protected void validateArray(final StravaAthlete[] bigList) {
		StravaAthleteTest.validateList(Arrays.asList(bigList));

	}

	/**
	 * @see test.api.rest.APIListTest#validId()
	 */
	@Override
	protected Integer validId() {
		return TestUtils.ACTIVITY_PRIVATE_WITH_KUDOS;
	}

	/**
	 * @see test.api.rest.APIListTest#validIdBelongsToOtherUser()
	 */
	@Override
	protected Integer validIdBelongsToOtherUser() {
		return TestUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER;
	}

	/**
	 * @see test.api.rest.APIListTest#validIdNoChildren()
	 */
	@Override
	protected Integer validIdNoChildren() {
		return TestUtils.ACTIVITY_WITHOUT_KUDOS;
	}

}
