/**
 *
 */
package test.service.standardtests.spec;

import org.junit.Test;

import javastrava.api.v3.model.StravaEntity;

/**
 * <p>
 * Standard set of tests to be executed for all methods
 * </p>
 *
 * @author Dan Shannon
 * @param <T>
 *            Type of Strava object under test
 * @param <U>
 *            Strava object type's identifier type
 *
 */
public interface GetTests<T extends StravaEntity, U> extends PrivacyTests, StandardTests {
	/**
	 * <p>
	 * Test that an invalid id returns <code>null</code>
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Test
	public void testGetInvalidId() throws Exception;

	/**
	 * <p>
	 * Test that a null id returns <code>null</code> as expected
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Test
	public void testGetNullId() throws Exception;

	/**
	 * <p>
	 * Test that a valid id returns an object as expected
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Test
	public void testGetValidId() throws Exception;

}
