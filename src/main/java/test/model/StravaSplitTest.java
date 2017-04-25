package test.model;

import javastrava.model.StravaSplit;
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

	@Override
	protected Class<StravaSplit> getClassUnderTest() {
		return StravaSplit.class;
	}
}
