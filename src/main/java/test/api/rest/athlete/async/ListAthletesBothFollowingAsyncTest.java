package test.api.rest.athlete.async;

import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.rest.API;
import test.api.rest.athlete.ListAthletesBothFollowingTest;
import test.api.rest.callback.APIListCallback;
import test.api.rest.util.ArrayCallback;

/**
 * <p>
 * Specific tests for {@link API#listAthletesBothFollowingAsync(Integer, Integer, Integer)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListAthletesBothFollowingAsyncTest extends ListAthletesBothFollowingTest {
	/**
	 * @see test.api.rest.athlete.ListAthletesBothFollowingTest#listCallback()
	 */
	@Override
	protected APIListCallback<StravaAthlete, Integer> listCallback() {
		return (api, id) -> api.listAthletesBothFollowingAsync(id, null, null).get();
	}

	/**
	 * @see test.api.rest.athlete.ListAthletesBothFollowingTest#pagingCallback()
	 */
	@Override
	protected ArrayCallback<StravaAthlete> pagingCallback() {
		return paging -> api().listAthletesBothFollowingAsync(validId(), paging.getPage(), paging.getPageSize()).get();
	}

}
