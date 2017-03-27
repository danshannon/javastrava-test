package test.service.impl.activityservice;

import javastrava.api.v3.model.StravaActivityZone;
import test.service.standardtests.ListMethodTest;
import test.service.standardtests.callbacks.ListCallback;
import test.service.standardtests.data.ActivityDataUtils;

/**
 * <p>
 * Specific tests for the methods that list Strava activity zones
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListActivityZonesTest extends ListMethodTest<StravaActivityZone, Long> {
	@Override
	public Long idInvalid() {
		return ActivityDataUtils.ACTIVITY_INVALID;
	}

	@Override
	protected Long idPrivate() {
		return ActivityDataUtils.ACTIVITY_PRIVATE;
	}

	@Override
	public Long idPrivateBelongsToOtherUser() {
		return ActivityDataUtils.ACTIVITY_PRIVATE_OTHER_USER;
	}

	@Override
	public Long idValidWithEntries() {
		return ActivityDataUtils.ACTIVITY_WITH_ZONES;
	}

	@Override
	public Long idValidWithoutEntries() {
		return ActivityDataUtils.ACTIVITY_WITHOUT_ZONES;
	}

	@Override
	protected ListCallback<StravaActivityZone, Long> lister() {
		return ((strava, id) -> strava.listActivityZones(id));
	}

	@Override
	protected void validate(StravaActivityZone zone) {
		ActivityDataUtils.validateActivityZone(zone);
	}

}
