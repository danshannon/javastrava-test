package test.api.service.impl.clubservice;

import javastrava.api.v3.model.StravaClubAnnouncement;
import javastrava.api.v3.service.Strava;
import test.api.service.standardtests.ListMethodTest;
import test.api.service.standardtests.callbacks.ListCallback;
import test.utils.TestUtils;

/**
 * @author Dan Shannon
 *
 */
public class ListClubAnnouncementsTest extends ListMethodTest<StravaClubAnnouncement, Integer> {
	/**
	 * @see test.api.service.standardtests.spec.ListMethodTests#getValidParentWithEntries()
	 */
	@Override
	public Integer getValidParentWithEntries() {
		return TestUtils.CLUB_VALID_ID;
	}

	/**
	 * @see test.api.service.standardtests.spec.ListMethodTests#getValidParentWithNoEntries()
	 */
	@Override
	public Integer getValidParentWithNoEntries() {
		return null;
	}

	/**
	 * @see test.api.service.standardtests.spec.ListMethodTests#getIdPrivateBelongsToOtherUser()
	 */
	@Override
	public Integer getIdPrivateBelongsToOtherUser() {
		return TestUtils.CLUB_PRIVATE_NON_MEMBER_ID;
	}

	/**
	 * @see test.api.service.standardtests.spec.ListMethodTests#getIdPrivateBelongsToAuthenticatedUser()
	 */
	@Override
	public Integer getIdPrivateBelongsToAuthenticatedUser() {
		return TestUtils.CLUB_PRIVATE_MEMBER_ID;
	}

	/**
	 * @see test.api.service.standardtests.spec.ListMethodTests#getIdInvalid()
	 */
	@Override
	public Integer getIdInvalid() {
		return TestUtils.CLUB_INVALID_ID;
	}

	/**
	 * @see test.api.service.standardtests.ListMethodTest#callback(javastrava.api.v3.service.Strava)
	 */
	@Override
	protected ListCallback<StravaClubAnnouncement, Integer> callback(final Strava strava) {
		return ((parentId) -> {
			return strava.listClubAnnouncements(parentId);
		});
	}
}
