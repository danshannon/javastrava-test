package test.api.service.impl.retrofit.athleteservices;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.service.AthleteServices;
import javastrava.api.v3.service.impl.retrofit.AthleteServicesImpl;
import javastrava.util.Paging;

import org.junit.Test;

import test.api.model.StravaAthleteTest;
import test.api.service.impl.ListCallback;
import test.api.service.impl.PagingListMethodTest;
import test.utils.TestUtils;

public class ListAthleteFriendsTest extends PagingListMethodTest<StravaAthlete, Integer> {
	@Test
	public void testListAthleteFriends_validAthlete() {
		final List<StravaAthlete> friends = service().listAthleteFriends(TestUtils.ATHLETE_VALID_ID);
		assertNotNull(friends);
		assertFalse(friends.size() == 0);
		for (final StravaAthlete athlete : friends) {
			StravaAthleteTest.validateAthlete(athlete, athlete.getId(), StravaResourceState.SUMMARY);
		}
	}

	// Test cases
	// 2. Invalid athlete
	@Test
	public void testListAthleteFriends_invalidAthlete() {
		final List<StravaAthlete> friends = service().listAthleteFriends(TestUtils.ATHLETE_INVALID_ID);

		assertNull("Listed friends despite athlete id being invalid", friends);

	}

	@Test
	public void testListAthleteFriends_privateAthlete() {
		final List<StravaAthlete> friends = service().listAthleteFriends(TestUtils.ATHLETE_PRIVATE_ID);
		assertNotNull(friends);
		for (final StravaAthlete athlete : friends) {
			StravaAthleteTest.validateAthlete(athlete, athlete.getId(), StravaResourceState.SUMMARY);
		}
	}

	private AthleteServices service() {
		return AthleteServicesImpl.implementation(TestUtils.getValidToken());
	}

	@Override
	protected void validate(final StravaAthlete athlete, final Integer id, final StravaResourceState state) {
		StravaAthleteTest.validateAthlete(athlete, id, state);

	}

	@Override
	protected void validate(final StravaAthlete athlete) {
		validate(athlete, athlete.getId(), athlete.getResourceState());

	}

	@Override
	protected ListCallback<StravaAthlete> callback() {
		return (new ListCallback<StravaAthlete>() {

			@Override
			public List<StravaAthlete> getList(final Paging paging) {
				return service().listAthleteFriends(TestUtils.ATHLETE_VALID_ID, paging);
			}

		});
	}

}