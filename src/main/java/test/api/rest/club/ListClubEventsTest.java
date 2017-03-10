package test.api.rest.club;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import javastrava.api.v3.model.StravaClubAnnouncement;
import javastrava.api.v3.model.StravaClubEvent;
import test.api.rest.APIListTest;
import test.api.rest.callback.APIListCallback;
import test.issues.strava.Issue112;
import test.service.standardtests.data.ClubDataUtils;
import test.utils.RateLimitedTestRunner;

/**
 * @author Dan Shannon
 *
 */
public class ListClubEventsTest extends APIListTest<StravaClubEvent, Integer> {
	/**
	 * @see test.api.rest.APIListTest#listCallback()
	 */
	@Override
	protected APIListCallback<StravaClubEvent, Integer> listCallback() {
		return (api, id) -> api.listClubGroupEvents(id);
	}

	/**
	 * @see test.api.rest.APIListTest#invalidId()
	 */
	@Override
	protected Integer invalidId() {
		return ClubDataUtils.CLUB_INVALID_ID;
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
		return ClubDataUtils.CLUB_PRIVATE_NON_MEMBER_ID;
	}

	/**
	 * List club announcements for a club which is private, and the authenticated user is a member
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testListClubAnnouncements_privateClubMember() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaClubAnnouncement[] announcements = api().listClubAnnouncements(ClubDataUtils.CLUB_PRIVATE_MEMBER_ID);
			assertNotNull(announcements);
		});
	}

	/**
	 * @see test.api.rest.APITest#validate(java.lang.Object)
	 */
	@Override
	protected void validate(final StravaClubEvent result) throws Exception {
		// TODO #138
		return;

	}

	@Override
	protected void validateArray(final StravaClubEvent[] list) {
		// TODO #138
		return;

	}

	/**
	 * @see test.api.rest.APIListTest#validId()
	 */
	@Override
	protected Integer validId() {
		return ClubDataUtils.CLUB_VALID_ID;
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
