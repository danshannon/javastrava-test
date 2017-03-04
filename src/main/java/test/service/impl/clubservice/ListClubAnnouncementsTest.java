package test.service.impl.clubservice;

import javastrava.api.v3.model.StravaClubAnnouncement;
import test.api.model.StravaClubAnnouncementTest;
import test.service.standardtests.ListMethodTest;
import test.service.standardtests.callbacks.ListCallback;
import test.service.standardtests.data.ClubDataUtils;

/**
 * @author Dan Shannon
 *
 */
public class ListClubAnnouncementsTest extends ListMethodTest<StravaClubAnnouncement, Integer> {
	@Override
	public Integer idValidWithEntries() {
		return ClubDataUtils.CLUB_VALID_ID;
	}

	@Override
	public Integer idValidWithoutEntries() {
		return null;
	}

	@Override
	public Integer idPrivateBelongsToOtherUser() {
		return ClubDataUtils.CLUB_PRIVATE_NON_MEMBER_ID;
	}

	@Override
	public Integer idInvalid() {
		return ClubDataUtils.CLUB_INVALID_ID;
	}

	@Override
	protected ListCallback<StravaClubAnnouncement, Integer> lister() {
		return ((strava, id) -> strava.listClubAnnouncements(id));
	}

	@Override
	protected Integer idPrivate() {
		return null;
	}

	@Override
	protected void validate(StravaClubAnnouncement object) {
		StravaClubAnnouncementTest.validate(object);
	}

}
