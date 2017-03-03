package test.api.model;

import static org.junit.Assert.assertNotNull;

import javastrava.api.v3.model.StravaActivityZoneDistributionBucket;
import test.utils.BeanTest;

/**
 * @author Dan Shannon
 *
 */
public class StravaActivityZoneDistributionBucketTest extends BeanTest<StravaActivityZoneDistributionBucket> {

	/**
	 * <p>
	 * Validate structure and content of the bucket
	 * </p>
	 *
	 * @param bucket
	 *            Bucket to be validated
	 */
	public static void validate(final StravaActivityZoneDistributionBucket bucket) {
		assertNotNull(bucket);
		assertNotNull(bucket.getMax());
		assertNotNull(bucket.getMin());
		assertNotNull(bucket.getTime());
	}

	@Override
	protected Class<StravaActivityZoneDistributionBucket> getClassUnderTest() {
		return StravaActivityZoneDistributionBucket.class;
	}

}
