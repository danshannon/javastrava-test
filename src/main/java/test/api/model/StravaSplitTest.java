package test.api.model;

import static org.junit.Assert.assertNotNull;

import javastrava.api.v3.model.StravaSplit;
import test.utils.BeanTest;

/**
 * <p>
 * Tests for {@link StravaSplit}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class StravaSplitTest extends BeanTest<StravaSplit> {

	/**
	 * Validate the structure and content of a split
	 *
	 * @param split
	 *            The split to be validated
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
