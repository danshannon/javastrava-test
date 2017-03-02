package test.api.rest.club;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import javastrava.api.v3.model.StravaClubAnnouncement;
import javastrava.api.v3.model.StravaClubEvent;
import test.api.rest.APIListTest;
import test.api.rest.TestListArrayCallback;
import test.issues.strava.Issue112;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * @author Dan Shannon
 *
 */
public class ListClubEventsTest extends APIListTest<StravaClubEvent, Integer> {
	/**
	 * @see test.api.rest.APIListTest#listCallback()
	 */
	@Override
	protected TestListArrayCallback<StravaClubEvent, Integer> listCallback() {
		return (api, id) -> api.listClubGroupEvents(id);
	}

	/**
	 * @see test.api.rest.APIListTest#invalidId()
	 */
	@Override
	protected Integer invalidId() {
		return TestUtils.CLUB_INVALID_ID;
	}

	/**
	 * @see test.api.rest.APIListTest#privateId()
	 */
	@Override
	protected Integer privateId() {
		return null;
	}

	/**
	 * @see test.api.rest.APIListTest#privateIdBelongsToOtherUser()
	 */
	@Override
	protected Integer privateIdBelongsToOtherUser() {
		return TestUtils.CLUB_PRIVATE_NON_MEMBER_ID;
	}

	@Test
	public void testListClubAnnouncements_privateClubMember() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaClubAnnouncement[] announcements = api().listClubAnnouncements(TestUtils.CLUB_PRIVATE_MEMBER_ID);
			assertNotNull(announcements);
		});
	}

	/**
	 * @see test.api.rest.APITest#validate(java.lang.Object)
	 */
	@Override
	protected void validate(final StravaClubEvent result) throws Exception {
		// TODO
		return;

	}

	/**
	 * @see test.api.rest.APIListTest#validateArray(java.lang.Object[])
	 */
	@Override
	protected void validateArray(final StravaClubEvent[] list) {
		// TODO
		return;

	}

	/**
	 * @see test.api.rest.APIListTest#validId()
	 */
	@Override
	protected Integer validId() {
		return TestUtils.CLUB_VALID_ID;
	}

	/**
	 * @see test.api.rest.APIListTest#validIdBelongsToOtherUser()
	 */
	@Override
	protected Integer validIdBelongsToOtherUser() {
		return null;
	}

	/**
	 * @see test.api.rest.APIListTest#validIdNoChildren()
	 */
	@Override
	protected Integer validIdNoChildren() {
		return null;
	}

	/**
	 * @see test.api.rest.APIListTest#list_privateBelongsToOtherUser()
	 */
	@Override
	public void list_privateBelongsToOtherUser() throws Exception {
		if (new Issue112().isIssue()) {
			return;
		}
		super.list_privateBelongsToOtherUser();
	}

}
