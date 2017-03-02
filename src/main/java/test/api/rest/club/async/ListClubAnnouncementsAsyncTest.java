package test.api.rest.club.async;

import static org.junit.Assert.assertNotNull;

import javastrava.api.v3.model.StravaClubAnnouncement;
import test.api.rest.TestListArrayCallback;
import test.api.rest.club.ListClubAnnouncementsTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * @author Dan Shannon
 *
 */
public class ListClubAnnouncementsAsyncTest extends ListClubAnnouncementsTest {
	/**
	 * @see test.api.rest.club.ListClubAnnouncementsTest#listCallback()
	 */
	@Override
	protected TestListArrayCallback<StravaClubAnnouncement, Integer> listCallback() {
		return (api, id) -> api.listClubAnnouncementsAsync(id).get();
	}

	@Override
	public void testListClubAnnouncements_privateClubMember() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaClubAnnouncement[] announcements = api().listClubAnnouncementsAsync(TestUtils.CLUB_PRIVATE_MEMBER_ID).get();
			assertNotNull(announcements);
		});
	}

}
