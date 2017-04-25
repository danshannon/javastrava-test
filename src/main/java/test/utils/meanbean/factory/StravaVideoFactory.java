package test.utils.meanbean.factory;

import org.meanbean.lang.Factory;

import javastrava.model.StravaVideo;
import javastrava.model.reference.StravaResourceState;
import test.service.standardtests.data.ActivityDataUtils;

/**
 * Test data factory for videos
 *
 * @author Dan Shannon
 *
 */
public class StravaVideoFactory implements Factory<StravaVideo> {

	@Override
	public StravaVideo create() {
		return ActivityDataUtils.testVideo(StravaResourceState.DETAILED);
	}

}
