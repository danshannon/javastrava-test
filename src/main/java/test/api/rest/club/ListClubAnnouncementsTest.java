package test.api.rest.club;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import javastrava.api.v3.model.StravaClubAnnouncement;
import test.api.rest.APIListTest;
import test.api.rest.callback.TestListArrayCallback;
import test.service.standardtests.data.ClubDataUtils;
import test.utils.RateLimitedTestRunner;

/**
 * @author Dan Shannon
 *
 */
public class ListClubAnnouncementsTest extends APIListTest<StravaClubAnnouncement, Integer> {
	/**
	 * @see test.api.rest.APIListTest#listCallback()
	 */
	@Override
	protected TestListArrayCallback<StravaClubAnnouncement, Integer> listCallback() {
		return (api, id) -> api.listClubAnnouncements(id);
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

	@Test
	public void testListClubAnnouncements_privateClubMember() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaClubAnnouncement[] announcements = api().listClubAnnouncements(ClubDataUtils.CLUB_PRIVATE_MEMBER_ID);
			assertNotNull(announcements);
		});
	}

	// @Test
	// public void testListClubAnnouncements_privateClubNonMember() throws Exception {
	// RateLimitedTestRunner.run(() -> {
	// final StravaClubAnnouncement[] announcements = api()
	// .listClubAnnouncements(TestUtils.CLUB_PRIVATE_NON_MEMBER_ID);
	// assertNotNull(announcements);
	// assertTrue(announcements.length == 0);
	// } );
	// }

	/**
	 * @see test.api.rest.APITest#validate(java.lang.Object)
	 */
	@Override
	protected void validate(final StravaClubAnnouncement result) throws Exception {
		return;

	}

	/**
	 * @see test.api.rest.APIListTest#validateArray(java.lang.Object[])
	 */
	@Override
	protected void validateArray(final StravaClubAnnouncement[] list) {
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

}
