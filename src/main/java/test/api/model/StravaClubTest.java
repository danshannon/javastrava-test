package test.api.model;

import javastrava.api.v3.model.StravaClub;
import test.utils.BeanTest;

/**
 * <p>
 * Data tests for {@link StravaClub}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class StravaClubTest extends BeanTest<StravaClub> {

	/**
	 * @param clubs
	 *            List of clubs to check
	 * @param id
	 *            Id of the club we're checking for membership
	 * @return <code>true</code> if one of the clubs has the given id
	 */
	public static boolean checkIsMember(final StravaClub[] clubs, final Integer id) {
		for (final StravaClub club : clubs) {
			if (club.getId().intValue() == id.intValue()) {
				return true;
			}
		}
		return false;
	}

	@Override
	protected Class<StravaClub> getClassUnderTest() {
		return StravaClub.class;
	}
}
