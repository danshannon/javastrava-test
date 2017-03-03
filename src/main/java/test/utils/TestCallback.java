/**
 *
 */
package test.utils;

/**
 * @author Dan Shannon
 *
 */
public interface TestCallback {
	/**
	 * <p>
	 * Perform a test
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	public void test() throws Exception;
}
