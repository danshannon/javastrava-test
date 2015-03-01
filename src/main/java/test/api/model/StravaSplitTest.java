package test.api.model;

import static org.junit.Assert.assertNotNull;
import javastrava.api.v3.model.StravaSplit;
import test.utils.BeanTest;

/**
 * @author dshannon
 *
 */
public class StravaSplitTest extends BeanTest<StravaSplit> {

	@Override
	protected Class<StravaSplit> getClassUnderTest() {
		return StravaSplit.class;
	}

	/**
	 * @param split
	 */
	public static void validateSplit(StravaSplit split) {
		assertNotNull(split);
		assertNotNull(split.getDistance());
		assertNotNull(split.getElapsedTime());
		assertNotNull(split.getElevationDifference());
		assertNotNull(split.getMovingTime());
		assertNotNull(split.getSplit());
		
	}
}
