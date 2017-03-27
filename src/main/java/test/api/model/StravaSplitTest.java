package test.api.model;

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

	@Override
	protected Class<StravaSplit> getClassUnderTest() {
		return StravaSplit.class;
	}
}
