package test.api.model;

import javastrava.api.v3.model.StravaActivityZoneDistributionBucket;
import test.utils.BeanTest;

/**
 * @author Dan Shannon
 *
 */
public class StravaActivityZoneDistributionBucketTest extends BeanTest<StravaActivityZoneDistributionBucket> {

	@Override
	protected Class<StravaActivityZoneDistributionBucket> getClassUnderTest() {
		return StravaActivityZoneDistributionBucket.class;
	}

}
