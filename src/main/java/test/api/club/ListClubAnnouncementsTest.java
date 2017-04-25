package test.api.club;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import javastrava.api.API;
import javastrava.model.StravaClubAnnouncement;
import javastrava.service.exception.UnauthorizedException;
import test.api.APIListTest;
import test.api.callback.APIListCallback;
import test.service.standardtests.data.ClubDataUtils;
import test.utils.RateLimitedTestRunner;

/**
 * <p>
 * Specific tests and config for {@link API#listClubAnnouncements(Integer)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListClubAnnouncementsTest extends APIListTest<StravaClubAnnouncement, Integer> {
	@Override
	protected Integer invalidId() {
		return ClubDataUtils.CLUB_INVALID_ID;
	}

	@Override
	protected APIListCallback<StravaClubAnnouncement, Integer> listCallback() {
		return (api, id) -> api.listClubAnnouncements(id);
	}

	@Override
	protected Integer privateId() {
		return null;
	}

	@Override
	protected Integer privateIdBelongsToOtherUser() {
		return ClubDataUtils.CLUB_PRIVATE_NON_MEMBER_ID;
	}

	/**
	 * <p>
	 * Attempt to list club announcements for a private club which the authenticated user is a member of
	 * </p>
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
	 * <p>
	 * Attempt to list club announcements for a private club which the authenticated user is not a member of
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testListClubAnnouncements_privateClubNonMember() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().listClubAnnouncements(ClubDataUtils.CLUB_PRIVATE_NON_MEMBER_ID);
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}
			fail("Returned a list of announcements for club " + ClubDataUtils.CLUB_PRIVATE_NON_MEMBER_ID + " of which the authenticated user is not a member"); //$NON-NLS-1$ //$NON-NLS-2$
		});
	}

	@Override
	protected void validate(final StravaClubAnnouncement result) throws Exception {
		return;

	}

	@Override
	protected void validateArray(final StravaClubAnnouncement[] list) {
		return;

	}

	@Override
	protected Integer validId() {
		return ClubDataUtils.CLUB_VALID_ID;
	}

	@Override
	protected Integer validIdBelongsToOtherUser() {
		return null;
	}

	@Override
	protected Integer validIdNoChildren() {
		return null;
	}

}
