package test.api.club;

import java.util.Arrays;

import javastrava.api.API;
import javastrava.model.StravaClub;
import test.api.APIListTest;
import test.api.callback.APIListCallback;
import test.service.standardtests.data.AthleteDataUtils;
import test.service.standardtests.data.ClubDataUtils;

/**
 * <p>
 * Specific tests and config for {@link API#listAuthenticatedAthleteActivities(Integer, Integer, Integer, Integer)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListAuthenticatedAthleteClubsTest extends APIListTest<StravaClub, Integer> {
	@Override
	protected Integer invalidId() {
		return null;
	}

	@Override
	protected APIListCallback<StravaClub, Integer> listCallback() {
		return (api, id) -> api.listAuthenticatedAthleteClubs();
	}

	@Override
	protected Integer privateId() {
		return null;
	}

	@Override
	protected Integer privateIdBelongsToOtherUser() {
		return null;
	}

	@Override
	protected void validate(final StravaClub result) throws Exception {
		ClubDataUtils.validate(result);

	}

	@Override
	protected void validateArray(final StravaClub[] list) {
		ClubDataUtils.validateList(Arrays.asList(list));

	}

	@Override
	protected Integer validId() {
		return AthleteDataUtils.ATHLETE_AUTHENTICATED_ID;
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
