package test.api.model;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import javastrava.api.v3.model.StravaClubAnnouncement;
import javastrava.api.v3.model.reference.StravaResourceState;
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

	/**
	 * Validate contents of the object
	 *
	 * @param object
	 *            Object to be validated
	 */
	public static void validate(StravaClubAnnouncement object) {
		assertNotNull(object.getAthlete());
		assertNotNull(object.getClubId());
		assertNotEquals("Unknown StravaResourceState" + object.getResourceState(), object.getResourceState(), //$NON-NLS-1$
				StravaResourceState.UNKNOWN);

	}

}
