package test.model;

import javastrava.model.StravaClubEventJoinResponse;
import test.utils.BeanTest;

/**
 * Mean bean test for {@link StravaClubEventJoinResponse}
 *
 * @author Dan Shannon
 *
 */
public class StravaClubEventJoinResponseTest extends BeanTest<StravaClubEventJoinResponse> {

	@Override
	protected Class<StravaClubEventJoinResponse> getClassUnderTest() {
		return StravaClubEventJoinResponse.class;
	}

}
