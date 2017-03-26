/**
 *
 */
package test.service.standardtests.spec;

import org.junit.Test;

/**
 * <p>
 * Standard tests that should be executed on all methods
 * </p>
 *
 * @author Dan Shannon
 */
public interface StandardTests {
	/**
	 * <p>
	 * Test the behaviour of a call using an invalid id (or in some cases an invalid parent id)
	 * </p>
	 *
	 * <p>
	 * Test should return <code>null</code>
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Test
	public void testInvalidId() throws Exception;
}
