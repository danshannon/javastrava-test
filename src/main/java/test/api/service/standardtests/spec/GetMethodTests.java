/**
 *
 */
package test.api.service.standardtests.spec;

import org.junit.Test;

import javastrava.api.v3.model.StravaEntity;
import test.utils.TestDataUtils;

/**
 * <p>
 * Standard set of tests to be executed for all methods
 * </p>
 *
 * @author Dan Shannon
 *
 */
public interface GetMethodTests<T extends StravaEntity<U>, U, V> extends PrivacyTests, StandardTests, TestDataUtils<T, U, V> {
	/**
	 * <p>
	 * Test that an invalid parent id returns <code>null</code>
	 * </p>
	 */
	@Test
	public void testGetInvalidId() throws Exception;

	/**
	 * <p>
	 * Test that a valid parent returns an object as expected
	 * </p>
	 *
	 * @throws Exception
	 */
	@Test
	public void testGetValidId() throws Exception;

}
