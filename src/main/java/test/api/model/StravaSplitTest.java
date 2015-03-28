package test.api.model;

import static org.junit.Assert.assertNotNull;
import javastrava.api.v3.model.StravaSplit;
import test.utils.BeanTest;

/**
 * @author Dan Shannon
 *
 */
public class StravaSplitTest extends BeanTest<StravaSplit> {

	/**
	 * @param split
	 */
	public static void validateSplit(final StravaSplit split) {
		assertNotNull(split);
		assertNotNull(split.getDistance());
		assertNotNull(split.getElapsedTime());
		assertNotNull(split.getElevationDifference());
		assertNotNull(split.getMovingTime());
		assertNotNull(split.getSplit());

	}

	@Override
	protected Class<StravaSplit> getClassUnderTest() {
		return StravaSplit.class;
	}
}
