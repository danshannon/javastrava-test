package test.api.service.impl.activityservice;

import javastrava.api.v3.model.StravaActivityZone;
import test.api.model.StravaActivityZoneTest;
import test.api.service.standardtests.ListMethodTest;
import test.api.service.standardtests.callbacks.ListCallback;
import test.utils.TestUtils;

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
	public Long idValidWithEntries() {
		return TestUtils.ACTIVITY_WITH_ZONES;
	}

	@Override
	public Long idValidWithoutEntries() {
		return TestUtils.ACTIVITY_WITHOUT_ZONES;
	}

	@Override
	public Long idPrivateBelongsToOtherUser() {
		return TestUtils.ACTIVITY_PRIVATE_OTHER_USER;
	}

	@Override
	public Long idInvalid() {
		return TestUtils.ACTIVITY_INVALID;
	}

	@Override
	protected ListCallback<StravaActivityZone, Long> lister() {
		return ((strava, id) -> strava.listActivityZones(id));
	}

	@Override
	protected Long idPrivate() {
		return TestUtils.ACTIVITY_PRIVATE;
	}

	@Override
	protected void validate(StravaActivityZone zone) {
		StravaActivityZoneTest.validate(zone);
	}

}
