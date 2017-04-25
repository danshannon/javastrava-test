package test.api.activity;

import static org.junit.Assume.assumeFalse;

import java.util.Arrays;

import javastrava.api.API;
import javastrava.model.StravaLap;
import test.api.APIListTest;
import test.api.callback.APIListCallback;
import test.issues.strava.Issue105;
import test.service.standardtests.data.ActivityDataUtils;

/**
 * <p>
 * Specific tests for {@link API#listActivityLaps(Long)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListActivityLapsTest extends APIListTest<StravaLap, Long> {
	/**
	 * Set flag to indicate that list activity laps for an activity belonging a user other than the authenticated one returns a 401 Unauthorised error
	 */
	public ListActivityLapsTest() {
		this.listOtherReturns401Unauthorised = true;
	}

	@Override
	protected Long invalidId() {
		return ActivityDataUtils.ACTIVITY_INVALID;
	}

	@Override
	public void list_validParentNoChildren() throws Exception {
		assumeFalse(Issue105.issue());
		super.list_validParentNoChildren();
	}

	@Override
	protected APIListCallback<StravaLap, Long> listCallback() {
		return ((api, id) -> api.listActivityLaps(id));
	}

	@Override
	protected Long privateId() {
		return ActivityDataUtils.ACTIVITY_PRIVATE_WITH_LAPS;
	}

	@Override
	protected Long privateIdBelongsToOtherUser() {
		return ActivityDataUtils.ACTIVITY_PRIVATE_OTHER_USER;
	}

	@Override
	protected void validate(final StravaLap lap) {
		ActivityDataUtils.validateLap(lap);

	}

	@Override
	protected void validateArray(final StravaLap[] list) {
		ActivityDataUtils.validateLapsList(Arrays.asList(list));

	}

	@Override
	protected Long validId() {
		return ActivityDataUtils.ACTIVITY_WITH_LAPS;
	}

	@Override
	protected Long validIdBelongsToOtherUser() {
		return ActivityDataUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER;
	}

	@Override
	protected Long validIdNoChildren() {
		return ActivityDataUtils.ACTIVITY_WITHOUT_LAPS;
	}

}
