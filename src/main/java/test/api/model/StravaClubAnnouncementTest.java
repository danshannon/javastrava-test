package test.api.model;

import javastrava.api.v3.model.StravaClubAnnouncement;
import test.utils.BeanTest;

/**
 * @author Dan Shannon
 *
 */
public class StravaClubAnnouncementTest extends BeanTest<StravaClubAnnouncement> {

	/**
	 * @see test.utils.BeanTest#getClassUnderTest()
	 */
	@Override
	protected Class<StravaClubAnnouncement> getClassUnderTest() {
		return StravaClubAnnouncement.class;
	}

}
