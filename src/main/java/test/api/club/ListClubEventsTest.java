package test.api.club;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assume.assumeFalse;

import org.junit.Test;

import javastrava.model.StravaClubAnnouncement;
import javastrava.model.StravaClubEvent;
import test.api.APIListTest;
import test.api.callback.APIListCallback;
import test.issues.strava.Issue112;
import test.service.standardtests.data.ClubDataUtils;
import test.utils.RateLimitedTestRunner;

/**
 * @author Dan Shannon
 *
 */
public class ListClubEventsTest extends APIListTest<StravaClubEvent, Integer> {
	/**
	 * @see test.api.APIListTest#invalidId()
	 */
	@Override
	protected Integer invalidId() {
		return ClubDataUtils.CLUB_INVALID_ID;
	}

	/**
	 * @see test.api.APIListTest#list_privateBelongsToOtherUser()
	 */
	@Override
	public void list_privateBelongsToOtherUser() throws Exception {
		assumeFalse(Issue112.issue());

		super.list_privateBelongsToOtherUser();
	}

	/**
	 * @see test.api.APIListTest#listCallback()
	 */
	@Override
	protected APIListCallback<StravaClubEvent, Integer> listCallback() {
		return (api, id) -> api.listClubGroupEvents(id);
	}

	/**
	 * @see test.api.APIListTest#privateId()
	 */
	@Override
	protected Integer privateId() {
		return null;
	}

	/**
	 * @see test.api.APIListTest#privateIdBelongsToOtherUser()
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
	 * @see test.api.APITest#validate(java.lang.Object)
	 */
	@Override
	protected void validate(final StravaClubEvent result) throws Exception {
		ClubDataUtils.validateClubEvent(result);

	}

	@Override
	protected void validateArray(final StravaClubEvent[] list) throws Exception {
		for (final StravaClubEvent event : list) {
			validate(event);
		}

	}

	/**
	 * @see test.api.APIListTest#validId()
	 */
	@Override
	protected Integer validId() {
		return ClubDataUtils.CLUB_VALID_ID;
	}

	/**
	 * @see test.api.APIListTest#validIdBelongsToOtherUser()
	 */
	@Override
	protected Integer validIdBelongsToOtherUser() {
		return null;
	}

	/**
	 * @see test.api.APIListTest#validIdNoChildren()
	 */
	@Override
	protected Integer validIdNoChildren() {
		return null;
	}

}
