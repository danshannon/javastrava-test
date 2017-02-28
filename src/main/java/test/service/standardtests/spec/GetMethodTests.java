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
 *            Type of Strava object undergoing test
 * @param <U>
 *            The type of the Strava object's identifier
 *
 */
public interface GetMethodTests<T extends StravaEntity, U> extends PrivacyTests, StandardTests, ValidationTests<T, U> {
	/**
	 * <p>
	 * Test that an invalid parent id returns <code>null</code>
	 * </p>
	 * 
	 * @throws Exception
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
