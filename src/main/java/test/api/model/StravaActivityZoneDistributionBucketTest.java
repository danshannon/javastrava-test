package test.api.model;

import static org.junit.Assert.assertNotNull;
import javastrava.api.v3.model.StravaActivityZoneDistributionBucket;
import test.utils.BeanTest;

/**
 * @author dshannon
 *
 */
public class StravaActivityZoneDistributionBucketTest extends BeanTest<StravaActivityZoneDistributionBucket> {

	@Override
	protected Class<StravaActivityZoneDistributionBucket> getClassUnderTest() {
		return StravaActivityZoneDistributionBucket.class;
	}

	public static void validateBucket(StravaActivityZoneDistributionBucket bucket) {
		assertNotNull(bucket);
		assertNotNull(bucket.getMax());
		assertNotNull(bucket.getMin());
		assertNotNull(bucket.getTime());
	}

}
