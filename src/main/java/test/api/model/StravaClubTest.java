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

	@Override
	protected Class<StravaClub> getClassUnderTest() {
		return StravaClub.class;
	}
}
